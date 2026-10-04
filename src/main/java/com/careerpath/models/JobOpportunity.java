package com.careerpath.models;

import java.util.*;

/**
 * Represents a job opportunity with matching details
 */
public class JobOpportunity {
    private String jobTitle;
    private String company;
    private String description;
    private List<String> requiredSkills;
    private double matchPercentage;
    private String seniority; // entry-level, mid-level, senior, lead
    private String salaryRange;
    private List<String> missingSkills;
    private List<String> matchingSkills;
    
    public JobOpportunity(String jobTitle, String company) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.requiredSkills = new ArrayList<>();
        this.missingSkills = new ArrayList<>();
        this.matchingSkills = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> skills) { this.requiredSkills = skills; }
    
    public double getMatchPercentage() { return matchPercentage; }
    public void setMatchPercentage(double percentage) { this.matchPercentage = percentage; }
    
    public String getSeniority() { return seniority; }
    public void setSeniority(String seniority) { this.seniority = seniority; }
    
    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String range) { this.salaryRange = range; }
    
    public List<String> getMissingSkills() { return missingSkills; }
    public void setMissingSkills(List<String> skills) { this.missingSkills = skills; }
    
    public List<String> getMatchingSkills() { return matchingSkills; }
    public void setMatchingSkills(List<String> skills) { this.matchingSkills = skills; }
    
    @Override
    public String toString() {
        return jobTitle + " at " + company + " (" + String.format("%.0f", matchPercentage) + "% match)";
    }
}
