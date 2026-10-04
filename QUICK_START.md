# 🚀 Quick Setup Without Maven or Chocolatey

Since Maven isn't installed, follow these **2 simple steps** to run the project:

## Step 1: Download & Install Apache Tomcat

### Windows:
1. Go to https://tomcat.apache.org/download-90.cgi
2. Download **"Binary Distributions" → ".zip"** (e.g., `apache-tomcat-9.0.x.zip`)
3. Extract to `C:\tomcat` (or any location)
4. Note the path for later

### Linux/Mac:
```bash
# Download
wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.96/bin/apache-tomcat-9.0.96.tar.gz

# Extract
tar xzf apache-tomcat-9.0.96.tar.gz -C /opt/
ln -s /opt/apache-tomcat-9.0.96 /opt/tomcat
```

---

## Step 2: Compile & Package with Tomcat

### Option A: Using Tomcat Manager (No Build Tools)

1. **Compile Java files:**
   ```bash
   cd "6th sem mini project"
   compile.bat
   ```

2. **Create WAR file manually:**
   - Compress `src/main/webapp/*` → `careerpath.war`
   - And include compiled classes from `target/classes`

3. **Deploy to Tomcat:**
   - Copy `careerpath.war` to `$TOMCAT_HOME/webapps/`

4. **Start Tomcat:**
   ```bash
   # Windows
   "C:\tomcat\bin\startup.bat"
   
   # Linux/Mac
   /opt/tomcat/bin/startup.sh
   ```

5. **Access Application:**
   ```
   http://localhost:8080/careerpath
   ```

---

### Option B: Quick Alternative (Embedded Server)

Use a simpler embedded Tomcat approach:

1. Install Maven properly from https://maven.apache.org/download.cgi
2. Add Maven to your Windows PATH
3. Then run: `mvn clean package && mvn tomcat7:run`

---

## 📋 Detailed Manual Setup (Step by Step)

### Step 1: Download Tomcat
- Visit: https://tomcat.apache.org/download-90.cgi
- Download: Apache Tomcat 9.0.x (latest stable - `.zip` for Windows)

### Step 2: Extract Tomcat
**Windows:**
```
C:\tomcat\
├── bin\
├── conf\
├── lib\
├── logs\
├── webapps\    ← Applications go here
├── work\
└── temp\
```

### Step 3: Prepare the Application

Run batch file to compile:
```batch
compile.bat
```

### Step 4: Create WAR File

Create folder structure and package:
```
careerpath.war
├── index.jsp
├── dashboard.jsp
├── error.jsp
├── WEB-INF/
│   ├── web.xml
│   └── classes/
│       └── com/careerpath/... (compiled classes)
└── resources/
```

Or simply zip the `src/main/webapp` folder with `target/classes` inside.

### Step 5: Deploy

Copy `careerpath.war` to `C:\tomcat\webapps\`

### Step 6: Start Tomcat

**Windows cmd (Run as Administrator):**
```batch
cd C:\tomcat\bin
startup.bat
```

**You should see:**
```
...
INFO: Server startup in X ms
```

### Step 7: Access Application

Open browser: **http://localhost:8080/careerpath**

---

## 🔐 Login Credentials

```
Email: krishnami721@gmail.com
Password: password123
```

---

## ⚡ Troubleshooting

| Issue | Solution |
|-------|----------|
| "Port 8080 in use" | Change port in `conf/server.xml` or kill process |
| "Webapp not deploying" | Check `logs/catalina.out` for errors |
| "Servlet not found" | Verify `web.xml` in `WEB-INF` folder |
| "JSP not rendering" | Ensure `.jsp` files are in root of webapp |
| "Classes not found" | Put compiled `.class` files in `WEB-INF/classes/` |

---

## 📊 Project Status

✅ **Java Source Code**: Compiled successfully
✅ **JSP Pages**: Ready for deployment
✅ **Configuration**: All setup in place
✅ **Ready for Tomcat**: Just needs deployment

---

## 🎯 Quick Summary

1. Download Tomcat 9+ from https://tomcat.apache.org
2. Extract to `C:\tomcat`
3. Compile Java: Run `compile.bat`
4. Package as WAR and copy to `tomcat/webapps/`
5. Start Tomcat: Run `startup.bat`
6. Open: http://localhost:8080/careerpath

---

**Need Maven?** Install from: https://maven.apache.org/download.cgi

Once Maven is installed:
```bash
mvn clean package
mvn tomcat7:run
```

Then access: **http://localhost:8080/careerpath** ✨
