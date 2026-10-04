# Java Collections Framework - Implementation Guide

This document explains how Java Collections are used throughout the Career Path Analyzer project.

## 📚 Collections Used

### 1. HashMap - Key-Value Storage

**Location:** `CareerData.java`, `UserDatabase.java`

**Purpose:** Store key-value pairs for fast O(1) lookup

**Usage Examples:**

```java
// Job Requirements Storage
Map<String, List<String>> jobRequirements = new HashMap<>();
jobRequirements.put("Java Developer", Arrays.asList("Java", "Spring", "SQL"));

// User Database Storage
Map<String, User> users = new HashMap<>();
users.put("email@example.com", new User(...));

// Learning Paths Mapping
Map<String, String> learningPath = new HashMap<>();
learningPath.put("Java", "Oracle Java Documentation");
```

**Key Methods:**
- `put(K key, V value)` - Add or update entry
- `get(K key)` - Retrieve value
- `containsKey(K key)` - Check if key exists
- `remove(K key)` - Delete entry
- `keySet()` - Get all keys
- `values()` - Get all values
- `entrySet()` - Get all key-value pairs

---

### 2. HashSet - Unique Values

**Location:** `CareerData.java`, `ResumeProcessor.java`

**Purpose:** Store unique values, eliminate duplicates automatically

**Usage Examples:**

```java
// All Skills Storage (no duplicates)
Set<String> allSkills = new HashSet<>();
allSkills.add("Java");
allSkills.add("Python");
allSkills.add("Java"); // Not added (duplicate)

// Extracted Skills from Resume
Set<String> extractedSkills = ResumeProcessor.extractSkills(resumeText);
```

**Key Methods:**
- `add(E e)` - Add element (returns false if already exists)
- `contains(E e)` - Check if element exists
- `remove(E e)` - Delete element
- `size()` - Get number of unique elements
- `toArray()` - Convert to array
- `iterator()` - Iterate through elements

---

### 3. ArrayList - Dynamic List

**Location:** `CareerData.java`, `ResumeProcessor.java`, `UserDatabase.java`

**Purpose:** Store ordered collection of elements (can grow/shrink dynamically)

**Usage Examples:**

```java
// Individual Job Skills List
List<String> javaDeveloperSkills = new ArrayList<>(
    Arrays.asList("Java", "Spring", "SQL", "Servlets")
);

// User Skills List
List<String> userSkillsList = new ArrayList<>(extractedSkills);

// Analysis Results List
List<AnalysisResult> results = new ArrayList<>();
results.add(new AnalysisResult(...));
```

**Key Methods:**
- `add(E e)` - Add element at end
- `add(int index, E e)` - Add element at specific index
- `get(int index)` - Retrieve element
- `remove(int index)` - Delete element
- `size()` - Get number of elements
- `contains(E e)` - Check if contains
- `sort(Comparator)` - Sort using comparator

---

### 4. LinkedHashMap - Ordered HashMap

**Location:** `UserDatabase.java`

**Purpose:** HashMap that maintains insertion order

**Usage:**

```java
// Users stored in order of registration
Map<String, User> users = new LinkedHashMap<>();
```

---

## 🔄 Collection Operations & Patterns

### Converting Between Collections

```java
// Set to List
Set<String> skillsSet = new HashSet<>();
List<String> skillsList = new ArrayList<>(skillsSet);

// List to Set
List<String> jobList = Arrays.asList("Java", "Python");
Set<String> jobSet = new HashSet<>(jobList);

// Array to List
String[] skills = {"Java", "Python"};
List<String> skillsList = Arrays.asList(skills);

// Set to Array
Set<String> allSkills = new HashSet<>();
String[] skillsArray = allSkills.toArray(new String[0]);
```

### Filtering Collections

```java
// Get users by role
List<User> adminUsers = new ArrayList<>();
for (User user : users.values()) {
    if (user.getRole().equals("admin")) {
        adminUsers.add(user);
    }
}

// Or using Stream API (Java 8+)
List<User> adminUsers = users.values().stream()
    .filter(u -> u.getRole().equals("admin"))
    .collect(Collectors.toList());
```

