package com.careerpath.services;

import com.careerpath.models.JobOpportunity;
import java.util.*;

/**
 * Service for matching user skills to job opportunities
 */
public class JobMatcher {
    
    /**
     * Find matching jobs based on user skills
     */
    public static List<JobOpportunity> findMatchingJobs(List<String> userSkills, String userExperience) {
        List<JobOpportunity> jobs = new ArrayList<>();
        
        // Define available jobs in database
        List<JobOpportunity> allJobs = generateJobDatabase();
        
        // Match jobs and calculate percentages
        for (JobOpportunity job : allJobs) {
            double matchPercentage = calculateJobMatch(userSkills, job.getRequiredSkills());
            job.setMatchPercentage(matchPercentage);
            
            // Identify missing and matching skills
            List<String> missing = new ArrayList<>();
            List<String> matching = new ArrayList<>();
            
            for (String required : job.getRequiredSkills()) {
                if (hasSkill(userSkills, required)) {
                    matching.add(required);
                } else {
                    missing.add(required);
                }
            }
            
            job.setMissingSkills(missing);
            job.setMatchingSkills(matching);
            
            // Only include jobs with at least 40% match
            if (matchPercentage >= 40) {
                jobs.add(job);
            }
        }
        
        // Sort by match percentage descending
        jobs.sort((a, b) -> Double.compare(b.getMatchPercentage(), a.getMatchPercentage()));
        
        return jobs.subList(0, Math.min(5, jobs.size()));
    }
    
    /**
     * Calculate match percentage between user skills and job requirements
     */
    private static double calculateJobMatch(List<String> userSkills, List<String> jobRequirements) {
        if (jobRequirements.isEmpty()) return 0;
        
        int matched = 0;
        for (String required : jobRequirements) {
            if (hasSkill(userSkills, required)) {
                matched++;
            }
        }
        
        return (matched * 100.0) / jobRequirements.size();
    }
    
    /**
     * Check if user has a specific skill
     */
    private static boolean hasSkill(List<String> userSkills, String requiredSkill) {
        String required = requiredSkill.toLowerCase();
        for (String skill : userSkills) {
            if (skill.toLowerCase().contains(required) || required.contains(skill.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Generate job database with various opportunities
     */
    private static List<JobOpportunity> generateJobDatabase() {
        List<JobOpportunity> jobs = new ArrayList<>();
        
        // Job 1: Senior Java Developer
        JobOpportunity job1 = new JobOpportunity("Senior Java Developer", "TechCorp");
        job1.setDescription("Develop and maintain enterprise Java applications");
        job1.setRequiredSkills(Arrays.asList("Java", "Spring Boot", "REST APIs", "Database Design", "Microservices"));
        job1.setSeniority("senior");
        job1.setSalaryRange("120,000 - 180,000");
        jobs.add(job1);
        
        // Job 2: Full Stack Engineer
        JobOpportunity job2 = new JobOpportunity("Full Stack Engineer", "WebSolutions");
        job2.setDescription("Build complete web applications with modern tech stack");
        job2.setRequiredSkills(Arrays.asList("Java", "JavaScript", "React", "MySQL", "Docker"));
        job2.setSeniority("mid-level");
        job2.setSalaryRange("90,000 - 140,000");
        jobs.add(job2);
        
        // Job 3: DevOps Engineer
        JobOpportunity job3 = new JobOpportunity("DevOps Engineer", "CloudInfra");
        job3.setDescription("Manage cloud infrastructure and CI/CD pipelines");
        job3.setRequiredSkills(Arrays.asList("Docker", "Kubernetes", "AWS", "Linux", "Git"));
        job3.setSeniority("mid-level");
        job3.setSalaryRange("100,000 - 150,000");
        jobs.add(job3);
        
        // Job 4: Data Engineer
        JobOpportunity job4 = new JobOpportunity("Data Engineer", "DataDriven Inc");
        job4.setDescription("Design and build data processing pipelines");
        job4.setRequiredSkills(Arrays.asList("SQL", "Python", "Hadoop", "Spark", "Data Warehousing"));
        job4.setSeniority("mid-level");
        job4.setSalaryRange("110,000 - 160,000");
        jobs.add(job4);
        
        // Job 5: Frontend Developer
        JobOpportunity job5 = new JobOpportunity("Frontend Developer", "UIDesign Studio");
        job5.setDescription("Create responsive and interactive web interfaces");
        job5.setRequiredSkills(Arrays.asList("JavaScript", "React", "CSS", "HTML", "Web Performance"));
        job5.setSeniority("mid-level");
        job5.setSalaryRange("80,000 - 130,000");
        jobs.add(job5);
        
        // Job 6: Backend Developer
        JobOpportunity job6 = new JobOpportunity("Backend Developer", "SystemsWorks");
        job6.setDescription("Develop scalable backend services");
        job6.setRequiredSkills(Arrays.asList("Java", "Spring", "PostgreSQL", "Redis", "Messaging"));
        job6.setSeniority("mid-level");
        job6.setSalaryRange("85,000 - 135,000");
        jobs.add(job6);
        
        // Job 7: Cloud Architect
        JobOpportunity job7 = new JobOpportunity("Cloud Architect", "EnterpriseSystems");
        job7.setDescription("Design cloud solutions and infrastructure");
        job7.setRequiredSkills(Arrays.asList("AWS", "Cloud Architecture", "Security", "DevOps", "Networking"));
        job7.setSeniority("senior");
        job7.setSalaryRange("150,000 - 220,000");
        jobs.add(job7);
        
        // Job 8: Tech Lead
        JobOpportunity job8 = new JobOpportunity("Tech Lead", "InnovateLabs");
        job8.setDescription("Lead technical teams and architectural decisions");
        job8.setRequiredSkills(Arrays.asList("Java", "Leadership", "Architecture", "Communication", "Mentoring"));
        job8.setSeniority("senior");
        job8.setSalaryRange("130,000 - 190,000");
        jobs.add(job8);
        
        return jobs;
    }
}
