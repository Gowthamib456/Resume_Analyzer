package com.careerpath.models;

import java.util.ArrayList;
import java.util.List;

/**
 * JobRequirement model representing a job position.
 * Uses Java Collections (ArrayList) for storing required skills.
 */
public class JobRequirement {
    private String title;
    private List<String> requiredSkills;

    public JobRequirement(String title, List<String> requiredSkills) {
        this.title = title;
        this.requiredSkills = new ArrayList<>(requiredSkills);
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getRequiredSkills() {
        return new ArrayList<>(requiredSkills);
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = new ArrayList<>(requiredSkills);
    }

    public void addSkill(String skill) {
        if (!requiredSkills.contains(skill)) {
            requiredSkills.add(skill);
        }
    }

    public int getMatchingSkillsCount(List<String> userSkills) {
        int count = 0;
        for (String skill : userSkills) {
            if (requiredSkills.contains(skill)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "JobRequirement{" +
                "title='" + title + '\'' +
                ", requiredSkills=" + requiredSkills +
                '}';
    }
}
