# Career Path Analyzer - Technology Stack Explanation

## Project Overview
This is a **Full-Stack Java Web Application** that analyzes resumes and recommends career paths. It demonstrates enterprise-level Java development with proper architecture.

---

## Answers to Sir's Questions

### 1. WHERE IS JDBC? ✓
**Location:** `src/main/java/com/careerpath/database/`

**Files using JDBC:**
- `DatabaseConnection.java` - Main JDBC connection class
- `UserDatabase.java` - JDBC queries for user authentication
- `AnalysisResultDatabase.java` - JDBC queries for results storage
- `ResumeProcessor.java` - Queries resume data from database

**How JDBC is Used:**
```java
// DatabaseConnection.java uses JDBC with MySQL
private static final String DB_URL = "jdbc:mysql://localhost:3306/careerpath_db";
Connection conn = DatabaseConnection.getConnection(); // Gets JDBC Connection
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM users");
```

**Why We Use JDBC:**
- Direct database connectivity to MySQL
- Execute SQL queries and store/retrieve data
- Connection pooling with HikariCP for performance
- Handle user authentication and resume data storage

---

### 2. WHERE ARE SERVLETS? ✓
**Location:** `src/main/java/com/careerpath/servlets/`

**Files:**
- `LoginServlet.java` - Handles user login
- `SignupServlet.java` - Handles user registration
- `ResumeAnalysisServlet.java` - Processes resume uploads and analysis
- `LogoutServlet.java` - Handles user logout

**How Servlets Work:**
```java
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // 1. Get user input from frontend
        String email = request.getParameter("email");
        
        // 2. Call business logic (services)
        User user = UserDatabase.authenticateUser(email, password);
        
        // 3. Send JSON response back to frontend
        response.setContentType("application/json");
        out.println("{\"success\": true}");
    }
}
```

**How Servlets are Configured (web.xml):**
```xml
<servlet>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>com.careerpath.servlets.LoginServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/api/login</url-pattern>
</servlet-mapping>
```

**How Servlets Run:**
1. Browser sends HTTP request to `http://localhost:8080/careerpath/api/login`
2. Tomcat receives request and routes to `LoginServlet`
3. Servlet's `doPost()` method executes
4. Servlet sends JSON response back to browser

---

### 3. WHERE ARE JSP FILES? ✓
**Location:** `src/main/webapp/`

**Files:**
- `index.jsp` - Login page UI
- `dashboard.jsp` - Dashboard page UI
- `error.jsp` - Error page

**How JSP is Used:**
JSP is a **templating language** that combines Java + HTML. However, in this project, we use JSP **minimally** because we have a modern frontend.

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Career Path - Resume Analyzer</title>
</head>
<body>
    <h1>Welcome to Career Path Analyzer</h1>
    <!-- This JSP gets rendered by Tomcat and sent to browser -->
</body>
</html>
```

---

### 4. INSTEAD OF SWING, WHAT DID YOU USE? ✓
**Answer: React + TypeScript (Modern Web UI)**

| Feature | Swing | Your Project |
|---------|-------|--------------|
| **Type** | Desktop GUI | Web-based UI |
| **Language** | Java | React (TypeScript/JavaScript) |
| **Rendering** | Desktop Window | HTML/CSS in Browser |
| **Location** | `src/` (React App) | - |
| **Why Changed** | Swing is outdated for modern apps | React is industry-standard, scalable |

**Your Frontend Technology Stack:**
```
React + TypeScript:
├── src/App.tsx - Main React component
├── src/main.tsx - React entry point
├── src/index.css - Styling
├── vite.config.ts - Build configuration
└── src/services/ - Frontend services calling backend APIs
```

**Why React Instead of Swing:**
- ✓ Modern, responsive UI
- ✓ Works on all browsers (not just desktop)
- ✓ Industry standard for web development
- ✓ Better user experience
- ✓ Can be deployed as web app

---

### 5. HOW DO YOU RUN SERVLETS? ✓
Servlets **cannot run standalone**. They need an **Application Server (Tomcat)** to run.

**Step-by-Step Servlet Execution:**

1. **Compile Servlets to .class files:**
   ```bash
   mvn clean compile
   # Creates: target/classes/com/careerpath/servlets/LoginServlet.class
   ```

2. **Package as WAR file:**
   ```bash
   mvn package
   # Creates: target/careerpath.war
   ```

3. **Deploy to Tomcat:**
   ```bash
   # Copy .war file to Tomcat webapps folder
   cp target/careerpath.war $TOMCAT_HOME/webapps/
   ```

4. **Start Tomcat:**
   ```bash
   # Windows:
   catalina.bat start
   
   # Linux/Mac:
   catalina.sh start
   ```

5. **Access Servlet via Browser:**
   ```
   http://localhost:8080/careerpath/api/login
   # Tomcat routes to LoginServlet.doPost()
   ```

**Flow Diagram:**
```
Browser Request
    ↓
HTTP → localhost:8080/careerpath/api/login
    ↓
Tomcat (Application Server)
    ↓
Servlet Container finds matching servlet
    ↓
LoginServlet.doPost() executes
    ↓
JDBC calls to MySQL Database
    ↓
Returns JSON response
    ↓
