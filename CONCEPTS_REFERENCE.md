# 📚 Concepts & Topics - File Reference Guide

This document maps each file to the Java concepts it demonstrates.

## 🎯 Core Concepts Mapping

### 1. **Java Collections Framework**

#### Files:
- [CareerData.java](/src/main/java/com/careerpath/services/CareerData.java)
- [ResumeProcessor.java](/src/main/java/com/careerpath/services/ResumeProcessor.java)
- [UserDatabase.java](/src/main/java/com/careerpath/services/UserDatabase.java)

#### Concepts Covered:
- **HashMap** - Key-value stores for:
  - Job requirements mapping
  - User authentication database
  - Learning resources mapping
  
- **HashSet** - Unique collections for:
  - Skill deduplication
  - Unique user emails
  - Unique job titles

- **ArrayList** - Dynamic lists for:
  - Job skill requirements
  - User collections
  - Analysis results

- **LinkedHashMap** - Ordered storage for:
  - Maintaining user registration order
  - Preserving insertion sequence

#### Key Methods Used:
- `put()`, `get()`, `remove()`, `containsKey()`
- `add()`, `contains()`, `remove()`, `forEach()`
- `toArray()`, `isEmpty()`, `size()`
- `stream()`, `filter()`, `collect()`

---

### 2. **Servlets**

#### Files:
- [LoginServlet.java](/src/main/java/com/careerpath/servlets/LoginServlet.java)
- [SignupServlet.java](/src/main/java/com/careerpath/servlets/SignupServlet.java)
- [ResumeAnalysisServlet.java](/src/main/java/com/careerpath/servlets/ResumeAnalysisServlet.java)
- [LogoutServlet.java](/src/main/java/com/careerpath/servlets/LogoutServlet.java)

#### Concepts Covered:
- **HTTP Request Handling**
  - GET and POST methods
  - Request parameters
  - Query strings

- **HTTP Response Processing**
  - Setting content type
  - Writing response body
  - Setting HTTP status codes

- **Session Management**
  - Creating sessions
  - Storing attributes
  - Session invalidation
  - Session timeout

- **JSON Response**
  - Building JSON strings
  - Error handling
  - Data serialization

#### Key Servlet Methods:
- `doGet()`, `doPost()`
- `getParameter()`, `getSession()`
- `setAttribute()`, `getAttribute()`
- `getWriter()`, `setContentType()`

---

### 3. **JSP (Java Server Pages)**

#### Files:
- [index.jsp](/src/main/webapp/index.jsp) - Login/Signup
- [dashboard.jsp](/src/main/webapp/dashboard.jsp) - Main Application
- [error.jsp](/src/main/webapp/error.jsp) - Error Handling

#### Concepts Covered:
- **JSP Directives**
  - `<%@ page %>` - Page settings and imports
  - `<%@ import %>` - Java imports

- **JSP Scriptlets**
  - `<% %>` - Java code blocks
  - Conditional logic
  - Session attribute access

- **JSP Expressions**
  - `<%= %>` - Output values
  - String interpolation

- **HTML Form Integration**
  - Form submission
  - Input handling
  - JavaScript interaction

- **Session Access**
  - User object retrieval
  - Authentication checks
  - Redirect logic

#### Features Demonstrated:
```jsp
<%@ page import="com.careerpath.models.User" %>
<%
    User loggedInUser = (User) session.getAttribute("user");
    if (loggedInUser == null) {
        response.sendRedirect(...);
    }
%>
<%= loggedInUser.getName() %>
```

---

### 4. **String Handling**

#### Files:
- [ResumeProcessor.java](/src/main/java/com/careerpath/services/ResumeProcessor.java)
- [LoginServlet.java](/src/main/java/com/careerpath/servlets/LoginServlet.java)
- [SignupServlet.java](/src/main/java/com/careerpath/servlets/SignupServlet.java)

#### Concepts Covered:
- **String Methods**
  - `toLowerCase()` - Convert to lowercase
  - `split()` - Split by delimiter
  - `replace()` - Replace characters
  - `replaceAll()` - Regex replacement
  - `trim()` - Remove whitespace
  - `equals()` - String comparison

- **Regular Expressions**
  - `replaceAll("[^a-z0-9\\s]", " ")` - Remove special characters
  - `split("\\s+")` - Split by whitespace
  - Pattern matching for skills

- **String Validation**
  - Empty check
  - Length validation
  - Format validation

- **String Manipulation**
  - Concatenation with `+`
  - String builders for JSON
  - Encoding with `encodeURIComponent()`

#### Key Methods:
```java
String cleaned = text.toLowerCase()
                      .replaceAll("[^a-z0-9\\s]", " ")
                      .trim();

String[] words = cleaned.split("\\s+");

if (text == null || text.isEmpty()) {
    // validation
}
```

---

## 📁 Model Classes - Data Structures

### [User.java](/src/main/java/com/careerpath/models/User.java)
**Represents:** User account data
**Contains:**
```java
- email: String
- name: String
- password: String
- role: String (user/admin)
```
**Concepts:** Encapsulation, Getters/Setters

