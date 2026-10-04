package com.careerpath.models;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * AnalysisResult model representing the analysis of a user's resume.
 * Contains extracted skills, resume score, recommendations, and career planning.
 */
public class AnalysisResult {
    private List<String> extractedSkills;
    private JobRequirement matchedJob;
    private int matchPercentage;
    
    // Resume Improvement Features
    private int resumeScore; // 0-100
    private List<String> improvementSuggestions;
    private List<String> atsOptimizationTips;
    private List<JobOpportunity> matchedJobs;
    private List<SkillRoadmap> skillRoadmaps;
    private List<String> interviewQuestionsForRole;
    private Map<String, String> careerProgression; // currentRole -> nextRole
    private List<String> missingSkills;
    private String nextCareerStep;

    public AnalysisResult(List<String> extractedSkills, JobRequirement matchedJob, int matchPercentage) {
        this.extractedSkills = new ArrayList<>(extractedSkills);
        this.matchedJob = matchedJob;
        this.matchPercentage = matchPercentage;
        this.improvementSuggestions = new ArrayList<>();
        this.atsOptimizationTips = new ArrayList<>();
        this.matchedJobs = new ArrayList<>();
        this.skillRoadmaps = new ArrayList<>();
        this.interviewQuestionsForRole = new ArrayList<>();
        this.careerProgression = new HashMap<>();
        this.missingSkills = new ArrayList<>();
        this.resumeScore = 0;
    }

    // Getters and Setters
    public List<String> getExtractedSkills() {
        return new ArrayList<>(extractedSkills);
    }

    public void setExtractedSkills(List<String> extractedSkills) {
        this.extractedSkills = new ArrayList<>(extractedSkills);
    }

    public JobRequirement getMatchedJob() {
        return matchedJob;
    }

    public void setMatchedJob(JobRequirement matchedJob) {
        this.matchedJob = matchedJob;
    }

    public int getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(int matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    // Resume Score Getters and Setters
    public int getResumeScore() { return resumeScore; }
    public void setResumeScore(int score) { this.resumeScore = score; }

    // Improvement Suggestions
    public List<String> getImprovementSuggestions() { return improvementSuggestions; }
    public void setImprovementSuggestions(List<String> suggestions) { this.improvementSuggestions = suggestions; }
    public void addImprovementSuggestion(String suggestion) { this.improvementSuggestions.add(suggestion); }

    // ATS Optimization Tips
    public List<String> getAtsOptimizationTips() { return atsOptimizationTips; }
    public void setAtsOptimizationTips(List<String> tips) { this.atsOptimizationTips = tips; }
    public void addAtsOptimizationTip(String tip) { this.atsOptimizationTips.add(tip); }

    // Matched Jobs
    public List<JobOpportunity> getMatchedJobs() { return matchedJobs; }
    public void setMatchedJobs(List<JobOpportunity> jobs) { this.matchedJobs = jobs; }
    public void addMatchedJob(JobOpportunity job) { this.matchedJobs.add(job); }

    // Skill Roadmaps
    public List<SkillRoadmap> getSkillRoadmaps() { return skillRoadmaps; }
    public void setSkillRoadmaps(List<SkillRoadmap> roadmaps) { this.skillRoadmaps = roadmaps; }
    public void addSkillRoadmap(SkillRoadmap roadmap) { this.skillRoadmaps.add(roadmap); }

    // Interview Questions
    public List<String> getInterviewQuestionsForRole() { return interviewQuestionsForRole; }
    public void setInterviewQuestionsForRole(List<String> questions) { this.interviewQuestionsForRole = questions; }
    public void addInterviewQuestion(String question) { this.interviewQuestionsForRole.add(question); }

    // Career Progression
    public Map<String, String> getCareerProgression() { return careerProgression; }
    public void setCareerProgression(Map<String, String> progression) { this.careerProgression = progression; }
    public void addCareerStep(String from, String to) { this.careerProgression.put(from, to); }

    // Missing Skills
    public List<String> getMissingSkills() { return missingSkills; }
    public void setMissingSkills(List<String> skills) { this.missingSkills = skills; }
    public void addMissingSkill(String skill) { this.missingSkills.add(skill); }

    // Next Career Step
    public String getNextCareerStep() { return nextCareerStep; }
    public void setNextCareerStep(String step) { this.nextCareerStep = step; }

    @Override
    public String toString() {
        return "AnalysisResult{" +
                "extractedSkills=" + extractedSkills +
                ", matchedJob=" + matchedJob.getTitle() +
                ", matchPercentage=" + matchPercentage +
                ", resumeScore=" + resumeScore +
                ", suggestions=" + improvementSuggestions.size() +
                '}';
    }
}
