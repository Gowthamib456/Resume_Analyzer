@echo off
REM Complete Deployment Script for Career Path Application
REM This script packages the application and deploys to Tomcat

setlocal enabledelayedexpansion

SET PROJECT_DIR=%~dp0
SET SRC_JAVA=%PROJECT_DIR%src\main\java
SET WEBAPP_DIR=%PROJECT_DIR%src\main\webapp
SET TOMCAT_HOME=C:\apache-tomcat-10.1.0
SET DEPLOY_DIR=%PROJECT_DIR%deploy_careerpath
SET BUILD_DIR=%PROJECT_DIR%build\classes

echo.
echo ╔════════════════════════════════════════════════╗
echo ║  Career Path - Deployment to Tomcat           ║
echo ╚════════════════════════════════════════════════╝
echo.

REM Step 1: Get Servlet API JARs from Tomcat
echo [Step 1] Setting up Servlet API classpath...
SET SERVLET_JARS=%TOMCAT_HOME%\lib\servlet-api.jar;%TOMCAT_HOME%\lib\jsp-api.jar
if not exist "%TOMCAT_HOME%\lib\servlet-api.jar" (
    echo Getting alternative Servlet JARs from Tomcat...
    for %%F in (%TOMCAT_HOME%\lib\*servlet*.jar) do set SERVLET_JARS=!SERVLET_JARS!;%%F
)
echo ✓ Servlet classpath ready!
SET APP_JARS=%PROJECT_DIR%lib\mysql-connector-java-8.0.33.jar;%PROJECT_DIR%lib\HikariCP-5.0.1.jar;%PROJECT_DIR%lib\json-20240303.jar;%PROJECT_DIR%lib\slf4j-api-1.7.36.jar;%PROJECT_DIR%lib\slf4j-simple-1.7.36.jar

REM Step 2: Compile ALL Java files including Servlets
echo.
echo [Step 2] Compiling ALL Java source files (including Servlets)...
if exist "%BUILD_DIR%" rmdir /s /q "%BUILD_DIR%"
mkdir "%BUILD_DIR%"
cd /d "%SRC_JAVA%"

javac -cp "%SERVLET_JARS%;%APP_JARS%" -sourcepath . -d "%BUILD_DIR%" ^
    com/careerpath/models/User.java ^
    com/careerpath/models/JobRequirement.java ^
    com/careerpath/models/AnalysisResult.java ^
    com/careerpath/services/CareerData.java ^
    com/careerpath/services/ResumeProcessor.java ^
    com/careerpath/services/UserDatabase.java ^
    com/careerpath/servlets/LoginServlet.java ^
    com/careerpath/servlets/SignupServlet.java ^
    com/careerpath/servlets/ResumeAnalysisServlet.java ^
    com/careerpath/servlets/LogoutServlet.java

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Compilation failed!
    exit /b 1
)
echo ✓ Compilation successful!

REM Step 2: Create deployment structure
echo.
echo [Step 2] Creating deployment structure...
if exist "%DEPLOY_DIR%" rmdir /s /q "%DEPLOY_DIR%"
mkdir "%DEPLOY_DIR%"
mkdir "%DEPLOY_DIR%\WEB-INF"
mkdir "%DEPLOY_DIR%\WEB-INF\classes"
mkdir "%DEPLOY_DIR%\WEB-INF\lib"

echo ✓ Deployment folders created!

REM Step 3: Copy JSP files
echo.
echo [Step 3] Copying JSP files...
copy "%WEBAPP_DIR%\index.jsp" "%DEPLOY_DIR%\" >nul
copy "%WEBAPP_DIR%\dashboard.jsp" "%DEPLOY_DIR%\" >nul
copy "%WEBAPP_DIR%\error.jsp" "%DEPLOY_DIR%\" >nul

echo ✓ JSP files copied!

REM Step 4: Copy web.xml
echo.
echo [Step 4] Copying web.xml...
copy "%WEBAPP_DIR%\WEB-INF\web.xml" "%DEPLOY_DIR%\WEB-INF\" >nul

echo ✓ web.xml copied!

REM Step 5: Copy compiled classes
echo.
echo [Step 5] Copying compiled Java classes...
xcopy "%BUILD_DIR%\com" "%DEPLOY_DIR%\WEB-INF\classes\com" /E /I /Y >nul
copy "%PROJECT_DIR%lib\*.jar" "%DEPLOY_DIR%\WEB-INF\lib\" >nul

echo ✓ Classes copied!

REM Step 6: Skip Servlet API libraries
echo.
echo [Step 6] Skipping Servlet API library packaging...
del "%DEPLOY_DIR%\WEB-INF\lib\*servlet*.jar" >nul 2>&1
echo ✓ Servlet/JSP API libraries are provided by Tomcat and should not be bundled inside the WAR.

REM Step 7: Create WAR file
echo.
echo [Step 7] Creating WAR file...
cd /d "%PROJECT_DIR%"

REM Delete old WAR if exists
if exist "%PROJECT_DIR%careerpath.war" del "%PROJECT_DIR%careerpath.war"

REM Create WAR using jar to preserve correct entry separators
where jar >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: jar command not found. Ensure JDK bin is on PATH.
    exit /b 1
)
jar -cvf "%PROJECT_DIR%careerpath.war" -C "%DEPLOY_DIR%" .

if %ERRORLEVEL% EQU 0 (
    echo ✓ WAR file created successfully!
) else (
    echo ERROR: Failed to create WAR file!
    exit /b 1
)

REM Step 8: Deploy to Tomcat
echo.
echo [Step 8] Deploying to Tomcat...

if not exist "%TOMCAT_HOME%\webapps\" (
    echo ERROR: Tomcat not found at %TOMCAT_HOME%
    echo Please update TOMCAT_HOME in this script
    exit /b 1
)

copy "%PROJECT_DIR%careerpath.war" "%TOMCAT_HOME%\webapps\" >nul

echo ✓ WAR file deployed to Tomcat!

REM Step 9: Display deployment complete message
echo.
echo ╔════════════════════════════════════════════════╗
echo ║    FIXED DEPLOYMENT COMPLETE!                 ║
echo ╚════════════════════════════════════════════════╝
echo.
echo.
echo IMPORTANT: Clear Tomcat cache
echo 1. Stop Tomcat (Ctrl+C in startup window)
echo 2. Delete: %TOMCAT_HOME%\work\Catalina\localhost\careerpath
echo 3. Delete: %TOMCAT_HOME%\webapps\careerpath (folder)
echo.
echo Then:
echo 4. Start Tomcat: %TOMCAT_HOME%\bin\startup.bat
echo 5. Wait 15 seconds
echo 6. Open: http://localhost:8080/careerpath
echo.
echo Login Credentials:
echo   Email: krishnami721@gmail.com
echo   Password: password123
echo.
echo WAR file: %PROJECT_DIR%careerpath.war
echo.
