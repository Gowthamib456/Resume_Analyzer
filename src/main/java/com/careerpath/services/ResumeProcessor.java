package com.careerpath.services;

import java.util.*;

/**
 * ResumeProcessor service class.
 * Demonstrates String Handling and Java Collections.
 * Extracts skills from resume text using regex and string manipulation.
 */
public class ResumeProcessor {

    /**
     * Extract skills from resume text.
     * Uses String Handling (regex, toLowerCase, split) and Collections (HashSet).
     * 
     * @param resumeText Raw resume text
     * @return Set of extracted skills
     */
    public static Set<String> extractSkills(String resumeText) {
        Set<String> extractedSkills = new HashSet<>();

        if (resumeText == null || resumeText.trim().isEmpty()) {
            return extractedSkills;
        }

        // String Handling: Convert to lowercase and remove special characters
        String cleanedText = resumeText.toLowerCase();
        cleanedText = cleanedText.replaceAll("[^a-z0-9\\s]", " ");

        // String Handling: Split by whitespace
        String[] words = cleanedText.split("\\s+");

        // Get all possible skills
        String[] allSkills = CareerData.getAllPossibleSkills();

        // Check each word against known skills
        for (String word : words) {
            for (String skill : allSkills) {
                if (skill.toLowerCase().equals(word)) {
                    extractedSkills.add(skill);
                }
            }
        }

        // Check for multi-word skills in the original text
        for (String skill : allSkills) {
            if (skill.contains(" ")) {
                if (cleanedText.contains(skill.toLowerCase())) {
                    extractedSkills.add(skill);
                }
            }
        }

        return extractedSkills;
    }

    /**
     * Analyze resume and find matching jobs.
     * Uses Collections to find best matches.
     * 
     * @param resumeText Resume content
     * @return List of AnalysisResult sorted by match percentage
     */
    public static List<com.careerpath.models.AnalysisResult> analyzeResume(String resumeText) {
        // Extract skills from resume
        Set<String> userSkills = extractSkills(resumeText);
        List<String> userSkillsList = new ArrayList<>(userSkills);

        // Get all jobs
        List<com.careerpath.models.JobRequirement> jobs = CareerData.getAllJobs();

        // Calculate match percentages and create analysis results
        List<com.careerpath.models.AnalysisResult> results = new ArrayList<>();

        for (com.careerpath.models.JobRequirement job : jobs) {
            int matchingSkills = job.getMatchingSkillsCount(userSkillsList);
            int totalRequiredSkills = job.getRequiredSkills().size();
            
            int matchPercentage = totalRequiredSkills > 0 
                ? (matchingSkills * 100) / totalRequiredSkills 
                : 0;

            results.add(new com.careerpath.models.AnalysisResult(
                userSkillsList, 
                job, 
                matchPercentage
            ));
        }

        // Sort results by match percentage in descending order
        results.sort((a, b) -> Integer.compare(b.getMatchPercentage(), a.getMatchPercentage()));

        return results;
    }

    /**
     * Get missing skills for a specific job.
     * Demonstrates String handling and Collections operations.
     * 
     * @param userSkills User's extracted skills
     * @param jobTitle Job title
     * @return List of missing skills
     */
    public static List<String> getMissingSkills(List<String> userSkills, String jobTitle) {
        List<String> requiredSkills = CareerData.getJobSkills(jobTitle);
        List<String> missingSkills = new ArrayList<>();

        for (String skill : requiredSkills) {
            if (!userSkills.contains(skill)) {
                missingSkills.add(skill);
            }
        }

        return missingSkills;
    }

    /**
     * Calculate skill match percentage for a specific job.
     *
     * @param userSkills User's skills
     * @param jobTitle Job title
     * @return Match percentage (0-100)
     */
    public static int calculateMatchPercentage(List<String> userSkills, String jobTitle) {
        List<String> requiredSkills = CareerData.getJobSkills(jobTitle);
        
        if (requiredSkills.isEmpty()) {
            return 0;
        }

        int matchingCount = 0;
        for (String skill : userSkills) {
            if (requiredSkills.contains(skill)) {
                matchingCount++;
            }
        }

        return (matchingCount * 100) / requiredSkills.size();
    }
}
