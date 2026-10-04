@echo off
REM Complete automation script to build, deploy, and run the Career Path app

setlocal enabledelayedexpansion

SET PROJECT_DIR=%~dp0
SET TOMCAT_HOME=C:\apache-tomcat-10.1.0
SET CONTEXT_PATH=careerpath

echo.
echo ╔════════════════════════════════════════════════╗
echo ║     Career Path - Automated Build & Deploy    ║
echo ╚════════════════════════════════════════════════╝
echo.

REM Step 1: Stop Tomcat
echo [Step 1] Stopping Tomcat...
taskkill /F /IM java.exe /T >nul 2>&1
timeout /t 2 >nul
echo ✓ Tomcat stopped

REM Step 2: Clean old deployment
echo.
echo [Step 2] Cleaning old deployment artifacts...
if exist "%TOMCAT_HOME%\webapps\%CONTEXT_PATH%.war" del /q "%TOMCAT_HOME%\webapps\%CONTEXT_PATH%.war"
if exist "%TOMCAT_HOME%\webapps\%CONTEXT_PATH%" rmdir /s /q "%TOMCAT_HOME%\webapps\%CONTEXT_PATH%"
if exist "%TOMCAT_HOME%\work\Catalina\localhost\%CONTEXT_PATH%" rmdir /s /q "%TOMCAT_HOME%\work\Catalina\localhost\%CONTEXT_PATH%"
echo ✓ Old deployment cleaned

REM Step 3: Run deploy script
echo.
echo [Step 3] Building and packaging WAR...
cd /d "%PROJECT_DIR%"
call deploy.bat >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Deployment script failed!
    pause
    exit /b 1
)
echo ✓ WAR built and deployed to Tomcat

REM Step 4: Verify WAR file
echo.
echo [Step 4] Verifying deployment...
if not exist "%TOMCAT_HOME%\webapps\%CONTEXT_PATH%.war" (
    echo ERROR: WAR file not found in %TOMCAT_HOME%\webapps\
    pause
    exit /b 1
)
echo ✓ WAR file verified: %TOMCAT_HOME%\webapps\%CONTEXT_PATH%.war

REM Step 5: Start Tomcat
echo.
echo [Step 5] Starting Tomcat...
set CATALINA_HOME=%TOMCAT_HOME%
cd /d "%TOMCAT_HOME%\bin"
call startup.bat
timeout /t 3 >nul
echo ✓ Tomcat startup initiated

REM Step 6: Wait for deployment
echo.
echo [Step 6] Waiting for application to deploy...
timeout /t 8 >nul

REM Step 7: Open browser
echo.
echo [Step 7] Opening application in browser...
start http://localhost:8080/%CONTEXT_PATH%

echo.
echo ╔════════════════════════════════════════════════╗
echo ║        APPLICATION READY!                      ║
echo ╚════════════════════════════════════════════════╝
echo.
echo URL: http://localhost:8080/%CONTEXT_PATH%
echo.
echo Login Credentials:
echo   Email: krishnami721@gmail.com
echo   Password: password123
echo.
echo Tomcat Console is running in a separate window
echo.
