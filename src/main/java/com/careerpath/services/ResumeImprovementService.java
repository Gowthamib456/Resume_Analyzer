package com.careerpath.services;

import java.util.*;

/**
 * Service for generating resume improvement recommendations and career guidance
 */
public class ResumeImprovementService {
    
    /**
     * Calculate resume score based on various factors (0-100)
     */
    public static int calculateResumeScore(List<String> skills, String experience, String education, String achievements) {
        int score = 0;
        
        // Skills scoring (max 30 points)
        if (skills != null && skills.size() >= 10) score += 30;
        else if (skills != null) score += skills.size() * 3;
        
        // Experience scoring (max 30 points)
        if (experience != null && experience.length() > 500) score += 30;
        else if (experience != null) score += (experience.length() / 50);
        
        // Education scoring (max 20 points)
        if (education != null && education.contains("Bachelor")) score += 15;
        if (education != null && education.contains("Master")) score += 5;
        if (education != null && education.contains("Certification")) score += 5;
        
        // Achievements/Accomplishments (max 20 points)
        if (achievements != null && achievements.length() > 300) score += 20;
        else if (achievements != null) score += (achievements.length() / 30);
        
        // Format score to stay within 0-100 range
        return Math.min(100, Math.max(0, score));
    }
    
    /**
     * Generate actionable improvement suggestions
     */
    public static List<String> generateImprovementSuggestions(List<String> skills, int resumeScore, int matchPercentage) {
        List<String> suggestions = new ArrayList<>();
        
        // Skills-based suggestions
        if (skills.size() < 8) {
            suggestions.add("Add more technical skills to make your resume stand out");
        }
        
        if (!hasKeySkill(skills, "leadership")) {
            suggestions.add("Highlight leadership experience and team management");
        }
        
        if (!hasKeySkill(skills, "communication")) {
            suggestions.add("Emphasize communication and soft skills");
        }
        
        // Score-based suggestions
        if (resumeScore < 50) {
            suggestions.add("Expand your professional experience section with more details");
            suggestions.add("Include quantifiable achievements (e.g., 'Increased revenue by 20%')");
        }
        
        if (resumeScore < 70) {
            suggestions.add("Add certifications or courses relevant to your target role");
            suggestions.add("Include a professional summary at the top of your resume");
        }
        
        // Match-based suggestions
        if (matchPercentage < 70) {
            suggestions.add("Consider learning additional skills required for target positions");
            suggestions.add("Tailor your resume keywords to match job descriptions");
        }
        
        // General improvements
        suggestions.add("Ensure consistent formatting and clear section headers");
        suggestions.add("Use action verbs to describe achievements (e.g., 'Developed', 'Managed', 'Improved')");
        suggestions.add("Keep resume to 1-2 pages for better ATS compatibility");
        
        return suggestions;
    }
    
    /**
     * Generate ATS (Applicant Tracking System) optimization tips
     */
    public static List<String> generateATSOptimizationTips() {
        List<String> tips = new ArrayList<>();
        
        tips.add("Use standard section headers: Summary, Skills, Experience, Education, Certifications");
        tips.add("Avoid headers, text boxes, and graphics that ATS systems may not parse correctly");
        tips.add("Use standard fonts like Arial, Calibri, or Times New Roman");
        tips.add("Save and submit resume as PDF or Word doc, not as image");
        tips.add("Use single-column layout - ATS systems struggle with multi-column layouts");
        tips.add("Include keywords from the job posting naturally in your resume");
        tips.add("Avoid special characters and symbols that may confuse parsers");
        tips.add("Use bullet points instead of paragraphs for better readability");
        tips.add("Include both acronyms and full names (e.g., 'Agile Software Development (Agile)')");
        tips.add("Ensure contact information is clearly visible at the top");
        
        return tips;
    }
    
    /**
     * Generate interview questions for a given role
     */
    public static List<String> generateInterviewQuestions(String jobTitle, List<String> requiredSkills) {
        List<String> questions = new ArrayList<>();
        
        // General behavioral questions
        questions.add("Tell me about yourself and your professional journey");
        questions.add("Describe a challenging project you completed and how you overcame obstacles");
        questions.add("How do you stay updated with latest technologies and industry trends?");
        questions.add("Tell me about a time you worked in a team and had to handle conflicts");
        questions.add("What are your career goals for the next 5 years?");
        
        // Role-specific questions
        if (jobTitle.toLowerCase().contains("developer") || jobTitle.toLowerCase().contains("engineer")) {
            questions.add("Explain your approach to debugging complex software issues");
            questions.add("Describe your experience with agile/scrum development");
            questions.add("How do you ensure code quality and maintainability?");
            questions.add("Tell me about a time you had to learn a new technology quickly");
        }
        
        if (jobTitle.toLowerCase().contains("manager") || jobTitle.toLowerCase().contains("lead")) {
            questions.add("How do you motivate your team members?");
            questions.add("Describe your approach to handling underperforming team members");
            questions.add("How do you prioritize tasks when managing multiple projects?");
            questions.add("Tell me about your experience with mentoring junior team members");
        }
        
        // Skills-specific questions
        if (requiredSkills.contains("Java")) {
            questions.add("Explain the concept of object-oriented programming and its benefits");
            questions.add("What is the difference between abstract classes and interfaces?");
        }
        
        if (requiredSkills.contains("Spring")) {
            questions.add("What are the core concepts of Spring Framework?");
            questions.add("Explain dependency injection and how Spring implements it");
        }
        
        if (requiredSkills.contains("REST")) {
            questions.add("Explain REST principles and HTTP methods");
            questions.add("Describe how to design a RESTful API for scalability");
        }
        
        return questions;
    }
    
