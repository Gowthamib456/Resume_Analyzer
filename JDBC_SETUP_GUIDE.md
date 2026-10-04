# JDBC Database Setup Guide

This guide explains how to set up MySQL database for the Career Path Analyzer project.

## Prerequisites

1. **MySQL Server** installed and running
   - Download: https://dev.mysql.com/downloads/mysql/
   - Version: 8.0+

2. **Database Management Tools** (optional)
   - MySQL Workbench: https://dev.mysql.com/downloads/workbench/
   - or DBeaver: https://dbeaver.io/

## Setup Steps

### Step 1: Connect to MySQL

Open command prompt or MySQL Workbench and connect to MySQL:

```bash
mysql -u root -p
```

Enter your MySQL root password when prompted.

### Step 2: Create Database and Tables

Run the SQL script to create the database and all tables:

```bash
mysql -u root -p < database_schema.sql
```

Or paste the contents of `database_schema.sql` into MySQL Workbench and execute.

### Step 3: Verify Database Creation

```sql
USE careerpath_db;
SHOW TABLES;
```

You should see these tables:
- users
- resumes
- resume_analysis
- job_matches
- skill_roadmaps
- interview_questions
- career_progress

### Step 4: Configure Database Connection (Optional)

Edit `src/main/java/com/careerpath/database/DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/careerpath_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "root"; // Change this to your MySQL password
```

### Step 5: Build and Deploy

```powershell
cd "C:\Users\user\OneDrive\Desktop\6th sem mini project"
C:\apache-maven-3.9.6\bin\mvn.cmd clean compile package -DskipTests
```

### Step 6: Deploy to Tomcat

```powershell
Copy-Item -Path "target\careerpath.war" -Destination "C:\apache-tomcat-10.1.0\webapps\" -Force
C:\apache-tomcat-10.1.0\bin\startup.bat
```

## Database Features

### 1. **Users Table**
- Stores user credentials and profile information
- Supports registration and authentication

### 2. **Resumes Table**
- Stores uploaded resume text
- Tracks file name and size
- Links to user account

### 3. **Resume Analysis Results**
- Stores analysis scores and job matching data
- Persists extracted skills and suggestions
- Maintains analysis history

### 4. **Job Matches**
- Stores matching job opportunities
- Tracks skill matches and gaps
- Records salary ranges and seniority levels

### 5. **Skill Roadmaps**
- Tracks skill learning progress
- Stores proficiency levels and learning time estimates
- Records salary boost potential

### 6. **Interview Questions**
- Stores personalized interview questions
- Organizes by job role and question category

### 7. **Career Progress**
- Tracks user's career advancement
- Monitors skills acquired vs. needed
- Records progress percentage

## Fallback Mode

If MySQL database is unavailable:
- Application automatically falls back to **in-memory storage**
- All features continue to work normally
- Data is lost when application restarts
- Check `UserDatabase.getDatabaseStatus()` to verify connection

## Connection Pooling

The application uses **HikariCP** for efficient database connection management:

```java
- Maximum Pool Size: 10 connections
- Minimum Idle: 5 connections
- Connection Timeout: 30 seconds
- Idle Timeout: 10 minutes
- Max Lifetime: 30 minutes
```

Check pool statistics:
```java
DatabaseConnection.getPoolStats()
```

## Troubleshooting

### Issue: "Connection refused"
**Solution:** Ensure MySQL server is running
```bash
# On Windows
net start MySQL80

# On Mac
brew services start mysql

# On Linux
sudo service mysql start
```

### Issue: "Access denied for user 'root'"
**Solution:** Update password in DatabaseConnection.java:
```java
private static final String DB_PASSWORD = "your_actual_password";
```

### Issue: "Database 'careerpath_db' doesn't exist"
**Solution:** Re-run the SQL schema:
```bash
mysql -u root -p < database_schema.sql
```

### Issue: "Table already exists"
**Solution:** The SQL script includes `IF NOT EXISTS`, so it's safe to run multiple times.
To start fresh, drop the database:
```sql
DROP DATABASE careerpath_db;
```
Then re-run the schema script.

## Sample Queries

### View all users
```sql
SELECT * FROM users;
```

### View analysis history for a user
```sql
SELECT ra.*, r.uploaded_at FROM resume_analysis ra
JOIN resumes r ON ra.resume_id = r.id
WHERE ra.user_id = 1
ORDER BY ra.analyzed_at DESC;
```

### View job matches for specific analysis
```sql
SELECT * FROM job_matches WHERE analysis_id = 1;
```

### View user's skill roadmap
```sql
SELECT * FROM skill_roadmaps WHERE user_id = 1;
```

## Performance Optimization

### Indexes
The schema includes indexes for commonly queried fields:
- User email lookups
- Resume-to-user relationships
- Analysis-to-user relationships
- Job match queries

### Best Practices
1. Run analytics: `ANALYZE TABLE table_name;`
2. Optimize tables: `OPTIMIZE TABLE table_name;`
3. Monitor connection pool: `SELECT * FROM information_schema.PROCESSLIST;`

## Migration from In-Memory to Database

The application supports seamless migration:

1. Set up MySQL database (Steps 1-3 above)
2. Ensure `DatabaseConnection.java` has correct credentials
3. Restart Tomcat
4. Application will automatically detect database and use it
5. Previous in-memory data will be discarded (migration script available upon request)

## Backup and Restore

### Backup database
```bash
mysqldump -u root -p careerpath_db > careerpath_backup.sql
```

### Restore database
```bash
mysql -u root -p careerpath_db < careerpath_backup.sql
```

## Additional Resources

- MySQL Documentation: https://dev.mysql.com/doc/
- HikariCP Documentation: https://github.com/brettwooldridge/HikariCP
- JDBC Tutorial: https://docs.oracle.com/javase/tutorial/jdbc/

For questions or issues, refer to the project documentation or Java comments in the source code.
