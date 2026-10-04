package com.careerpath.services;

import java.util.*;

/**
 * CareerData service class using Java Collections.
 * Uses HashMap for storing job requirements and learning paths.
 * Demonstrates Java Collections Framework.
 */
public class CareerData {
    // HashMap: Key -> Job Title, Value -> List of required skills
    private static final Map<String, List<String>> jobRequirements = new HashMap<>();
    
    // HashMap: Key -> Skill, Value -> Learning resource
    private static final Map<String, String> learningPath = new HashMap<>();
    
    // Set for storing all possible skills
    private static final Set<String> allSkills = new HashSet<>();

    // Static initialization block
    static {
        // Initialize job requirements using Collections
        List<String> javaDeveloperSkills = new ArrayList<>(Arrays.asList(
            "Java", "Spring", "SQL", "Servlets", "Collections"
        ));
        jobRequirements.put("Java Developer", javaDeveloperSkills);

        List<String> frontendSkills = new ArrayList<>(Arrays.asList(
            "HTML", "CSS", "JavaScript", "React", "UI/UX"
        ));
        jobRequirements.put("Frontend Developer", frontendSkills);

        List<String> dataScientistSkills = new ArrayList<>(Arrays.asList(
            "Python", "Machine Learning", "Statistics", "SQL", "R"
        ));
        jobRequirements.put("Data Scientist", dataScientistSkills);

        // Initialize learning paths
        learningPath.put("Spring", "Learn at Baeldung.com or Spring.io documentation.");
        learningPath.put("SQL", "Practice on LeetCode Database section and W3Schools.");
        learningPath.put("React", "Follow the official React.dev tutorial and build a Todo app.");
        learningPath.put("Machine Learning", "Take the Andrew Ng course on Coursera.");
        learningPath.put("Java", "Oracle Java Documentation and JetBrains Academy.");
        learningPath.put("Servlets", "Java EE tutorials and Baeldung.");
        learningPath.put("Collections", "Java Collections Framework guide on GeeksforGeeks.");
        learningPath.put("HTML", "MDN Web Docs and freeCodeCamp.");
        learningPath.put("CSS", "CSS-Tricks and MDN Web Docs.");
        learningPath.put("JavaScript", "Eloquent JavaScript and JavaScript.info.");
        learningPath.put("UI/UX", "Google UX Design Professional Certificate on Coursera.");
        learningPath.put("Python", "Python.org and Real Python.");
        learningPath.put("Statistics", "Khan Academy Statistics and Probability.");
        learningPath.put("R", "R-bloggers and Swirl.");

        // Populate all skills Set
        allSkills.addAll(learningPath.keySet());
    }

    /**
     * Get all job requirements as a List.
     * Demonstrates conversion from Map to List using Collections.
     */
    public static List<com.careerpath.models.JobRequirement> getAllJobs() {
        List<com.careerpath.models.JobRequirement> jobs = new ArrayList<>();
        
        for (Map.Entry<String, List<String>> entry : jobRequirements.entrySet()) {
            jobs.add(new com.careerpath.models.JobRequirement(entry.getKey(), entry.getValue()));
        }
        
        return jobs;
    }

    /**
     * Get all possible skills as an array.
     * Demonstrates Set to Array conversion.
     */
    public static String[] getAllPossibleSkills() {
        return allSkills.toArray(new String[0]);
    }

    /**
     * Get a specific job's requirements.
     */
    public static List<String> getJobSkills(String jobTitle) {
        return new ArrayList<>(jobRequirements.getOrDefault(jobTitle, new ArrayList<>()));
    }

    /**
     * Get learning path for a specific skill.
     */
    public static String getLearningPath(String skill) {
        return learningPath.getOrDefault(skill, "No learning path found for: " + skill);
    }

    /**
     * Get all learning paths.
     */
    public static Map<String, String> getAllLearningPaths() {
        return new HashMap<>(learningPath);
    }

    /**
     * Get all job titles.
     */
    public static Set<String> getAllJobTitles() {
        return new HashSet<>(jobRequirements.keySet());
    }
}