    /**
     * Build skill roadmap with learning paths
     */
    public static Map<String, Object> buildSkillRoadmap(List<String> currentSkills, String currentRole) {
        Map<String, Object> roadmap = new HashMap<>();
        
        // Define learning paths based on current role
        List<String> recommendedSkills = new ArrayList<>();
        List<String> highDemandSkills = new ArrayList<>();
        
        // High-demand skills in current market
        highDemandSkills.addAll(Arrays.asList(
            "Cloud Computing (AWS/Azure/GCP)",
            "Kubernetes & Docker",
            "AI/Machine Learning",
            "DevOps",
            "Microservices Architecture",
            "GraphQL",
            "Rust",
            "Go",
            "Data Science",
            "Cybersecurity"
        ));
        
        // Find skills to learn
        for (String skill : highDemandSkills) {
            if (!currentSkills.contains(skill)) {
                recommendedSkills.add(skill);
            }
        }
        
        roadmap.put("recommendedSkills", recommendedSkills.subList(0, Math.min(5, recommendedSkills.size())));
        roadmap.put("careerProgression", buildCareerProgressionPath(currentRole));
        roadmap.put("estimatedTimeToMastery", "3-6 months per skill");
        roadmap.put("learningResources", generateLearningResources(recommendedSkills));
        
        return roadmap;
    }
    
    /**
     * Build career progression path
     */
    public static List<String> buildCareerProgressionPath(String currentRole) {
        List<String> progression = new ArrayList<>();
        
        // Define typical progression paths
        if (currentRole.toLowerCase().contains("developer")) {
            progression.addAll(Arrays.asList(
                "Junior Developer",
                "Senior Developer",
                "Tech Lead",
                "Engineering Manager",
                "Director of Engineering"
            ));
        } else if (currentRole.toLowerCase().contains("engineer")) {
            progression.addAll(Arrays.asList(
                "Junior Engineer",
                "Senior Engineer",
                "Staff Engineer",
                "Principal Engineer"
            ));
        } else if (currentRole.toLowerCase().contains("analyst")) {
            progression.addAll(Arrays.asList(
                "Junior Analyst",
                "Senior Analyst",
                "Lead Analyst",
                "Manager"
            ));
        } else {
            progression.addAll(Arrays.asList(
                "Associate",
                "Professional",
                "Senior Professional",
                "Manager",
                "Director"
            ));
        }
        
        return progression;
    }
    
    /**
     * Generate learning resources for skills
     */
    public static List<String> generateLearningResources(List<String> skills) {
        List<String> resources = new ArrayList<>();
        
        resources.add("Udemy - For comprehensive video courses on any technology");
        resources.add("Coursera - For university-backed certificates");
        resources.add("LeetCode/HackerRank - For coding practice and interviews");
        resources.add("YouTube - For free tutorials and documentation");
        resources.add("Official Documentation - For latest features and best practices");
        resources.add("GitHub - For real-world project examples");
        resources.add("Conferences & Meetups - For networking and latest trends");
        resources.add("Online Certifications - For industry-recognized credentials");
        resources.add("Practice Projects - Build projects to gain hands-on experience");
        resources.add("Mentorship - Find mentors in your field for guidance");
        
        return resources;
    }
    
    /**
     * Generate job market insights
     */
    public static Map<String, String> generateJobMarketInsights() {
        Map<String, String> insights = new HashMap<>();
        
        insights.put("topDemandedSkills", "Cloud Computing, AI/ML, DevOps, Kubernetes");
        insights.put("emergingTechnologies", "Rust, Go, WebAssembly, Edge Computing");
        insights.put("salaryTrendsByExperience", "Entry: 40-60K | Mid: 80-120K | Senior: 120-200K+");
        insights.put("remoteWorkTrend", "60% of tech jobs now offer remote flexibility");
        insights.put("industryGrowth", "Tech sector growing 10-15% annually");
        insights.put("averageCareerChange", "Professionals learn new primary skill every 2-3 years");
        
        return insights;
    }
    
    /**
     * Helper method to check if skill exists
     */
    private static boolean hasKeySkill(List<String> skills, String keyword) {
        for (String skill : skills) {
            if (skill.toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
