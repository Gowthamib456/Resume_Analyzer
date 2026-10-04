# Career Path Analyzer - Java Web Application

A comprehensive resume analyzer and career path recommender built with **Java Servlets, JSP, and Java Collections Framework**.

## 📋 Project Overview

This is a **6th Semester Mini Project** that demonstrates core Java concepts:
- **Java Collections Framework** - HashMap, HashSet, ArrayList
- **Servlets** - Backend request processing
- **JSP** - Dynamic web page generation
- **String Handling** - Resume text parsing with regex
- **Object-Oriented Design** - Models, Services, and Servlets

## 🎯 Features

1. **User Authentication**
   - User Registration and Login
   - Session Management
   - Role-based Access (User/Admin)

2. **Resume Analysis**
   - Extract skills from resume text
   - Match against job requirements
   - Calculate match percentages
   - Identify missing skills

3. **Job Matching**
   - Multiple job profiles:
     - Java Developer
     - Frontend Developer
     - Data Scientist
   - Skill-based recommendations

4. **Learning Paths**
   - Customized learning resources for each skill
   - Based on matched job requirements

## 📁 Project Structure

```
career-path-analyzer/
├── src/
│   ├── main/
│   │   ├── java/com/careerpath/
│   │   │   ├── models/             # Data Models
│   │   │   │   ├── User.java
│   │   │   │   ├── JobRequirement.java
│   │   │   │   └── AnalysisResult.java
│   │   │   ├── servlets/           # Request Handlers
│   │   │   │   ├── LoginServlet.java
│   │   │   │   ├── SignupServlet.java
│   │   │   │   ├── ResumeAnalysisServlet.java
│   │   │   │   └── LogoutServlet.java
│   │   │   └── services/           # Business Logic
│   │   │       ├── CareerData.java
│   │   │       ├── ResumeProcessor.java
│   │   │       └── UserDatabase.java
│   │   └── webapp/
│   │       ├── index.jsp           # Login/Signup Page
│   │       ├── dashboard.jsp       # Main Application Page
│   │       └── WEB-INF/
│   │           └── web.xml         # Deployment Descriptor
│   └── test/
├── pom.xml                          # Maven Configuration
└── README.md                        # This File
```

## 🛠️ Technologies Used

- **Language:** Java 11+
- **Web Framework:** Servlets & JSP
- **Build Tool:** Maven
- **Server:** Apache Tomcat 9+
- **Collections:** HashMap, HashSet, ArrayList (Java Collections Framework)
- **Frontend:** HTML5, CSS3, JavaScript

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Apache Tomcat 9+

### Installation

1. **Clone/Download the project**
   ```bash
   cd "6th sem mini project"
   ```

2. **Build the project**
   ```bash
   mvn clean package
   ```

3. **Deploy to Tomcat**
   - Copy `target/careerpath.war` to Tomcat's `webapps` folder
   - Or use Maven plugin: `mvn tomcat7:run`

4. **Start Tomcat** (if not using Maven plugin)
   ```bash
   $CATALINA_HOME/bin/startup.sh   # Linux/Mac
   $CATALINA_HOME/bin/startup.bat  # Windows
   ```

5. **Access the application**
   - Open browser and go to: `http://localhost:8080/careerpath`

## 📝 Demo Credentials

**User Account:**
- Email: `krishnami721@gmail.com`
- Password: `password123`

**Admin Account:**
- Email: `admin@careerpath.ai`
- Password: `admin123`

## 💡 Key Java Concepts Demonstrated

### 1. Collections Framework
**File:** `src/main/java/com/careerpath/services/CareerData.java`
- **HashMap** - Job requirements mapping
- **HashSet** - Unique skills storage
- **ArrayList** - Dynamic skill lists

```java
// HashMap example
Map<String, List<String>> jobRequirements = new HashMap<>();
jobRequirements.put("Java Developer", new ArrayList<>(Arrays.asList(...)));

// HashSet example
Set<String> allSkills = new HashSet<>();
```

### 2. String Handling
**File:** `src/main/java/com/careerpath/services/ResumeProcessor.java`
- Regular expressions for text parsing
- String manipulation (toLowerCase, split, replace)
- Pattern matching for skill extraction

```java
public static Set<String> extractSkills(String resumeText) {
    String cleanedText = resumeText.toLowerCase();
    cleanedText = cleanedText.replaceAll("[^a-z0-9\\s]", " ");
    String[] words = cleanedText.split("\\s+");
    // ... skill matching logic
}
```

### 3. Servlets
**File:** `src/main/java/com/careerpath/servlets/`
- Request/Response handling
- Session management
- HTTP methods (GET, POST)
- JSON response generation

```java
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // Request processing logic
    }
}
```

### 4. JSP + JSTL
**File:** `src/main/webapp/`
- Dynamic page generation
- Form handling
- JavaScript integration
- Session attribute access

```jsp
<%
    User loggedInUser = (User) session.getAttribute("user");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
%>
```

## 🔄 Application Flow

1. **User Login/Registration**
   - Data stored in HashMap (UserDatabase)
   - Session created on successful login

2. **Resume Upload**
   - User pastes resume text
   - Sent to ResumeAnalysisServlet

3. **Skill Extraction**
   - ResumeProcessor parses text using regex
   - Skills stored in HashSet
   - Duplicates automatically removed

4. **Job Matching**
   - Compare user skills with job requirements
   - Calculate match percentages
   - Sort by best matches

5. **Results Display**
   - Show matched jobs with percentages
   - Display extracted skills
   - Highlight missing skills

## 🧪 Testing the Application

### Test Case 1: Resume Analysis
```
Resume Text: "I have 5 years of experience with Java, Spring, SQL, and Collections. 
I know HTML, CSS, and React as well."

Expected Results:
- Java Developer: ~80% match
- Frontend Developer: ~60% match
- Data Scientist: ~20% match
```

### Test Case 2: User Registration
- Email: `newuser@example.com`
- Password: `secure123`
- Confirm: `secure123`

## 📊 Database Design (Using Collections)

### Users HashMap
```
{
  "krishnami721@gmail.com": User(...),
  "admin@careerpath.ai": User(...),
  ...
}
```

### Job Requirements HashMap
```
{
  "Java Developer": ["Java", "Spring", "SQL", ...],
  "Frontend Developer": ["HTML", "CSS", "React", ...],
  ...
}
```

## 🔐 Security Features

- Password validation (minimum 6 characters)
- Session-based authentication
- HttpOnly cookies
- CSRF protection ready
- Input validation

## 📈 Future Enhancements

1. Database integration (MySQL/PostgreSQL)
2. File upload for resume
3. Admin panel for job management
4. Email notifications
5. Profile management
6. Skill proficiency levels
7. API endpoints (REST)

## 🤝 Contributing

This is a mini project for educational purposes. Feel free to extend it with additional features.

## 📧 Contact

For questions or suggestions, please contact the development team.

---

**Built with ❤️ using Java Collections Framework, Servlets, and JSP**
