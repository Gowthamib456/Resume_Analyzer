# Quick JDBC Database Setup Checklist

This is a **quick reference** for setting up the MySQL database for the Career Path Analyzer project.

## Prerequisites Checklist

- [ ] MySQL 8.0+ installed and running
- [ ] MySQL is accessible on `localhost:3306`
- [ ] You know the MySQL root password

---

## Setup Steps

### Step 1: Run Database Schema
Execute one of the following:

**Option A: Using MySQL Command Line**
```powershell
mysql -u root -p < database_schema.sql
```

**Option B: Using MySQL Workbench**
1. Open MySQL Workbench
2. Connect to your MySQL server
3. File → Open SQL Script
4. Select `database_schema.sql`
5. Click Execute (or Ctrl+Shift+Enter)

**Option C: Paste SQL Content**
1. Open MySQL Workbench or CLI
2. Open `database_schema.sql`
3. Copy all content
4. Paste into MySQL Workbench/CLI
5. Execute

### Step 2: Verify Database Creation
```sql
-- Copy-paste this into MySQL
USE careerpath_db;
SHOW TABLES;
```

You should see these 7 tables:
```
+-----------------------+
| Tables_in_careerpath_db |
+-----------------------+
| users                 |
| resumes               |
| resume_analysis       |
| job_matches           |
| skill_roadmaps        |
| interview_questions   |
| career_progress       |
+-----------------------+
```

### Step 3: Verify Sample Data
```sql
-- Check if test user exists
SELECT * FROM users;

-- Should see:
-- | 1 | krishnami721@gmail.com | password123 | Krishna | 2024-... | 2024-... |
```

---

## Configuration (Optional)

If your MySQL password is **NOT** "root":

1. Open file: `src/main/java/com/careerpath/database/DatabaseConnection.java`
2. Find line ~16: `private static final String DB_PASSWORD = "root";`
3. Change "root" to your actual password
4. Save file

### Example
```java
// Before:
private static final String DB_PASSWORD = "root";

// After:
private static final String DB_PASSWORD = "mySecurePassword";
```

---

## Rebuild and Deploy

After database setup:

```powershell
# Navigate to project directory
cd "C:\Users\user\OneDrive\Desktop\6th sem mini project"

# Clean build with JDBC support
C:\apache-maven-3.9.6\bin\mvn.cmd clean compile package -DskipTests

# Stop Tomcat
taskkill /F /IM java.exe

# Copy WAR to Tomcat
Copy-Item -Path "target\careerpath.war" -Destination "C:\apache-tomcat-10.1.0\webapps\" -Force

# Start Tomcat
C:\apache-tomcat-10.1.0\bin\startup.bat

# Wait 3-5 seconds for startup, then access:
# http://localhost:8080/careerpath
```

---

## Verification

### Check if JDBC is Working

After logging in and uploading a resume:

1. **Check MySQL Console**:
```sql
USE careerpath_db;
SELECT COUNT(*) FROM resume_analysis;
```
Should show: `1` (or higher if you did multiple uploads)

2. **Check Application Logs**:
```
✓ Database connection successful
✓ Analysis saved to database for user: your.email@example.com
```

3. **Check Tomcat Console** (C:\apache-tomcat-10.1.0\logs\catalina.out):
Should show database connection messages

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| "Access denied for user 'root'" | Update password in DatabaseConnection.java line 16 |
| "Connection refused" | Ensure MySQL is running: `net start MySQL80` |
| "Database careerpath_db doesn't exist" | Re-run database_schema.sql |
| Application still using in-memory | Stop Tomcat completely before restarting |
| Port 3306 already in use | Change MySQL port or stop other MySQL instances |

---

## Sample Test Data

### Pre-populated Test User
- **Email**: krishnami721@gmail.com
- **Password**: password123
- **Name**: Krishna

### Login Steps
1. Click "Login" button on home page
2. Enter email: `krishnami721@gmail.com`
3. Enter password: `password123`
4. Click "Login"
5. Upload resume and test analysis feature

---

## Database Features Now Active

✅ **Persistent User Accounts**
- Users stored in MySQL database
- Login data survives server restarts
- New users can register and are saved

✅ **Resume Storage**
- Uploaded resumes saved to database
- Accessible for future analysis
- Organized by user

✅ **Analysis History**
- Every analysis saved
- Track improvements over time
- View past recommendations

✅ **Job Matches**
- Job opportunities stored with skill matches
- Salary data persisted
- Track matched jobs

✅ **Career Progress**
- Track user progress over time
- Monitor skill acquisition
- Record career milestones

---

## Performance Notes

### Connection Pooling Active
- Max 10 concurrent connections
- Automatic connection cleanup
- Optimized for typical web usage

### Database Indexes Active
- Fast user lookups by email
- Quick analysis retrieval
- Efficient job matching queries

### What Gets Cached
- User authentication (5-10 min timeout)
- Analysis results (per session)
- Job matches (calculated fresh)

---

## Backup Your Database

Before making changes:

```powershell
# Backup database
mysqldump -u root -p careerpath_db > careerpath_backup.sql

# Restore backup
mysql -u root -p careerpath_db < careerpath_backup.sql
```

---

## Need Help?

1. Check the full guide: [JDBC_SETUP_GUIDE.md](JDBC_SETUP_GUIDE.md)
2. Review integration summary: [JDBC_INTEGRATION_SUMMARY.md](JDBC_INTEGRATION_SUMMARY.md)
3. Check Tomcat logs: `C:\apache-tomcat-10.1.0\logs\catalina.out`
4. Check MySQL errors: `mysql -u root -p` then `SHOW ERRORS;`

---

## What's Next?

Once JDBC is working:

1. ✅ Users can register and login persistently
2. ✅ Resumes are stored in database
3. ✅ Analysis results are saved
4. ✅ Job matches are tracked
5. 🔄 Optional: Add more career progression features
6. 🔄 Optional: Implement DAO pattern for better abstraction
7. 🔄 Optional: Add prepared statements for extra security

---

**JDBC Database Setup Complete!** 🎉

Your application now has persistent MySQL database support with connection pooling and automatic fallback to in-memory storage.
