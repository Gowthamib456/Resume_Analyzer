package com.careerpath.models;

import java.util.*;

/**
 * Represents a skill roadmap with learning path and progression
 */
public class SkillRoadmap {
    private String skillName;
    private String currentLevel; // beginner, intermediate, advanced, expert
    private String targetLevel;
    private List<String> learningResources;
    private List<String> relatedJobs;
    private int estimatedLearningMonths;
    private String jobMarketDemand; // high, medium, low
    private double averageSalaryBoost; // percentage increase
    
    public SkillRoadmap(String skillName, String currentLevel) {
        this.skillName = skillName;
        this.currentLevel = currentLevel;
        this.targetLevel = "expert";
        this.learningResources = new ArrayList<>();
        this.relatedJobs = new ArrayList<>();
        this.jobMarketDemand = "medium";
        this.averageSalaryBoost = 0.0;
    }
    
    // Getters and Setters
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    
    public String getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(String currentLevel) { this.currentLevel = currentLevel; }
    
    public String getTargetLevel() { return targetLevel; }
    public void setTargetLevel(String targetLevel) { this.targetLevel = targetLevel; }
    
    public List<String> getLearningResources() { return learningResources; }
    public void setLearningResources(List<String> learningResources) { this.learningResources = learningResources; }
    
    public List<String> getRelatedJobs() { return relatedJobs; }
    public void setRelatedJobs(List<String> relatedJobs) { this.relatedJobs = relatedJobs; }
    
    public int getEstimatedLearningMonths() { return estimatedLearningMonths; }
    public void setEstimatedLearningMonths(int months) { this.estimatedLearningMonths = months; }
    
    public String getJobMarketDemand() { return jobMarketDemand; }
    public void setJobMarketDemand(String demand) { this.jobMarketDemand = demand; }
    
    public double getAverageSalaryBoost() { return averageSalaryBoost; }
    public void setAverageSalaryBoost(double boost) { this.averageSalaryBoost = boost; }
    
    @Override
    public String toString() {
        return skillName + " [" + currentLevel + " → " + targetLevel + "]";
    }
}