Browser receives & displays
```

---

### 6. YOU HAVE TOMCAT, WHY USE MAVEN? ✓

**Maven is the Build Tool, Tomcat is the Runtime Server**

| Tool | Purpose | Responsibility |
|------|---------|-----------------|
| **Maven** | Build automation, dependency management | Compiles code, downloads libraries, packages WAR |
| **Tomcat** | Application server/container | Runs servlets and JSP at runtime |

**They Work Together:**
```
SOURCE CODE
    ↓ (Maven compiles)
.class FILES + DEPENDENCIES
    ↓ (Maven packages)
.WAR FILE
    ↓ (Deploy to Tomcat)
RUNNING APPLICATION
```

**Why Maven is Essential:**

1. **Dependency Management** - Automatically downloads libraries:
   ```xml
   <!-- pom.xml -->
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <version>8.0.33</version>
   </dependency>
   <!-- Maven downloads this automatically -->
   ```

2. **Compilation** - Compiles all Java code:
   ```bash
   mvn clean compile
   # Finds all .java files, compiles to .class
   ```

3. **Testing** - Runs unit tests:
   ```bash
   mvn test
   ```

4. **Packaging** - Creates deployable WAR:
   ```bash
   mvn package
   # Creates: target/careerpath.war for Tomcat
   ```

5. **Standardized Structure** - Organizes project:
   ```
   src/main/java/     → Java source code
   src/main/webapp/   → JSP & web resources
   src/test/          → Unit tests
   target/            → Compiled output
   ```

**Simple Analogy:**
- **Maven** = Chef (prepares ingredients, cooks)
- **Tomcat** = Restaurant (serves the meal)
- Without Chef, no meal for Restaurant!

---

## Collection Framework Usage ✓

**Where Collections are Used:**

1. **HashMap** - In-memory user storage (fallback):
   ```java
   private static final Map<String, User> users = new LinkedHashMap<>();
   users.put("email@domain.com", new User(...));
   ```

2. **ArrayList** - Store job requirements:
   ```java
   List<JobRequirement> jobs = new ArrayList<>();
   jobs.add(new JobRequirement("Java Developer", skills));
   ```

3. **Map** - Job skills mapping (React frontend):
   ```typescript
   jobRequirements: Map<string, string[]> = new Map([
       ["Java Developer", ["Java", "Spring", "SQL"]],
       ["Frontend Developer", ["HTML", "CSS", "React"]]
   ]);
   ```

---

## String Handling Usage ✓

**Where String Handling is Used:**

1. **User Input Validation:**
   ```java
   String email = request.getParameter("email").trim();
   if (email.isEmpty()) {
       // Handle empty string
   }
   ```

2. **Email & Password Processing:**
   ```java
   // Trim whitespace
   String cleanEmail = email.trim();
   
   // Validate format
   if (email.contains("@")) { ... }
   ```

3. **JSON Response Building:**
   ```java
   String jsonResponse = "{\"success\": true, \"message\": \"Login successful\"}";
   response.getWriter().println(jsonResponse);
   ```

---

## COMPLETE PROJECT ARCHITECTURE

```
FRONTEND (React/TypeScript)
├── index.html
├── src/App.tsx
├── src/main.tsx
├── vite.config.ts
└── HTTP API Calls to backend

        ↓ (REST API Calls)

SERVLET LAYER (Request Handler)
├── LoginServlet
├── SignupServlet
├── ResumeAnalysisServlet
└── LogoutServlet

        ↓ (Calls Services)

SERVICE LAYER (Business Logic)
├── UserDatabase (JDBC queries)
├── ResumeProcessor
├── CareerData
└── JobMatcher

        ↓ (SQL Queries)

DATABASE LAYER (JDBC)
├── DatabaseConnection (JDBC Connection Pool)
└── MySQL Database

```

---

## HOW TO RUN THE PROJECT

```bash
# 1. Build with Maven
mvn clean install

# 2. Deploy to Tomcat
mvn tomcat7:deploy
# OR manually copy target/careerpath.war to Tomcat webapps/

# 3. Start Tomcat
catalina.sh start  # or catalina.bat start on Windows

# 4. Build React frontend
npm install
npm run build

# 5. Access in browser
http://localhost:8080/careerpath/
```

---

## KEY TECHNOLOGIES SUMMARY

| Technology | Purpose | Location | Why Used |
|-----------|---------|----------|----------|
| **JDBC** | Database connectivity | `database/` | Query MySQL, store data |
| **Servlets** | Web request handler | `servlets/` | Process HTTP requests |
| **JSP** | Dynamic web pages | `webapp/` | Server-side templating |
| **Tomcat** | Application server | Runtime | Deploy & run servlets |
| **Maven** | Build automation | `pom.xml` | Compile, test, package |
| **React** | Frontend UI | `src/` | Modern web interface |
| **Collections** | Data storage | `models/`, `services/` | ArrayList, HashMap, Map |
| **String** | Text processing | Everywhere | Email, password, validation |
| **MySQL** | Database | Backend | Persistent data storage |

---

## Notes for Your Professor

✓ **JDBC**: Direct MySQL database connection with HikariCP connection pooling  
✓ **Servlets**: Handle all HTTP requests (login, signup, analysis)  
✓ **JSP**: Used for server-side HTML rendering  
✓ **Alternative to Swing**: React/TypeScript provides modern web UI  
✓ **Tomcat**: Application server where servlets run  
✓ **Maven**: Build tool that compiles, packages, and deploys to Tomcat  
✓ **Collections**: HashMap, ArrayList, Map used throughout  
✓ **String Handling**: Input validation, JSON parsing, response building
