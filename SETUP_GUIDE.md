# 🚀 Setup & Installation Guide - Career Path Analyzer

## Quick Start - 5 Minutes

### Step 1: Prerequisites Check

Verify you have the required software:

```bash
# Check Java version (should be 11 or higher)
java -version

# Check Maven version
mvn -version
```

**If missing:**
- [Java 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [Apache Tomcat 9+](https://tomcat.apache.org/download-90.cgi)

---

## Option 1: Running with Maven (Easiest)

### Windows

```bash
cd "6th sem mini project"
mvn clean package
mvn tomcat7:run
```

Then open: **http://localhost:8080/careerpath**

### Linux/Mac

```bash
cd "6th sem mini project"
./build.sh
mvn tomcat7:run
```

---

## Option 2: Manual Build & Deploy to Tomcat

### Step 1: Build the Project

**Windows:**
```bash
build.bat
```

**Linux/Mac:**
```bash
chmod +x build.sh
./build.sh
```

### Step 2: Setup Tomcat

1. Download [Apache Tomcat 9+](https://tomcat.apache.org/)
2. Extract to a location (e.g., `C:\tomcat` or `/opt/tomcat`)

### Step 3: Deploy Application

1. Copy `target/careerpath.war` from project to `$CATALINA_HOME/webapps/`

   **Windows:**
   ```
   copy target\careerpath.war "C:\tomcat\webapps\"
   ```

   **Linux/Mac:**
   ```bash
   cp target/careerpath.war /opt/tomcat/webapps/
   ```

### Step 4: Start Tomcat

**Windows:**
```batch
cd C:\tomcat\bin
startup.bat
```

**Linux/Mac:**
```bash
cd /opt/tomcat/bin
./startup.sh
```

### Step 5: Access Application

Open your browser: **http://localhost:8080/careerpath**

---

## Troubleshooting

### Issue: Maven not found
```bash
# Add Maven to PATH or use full path
# Windows: C:\path\to\maven\bin\mvn
# Linux/Mac: /path/to/maven/bin/mvn
```

### Issue: Port 8080 already in use
```bash
# Change port in pom.xml or use different port:
mvn tomcat7:run -Dtomcat.port=9090
```

### Issue: Build fails
```bash
# Clean and rebuild
mvn clean
mvn package -DskipTests
```

### Issue: Cannot find servlet class
- Verify `web.xml` has correct servlet mappings
- Check package structure: `com/careerpath/servlets/`
- Rebuild and redeploy

---

## Login Credentials

| Role | Email | Password |
|------|-------|----------|
| User | `krishnami721@gmail.com` | `password123` |
| Admin | `admin@careerpath.ai` | `admin123` |
| Test | `john@example.com` | `john123` |

---

## Project Structure

```
6th sem mini project/
├── src/
│   ├── main/
│   │   ├── java/com/careerpath/
│   │   │   ├── models/
│   │   │   │   ├── User.java
│   │   │   │   ├── JobRequirement.java
│   │   │   │   └── AnalysisResult.java
│   │   │   ├── servlets/
│   │   │   │   ├── LoginServlet.java
│   │   │   │   ├── SignupServlet.java
│   │   │   │   ├── ResumeAnalysisServlet.java
│   │   │   │   └── LogoutServlet.java
│   │   │   └── services/
│   │   │       ├── CareerData.java
│   │   │       ├── ResumeProcessor.java
│   │   │       └── UserDatabase.java
│   │   └── webapp/
│   │       ├── index.jsp
│   │       ├── dashboard.jsp
│   │       ├── error.jsp
│   │       └── WEB-INF/
│   │           └── web.xml
│   └── test/
├── pom.xml
├── build.bat
├── build.sh
├── JAVA_README.md
├── COLLECTIONS_GUIDE.md
├── SETUP_GUIDE.md
└── README.md
```

---

## Maven Commands Reference

```bash
# Clean previous build
mvn clean

# Compile code
mvn compile

# Run tests
mvn test

# Package as WAR
mvn package

# Build without tests
mvn clean package -DskipTests

# Run with embedded Tomcat
mvn tomcat7:run

# Deploy to Tomcat
mvn cargo:deploy

# Generate project documentation
mvn javadoc:javadoc
```

---

## Testing the Application

### Test Case 1: User Login
1. Go to http://localhost:8080/careerpath
2. Use credentials: `krishnami721@gmail.com` / `password123`
3. Click Login

### Test Case 2: User Signup
1. Click "Sign Up" button
2. Fill form:
   - Name: John Developer
   - Email: john.dev@example.com
   - Password: Developer123
   - Confirm: Developer123
3. Click Sign Up

### Test Case 3: Resume Analysis
1. Login with valid credentials
2. Paste sample resume:
   ```
   I am a Java developer with 5 years of experience.
   Skills: Java, Spring Framework, SQL, Collections, Servlets
   Experience with REST APIs and microservices.
   ```
3. Click "Analyze Resume"
4. View results

---

## Project Concepts Demonstrated

✅ **Java Collections Framework**
- HashMap for key-value storage
- HashSet for unique values
- ArrayList for dynamic lists
- Collections sorting and filtering

✅ **Servlets & JSP**
- Request/response handling
- Session management
- Form processing
- JSON responses

✅ **String Handling**
- Regular expressions (regex)
- Text parsing and splitting
- String manipulation methods

✅ **Object-Oriented Design**
- Model classes
- Service layer
- Separation of concerns

---

## IDE Setup

### IntelliJ IDEA
1. File → Open → Select project folder
2. Set project SDK to Java 11+
3. Maven should auto-configure
4. Run → Edit Configurations → Add Tomcat Server

### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Select root folder
3. Eclipse will import and configure

### VS Code
1. Install Extension Pack for Java
2. Open project folder
3. Let VS Code auto-configure Maven

---

## Performance Tips

- Use `mvn clean package -DskipTests` for faster builds during development
- Set Maven heap size: `set MAVEN_OPTS=-Xmx512m` (Windows) or `export MAVEN_OPTS=-Xmx512m` (Linux/Mac)
- Use SSD for better compilation times

---

## Additional Resources

- [Apache Tomcat Documentation](https://tomcat.apache.org/tomcat-9.0-doc/)
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [Java Servlets & JSP](https://docs.oracle.com/cd/E17802_01/products/products/servlet/index.html)
- [Java Collections Framework](https://docs.oracle.com/javase/tutorial/collections/)

---

**Ready to build? Start with Step 1 above! 🚀**
