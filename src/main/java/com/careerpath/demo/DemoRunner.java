package com.careerpath.demo;

import com.careerpath.services.*;
import com.careerpath.models.*;
import java.util.*;

/**
 * Demo Runner - Test the Career Path application logic
 * without requiring a full Tomcat server setup
 */
public class DemoRunner {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   Career Path Analyzer - Java Demo     ║");
        System.out.println("║   Collections | Servlets | JSP | Java  ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        // Demo 1: Collections & User Management
        demoUserDatabase();

        // Demo 2: Career Data & Collections
        demoCareerData();

        // Demo 3: Resume Processing & String Handling
        demoResumeProcessing();

        // Demo 4: Complete Analysis
        completeAnalysis();

        System.out.println("\n✅ Demo Complete! Ready for Tomcat deployment.\n");
    }

    /**
     * Demo 1: UserDatabase - HashMap based user storage
     */
    private static void demoUserDatabase() {
        System.out.println("\n════ DEMO 1: User Database (Collections: HashMap) ════");
        
        System.out.println("🔧 Testing user registration and retrieval...\n");

        // Register new user
        boolean registered = UserDatabase.registerUser(
            "demo@example.com", 
            "Demo User", 
            "demo123"
        );
        System.out.println("✓ User Registration: " + (registered ? "SUCCESS" : "FAILED"));

        // Authenticate user
        User user = UserDatabase.authenticateUser("krishnami721@gmail.com", "password123");
        System.out.println("✓ User Authentication: " + (user != null ? "SUCCESS" : "FAILED"));
        if (user != null) {
            System.out.println("  └─ Logged in as: " + user.getName() + " (" + user.getRole() + ")");
        }

        // Get all users
        Collection<User> allUsers = UserDatabase.getAllUsers();
        System.out.println("✓ Total Users: " + allUsers.size());

        // Demo: User operations
        User retrievedUser = UserDatabase.getUserByEmail("krishnami721@gmail.com");
        if (retrievedUser != null) {
            System.out.println("✓ Retrieved User: " + retrievedUser.getName());
        }
    }

    /**
     * Demo 2: CareerData - Collections (HashMap, HashSet, ArrayList)
     */
    private static void demoCareerData() {
        System.out.println("\n════ DEMO 2: Career Data (Collections: HashMap, HashSet, ArrayList) ════");
        
        System.out.println("🔧 Testing job requirements and skill data...\n");

        // Get all jobs
        List<com.careerpath.models.JobRequirement> jobs = CareerData.getAllJobs();
        System.out.println("✓ Available Jobs: " + jobs.size());
        for (com.careerpath.models.JobRequirement job : jobs) {
            System.out.println("  └─ " + job.getTitle() + " requires " + 
                            job.getRequiredSkills().size() + " skills");
        }

        // Get job titles
        Set<String> jobTitles = CareerData.getAllJobTitles();
        System.out.println("\n✓ Job Titles (Using HashSet - unique values):");
        for (String title : jobTitles) {
            System.out.println("  └─ " + title);
        }

        // Get all skills
        String[] allSkills = CareerData.getAllPossibleSkills();
        System.out.println("\n✓ Total Possible Skills: " + allSkills.length);
        System.out.println("  First 5 skills: " + String.join(", ", 
            Arrays.copyOf(allSkills, Math.min(5, allSkills.length))));

        // Get specific skill learning path
        String skillPath = CareerData.getLearningPath("Java");
        System.out.println("\n✓ Learning Path for 'Java': ");
        System.out.println("  └─ " + skillPath);
    }

    /**
     * Demo 3: ResumeProcessor - String Handling & Collections
     */
    private static void demoResumeProcessing() {
        System.out.println("\n════ DEMO 3: Resume Processing (String Handling & Collections) ════");
        
        System.out.println("🔧 Testing skill extraction from resume text...\n");

        String sampleResume = "I am a skilled Java developer with 5 years of experience. " +
                             "I have expertise in Spring Framework, SQL databases, and Collections. " +
                             "I also know HTML, CSS, and JavaScript for frontend work.";

        System.out.println("📄 Sample Resume:");
        System.out.println("  └─ " + sampleResume + "\n");

        // Extract skills
        Set<String> extractedSkills = ResumeProcessor.extractSkills(sampleResume);
        System.out.println("✓ Skills Extracted (Using HashSet - automatic deduplication):");
        System.out.println("  └─ Count: " + extractedSkills.size());
        for (String skill : extractedSkills) {
            System.out.println("    • " + skill);
        }

        // Get missing skills for Java Developer
        List<String> userSkillsList = new ArrayList<>(extractedSkills);
        List<String> missingSkills = ResumeProcessor.getMissingSkills(
            userSkillsList, 
            "Java Developer"
        );
        System.out.println("\n✓ Missing Skills for 'Java Developer':");
        for (String skill : missingSkills) {
            System.out.println("  └─ " + skill);
        }

        // Calculate match percentage
        int matchPercentage = ResumeProcessor.calculateMatchPercentage(
            userSkillsList, 
            "Java Developer"
        );
        System.out.println("\n✓ Match Percentage for 'Java Developer': " + matchPercentage + "%");
    }

    /**
     * Demo 4: Complete Resume Analysis
     */
    private static void completeAnalysis() {
        System.out.println("\n════ DEMO 4: Complete Resume Analysis ════");
        
        System.out.println("🔧 Analyzing resume against all jobs...\n");

        String sampleResume = "Python expert with machine learning background. " +
                             "Skills: Python, Machine Learning, Statistics, SQL, R programming.";

        System.out.println("📄 Resume Submitted:");
        System.out.println("  └─ " + sampleResume + "\n");

        // Analyze resume
        List<com.careerpath.models.AnalysisResult> results = 
            ResumeProcessor.analyzeResume(sampleResume);

        System.out.println("📊 Analysis Results (Sorted by Best Match):\n");
        
        for (int i = 0; i < results.size(); i++) {
            com.careerpath.models.AnalysisResult result = results.get(i);
            int matchPct = result.getMatchPercentage();
            
            String progressBar = getProgressBar(matchPct);
            
            System.out.println((i+1) + ". " + result.getMatchedJob().getTitle());
            System.out.println("   Match: " + progressBar + " " + matchPct + "%");
            
            System.out.println("   ✓ Your Skills: " + String.join(", ", result.getExtractedSkills()));
            
            List<String> missingSkills = ResumeProcessor.getMissingSkills(
                result.getExtractedSkills(),
                result.getMatchedJob().getTitle()
            );
            if (!missingSkills.isEmpty()) {
                System.out.println("   ✗ Missing: " + String.join(", ", missingSkills));
            } else {
                System.out.println("   ✓ You have all required skills!");
            }
            System.out.println();
        }
    }

    /**
     * Helper method to create a visual progress bar
     */
    private static String getProgressBar(int percentage) {
        int filled = percentage / 10;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            bar.append(i < filled ? "█" : "░");
        }
        bar.append("]");
        return bar.toString();
    }
}