### Sorting Collections

```java
// Sort analysis results by match percentage
List<AnalysisResult> results = /* ... */;
results.sort((a, b) -> Integer.compare(b.getMatchPercentage(), a.getMatchPercentage()));
```

### Iterating Collections

```java
// Using for-each loop
for (Map.Entry<String, List<String>> entry : jobRequirements.entrySet()) {
    String jobTitle = entry.getKey();
    List<String> skills = entry.getValue();
    // ...
}

// Using Iterator
Set<String> skills = new HashSet<>();
Iterator<String> iterator = skills.iterator();
while (iterator.hasNext()) {
    System.out.println(iterator.next());
}

// Using forEach
skills.forEach(skill -> System.out.println(skill));
```

---

## 🎯 Real-world Examples from Project

### Example 1: Resume Skill Extraction

```java
public static Set<String> extractSkills(String resumeText) {
    Set<String> extractedSkills = new HashSet<>();  // Use Set for uniqueness
    
    String cleanedText = resumeText.toLowerCase();
    String[] words = cleanedText.split("\\s+");
    
    String[] allSkills = CareerData.getAllPossibleSkills();
    
    // Add matching skills (no duplicates in Set)
    for (String word : words) {
        for (String skill : allSkills) {
            if (skill.toLowerCase().equals(word)) {
                extractedSkills.add(skill);  // Automatically eliminates duplicates
            }
        }
    }
    
    return extractedSkills;
}
```

### Example 2: Job Matching

```java
public static List<AnalysisResult> analyzeResume(String resumeText) {
    Set<String> userSkills = extractSkills(resumeText);
    List<String> userSkillsList = new ArrayList<>(userSkills);  // Convert Set to List
    
    List<JobRequirement> jobs = CareerData.getAllJobs();  // Get all jobs
    List<AnalysisResult> results = new ArrayList<>();  // Collect results
    
    // Calculate matches for each job
    for (JobRequirement job : jobs) {
        int matchCount = job.getMatchingSkillsCount(userSkillsList);
        int matchPercentage = (matchCount * 100) / job.getRequiredSkills().size();
        results.add(new AnalysisResult(userSkillsList, job, matchPercentage));
    }
    
    // Sort by match percentage (best first)
    results.sort((a, b) -> Integer.compare(b.getMatchPercentage(), a.getMatchPercentage()));
    
    return results;
}
```

### Example 3: User Database Operations

```java
public static List<User> getUsersByRole(String role) {
    List<User> result = new ArrayList<>();  // Dynamic list for results
    
    // Iterate through HashMap values
    for (User user : users.values()) {
        if (user.getRole().equals(role)) {
            result.add(user);
        }
    }
    
    return result;
}
```

---

## 📊 Collection Performance Comparison

| Operation | HashMap | HashSet | ArrayList |
|-----------|---------|---------|-----------|
| Add | O(1) | O(1) | O(1) amortized |
| Remove | O(1) | O(1) | O(n) |
| Search | O(1) | O(1) | O(n) |
| Space | Moderate | Moderate | Moderate |
| Order | No | No | Yes |

---

## 💡 Best Practices

1. **Use HashSet for unique values** - Eliminates duplicates automatically
2. **Use HashMap for fast lookups** - O(1) access time
3. **Use ArrayList for ordered data** - Maintains insertion order
4. **Initialize with size if known** - Improves performance
5. **Use generics for type safety** - `List<String>` instead of `List`
6. **Choose the right collection type** - Performance matters in large datasets

---

## 🔗 References

- [Java Collections Framework Documentation](https://docs.oracle.com/javase/tutorial/collections/)
- [HashMap vs HashSet vs ArrayList](https://www.geeksforgeeks.org/differences-between-hashmap-hashset-and-arraylist/)
- [Collection Performance](https://www.baeldung.com/java-collections-complexity)
