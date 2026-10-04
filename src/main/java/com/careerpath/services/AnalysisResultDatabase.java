package com.careerpath.services;

import com.careerpath.models.AnalysisResult;
import com.careerpath.database.DatabaseConnection;
import java.sql.*;
import java.util.*;

/**
 * Service for persisting resume analysis results to database
 */
public class AnalysisResultDatabase {
    
    /**
     * Save resume analysis result to database
     */
    public static boolean saveAnalysisResult(String userEmail, String resumeText, AnalysisResult result) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            
            // First, get user ID
            int userId = getUserId(conn, userEmail);
            if (userId == -1) {
                System.err.println("User not found: " + userEmail);
                return false;
            }
            
            // Save resume
            int resumeId = saveResume(conn, userId, resumeText);
            if (resumeId == -1) {
                return false;
            }
            
            // Save analysis result
            String skillsJson = String.join(",", result.getExtractedSkills());
            String missingJson = String.join(",", result.getMissingSkills());
            String suggestionsJson = String.join("|", result.getImprovementSuggestions());
            
            String sql = "INSERT INTO resume_analysis (resume_id, user_id, resume_score, matched_job_title, " +
                        "match_percentage, extracted_skills, missing_skills, improvement_suggestions) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, resumeId);
                pstmt.setInt(2, userId);
                pstmt.setInt(3, result.getResumeScore());
                pstmt.setString(4, result.getMatchedJob().getTitle());
                pstmt.setInt(5, result.getMatchPercentage());
                pstmt.setString(6, skillsJson);
                pstmt.setString(7, missingJson);
                pstmt.setString(8, suggestionsJson);
                
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int analysisId = generatedKeys.getInt(1);
                        
                        // Save job matches
                        if (result.getMatchedJobs() != null) {
                            for (com.careerpath.models.JobOpportunity job : result.getMatchedJobs()) {
                                saveJobMatch(conn, analysisId, userId, job);
                            }
                        }
                        
                        System.out.println("✓ Analysis result saved (ID: " + analysisId + ")");
                        return true;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error saving analysis result: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        
        return false;
    }
    
    /**
     * Get user ID by email
     */
    private static int getUserId(Connection conn, String userEmail) throws SQLException {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userEmail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }
    
    /**
     * Save resume text to database
     */
    private static int saveResume(Connection conn, int userId, String resumeText) throws SQLException {
        String sql = "INSERT INTO resumes (user_id, resume_text) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, resumeText);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }
    
    /**
     * Save individual job match to database
     */
    private static void saveJobMatch(Connection conn, int analysisId, int userId, 
                                     com.careerpath.models.JobOpportunity job) throws SQLException {
        String sql = "INSERT INTO job_matches (analysis_id, user_id, job_title, company, match_percentage, " +
                    "seniority, salary_range, matching_skills, missing_skills) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, analysisId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, job.getJobTitle());
            pstmt.setString(4, job.getCompany());
            pstmt.setDouble(5, job.getMatchPercentage());
            pstmt.setString(6, job.getSeniority());
            pstmt.setString(7, job.getSalaryRange());
            pstmt.setString(8, String.join(",", job.getMatchingSkills()));
            pstmt.setString(9, String.join(",", job.getMissingSkills()));
            
            pstmt.executeUpdate();
        }
    }
    
    /**
     * Retrieve analysis history for a user
     */
    public static List<Map<String, Object>> getUserAnalysisHistory(String userEmail) {
        List<Map<String, Object>> history = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            int userId = getUserId(conn, userEmail);
            if (userId == -1) return history;
            
            String sql = "SELECT ra.id, ra.resume_score, ra.matched_job_title, ra.match_percentage, " +
                        "ra.analyzed_at FROM resume_analysis ra WHERE ra.user_id = ? ORDER BY ra.analyzed_at DESC";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    Map<String, Object> record = new HashMap<>();
                    record.put("id", rs.getInt("id"));
                    record.put("resumeScore", rs.getInt("resume_score"));
                    record.put("jobTitle", rs.getString("matched_job_title"));
                    record.put("matchPercentage", rs.getInt("match_percentage"));
                    record.put("analyzedAt", rs.getString("analyzed_at"));
                    history.add(record);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving analysis history: " + e.getMessage());
        }
        
        return history;
    }
    
    /**
     * Get detailed analysis result by ID
     */
    public static Map<String, Object> getAnalysisById(int analysisId) {
        Map<String, Object> analysis = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM resume_analysis WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, analysisId);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    analysis.put("id", rs.getInt("id"));
                    analysis.put("resumeScore", rs.getInt("resume_score"));
                    analysis.put("jobTitle", rs.getString("matched_job_title"));
                    analysis.put("matchPercentage", rs.getInt("match_percentage"));
                    analysis.put("skills", rs.getString("extracted_skills").split(","));
                    analysis.put("missingSkills", rs.getString("missing_skills").split(","));
                    analysis.put("suggestions", rs.getString("improvement_suggestions").split("\\|"));
                    
                    // Get job matches
                    List<Map<String, Object>> jobMatches = getJobMatchesForAnalysis(conn, analysisId);
                    analysis.put("jobMatches", jobMatches);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving analysis: " + e.getMessage());
        }
        
        return analysis;
    }
    
    /**
     * Get job matches for specific analysis
     */
    private static List<Map<String, Object>> getJobMatchesForAnalysis(Connection conn, int analysisId) 
            throws SQLException {
        List<Map<String, Object>> matches = new ArrayList<>();
        
        String sql = "SELECT * FROM job_matches WHERE analysis_id = ? ORDER BY match_percentage DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, analysisId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> match = new HashMap<>();
                match.put("jobTitle", rs.getString("job_title"));
                match.put("company", rs.getString("company"));
                match.put("matchPercentage", rs.getDouble("match_percentage"));
                match.put("seniority", rs.getString("seniority"));
                match.put("salary", rs.getString("salary_range"));
                matches.add(match);
            }
        }
        
        return matches;
    }
}