### [JobRequirement.java](/src/main/java/com/careerpath/models/JobRequirement.java)
**Represents:** Job position with skills
**Contains:**
```java
- title: String
- requiredSkills: List<String> (ArrayList)
```
**Concepts:** Collections usage, List manipulation

### [AnalysisResult.java](/src/main/java/com/careerpath/models/AnalysisResult.java)
**Represents:** Resume analysis output
**Contains:**
```java
- extractedSkills: List<String>
- matchedJob: JobRequirement
- matchPercentage: int
```
**Concepts:** Data aggregation, Result modeling

---

## ⚙️ Service Layer - Business Logic

### [CareerData.java](/src/main/java/com/careerpath/services/CareerData.java)
**Demonstrates:**
- HashMap initialization and usage
- HashSet for unique values
- Collections conversion (Map→List, Set→Array)
- Static initialization block
- **Collections: HashMap, HashSet, ArrayList**

### [ResumeProcessor.java](/src/main/java/com/careerpath/services/ResumeProcessor.java)
**Demonstrates:**
- String regex processing
- Skills extraction algorithm
- Resume analysis logic
- Resume-to-job matching
- **Concepts: String handling, Collections operations, Algorithms**

### [UserDatabase.java](/src/main/java/com/careerpath/services/UserDatabase.java)
**Demonstrates:**
- HashMap as in-memory database
- CRUD operations
- User authentication
- Role-based filtering
- **Concepts: Collections, Authentication, Data persistence**

---

## 🔗 Configuration Files

### [web.xml](/src/main/webapp/WEB-INF/web.xml)
**Purpose:** Deployment descriptor
**Demonstrates:**
- Servlet mapping
- URL patterns
- Session configuration
- Error page handling
- Welcome files

### [pom.xml](/pom.xml)
**Purpose:** Maven project configuration
**Demonstrates:**
- Dependency management
- Build plugins
- Packaging (WAR)
- Compilation settings
- Test framework setup

---

## 📊 Concept-to-File Quick Reference

| Concept | Primary Files | Secondary Files |
|---------|---------------|-----------------|
| Collections | CareerData, UserDatabase | ResumeProcessor, JobRequirement |
| Servlets | LoginServlet, SignupServlet | ResumeAnalysisServlet, LogoutServlet |
| JSP | index.jsp, dashboard.jsp | error.jsp, web.xml |
| String Handling | ResumeProcessor | LoginServlet, SignupServlet |
| Session Management | LoginServlet, LogoutServlet | dashboard.jsp |
| OOP Design | All Model Classes | All Service Classes |
| Algorithms | ResumeProcessor | CareerData |

---

## 🎓 Learning Outcomes

After studying this project, you'll understand:

✅ **Java Collections Framework**
- When to use HashMap vs HashSet vs ArrayList
- Collection initialization and iteration
- Converting between collection types
- Performance characteristics

✅ **Servlets & Web Hierarchy**
- Servlet lifecycle (init, service, destroy)
- Request/response cycle
- Session management
- HTTP status codes

✅ **JSP Template Language**
- Mixing Java and HTML
- Accessing session data
- Redirects and forwards
- Error handling

✅ **String Processing**
- Regular expressions in Java
- Common string operations
- String validation patterns
- Text parsing algorithms

✅ **Web Application Architecture**
- MVC pattern (Models, Services, Servlets)
- Separation of concerns
- Business logic layer
- Data access layer

---

## 🔄 Data Flow Example

```
User Input (JSP Form)
        ↓
HTTP Request (Servlet)
        ↓
String Validation & Processing (ResumeProcessor)
        ↓
Collections Processing (CareerData, UserDatabase)
        ↓
Build Response (JSON from Servlet)
        ↓
Display in JSP (JavaScript parsing)
```

---

## 📖 Suggested Learning Path

1. **Start Here:** Read [JAVA_README.md](JAVA_README.md) for overview
2. **Collections:** Study [COLLECTIONS_GUIDE.md](COLLECTIONS_GUIDE.md) + CareerData.java
3. **Servlets:** Review LoginServlet → SignupServlet → ResumeAnalysisServlet
4. **JSP:** Examine index.jsp → dashboard.jsp
5. **Integration:** Trace full flow: User Input → Servlet → Service → Response
6. **Build:** Follow [SETUP_GUIDE.md](SETUP_GUIDE.md) to build and run

---

## 💡 Key Files for Each Topic

### For Collections Assignment:
- CareerData.java ⭐⭐⭐
- UserDatabase.java ⭐⭐
- ResumeProcessor.java ⭐

### For Servlets Assignment:
- LoginServlet.java ⭐⭐⭐
- ResumeAnalysisServlet.java ⭐⭐
- web.xml ⭐⭐

### For String Handling Assignment:
- ResumeProcessor.java ⭐⭐⭐
- LoginServlet.java (validation) ⭐

### For JSP Assignment:
- dashboard.jsp ⭐⭐⭐
- index.jsp ⭐⭐
- error.jsp ⭐

---

**Total Lines of Code: ~2500+ lines of well-documented Java code**

Each file includes detailed comments explaining concepts and implementations.
