@echo off
REM Direct Java Compilation and Packaging
REM This script compiles Java source files without Maven

setlocal enabledelayedexpansion

SET PROJECT_DIR=%~dp0
SET SRC_DIR=%PROJECT_DIR%src\main\java
SET OUTPUT_DIR=%PROJECT_DIR%target\classes
SET JAR_DIR=%PROJECT_DIR%target\lib

echo ============================================
echo  Career Path Analyzer - Direct Build
echo ============================================

REM Create output directories
if not exist "%OUTPUT_DIR%" mkdir "%OUTPUT_DIR%"
if not exist "%JAR_DIR%" mkdir "%JAR_DIR%"

echo.
echo Step 1: Compiling Java source files...
cd /d "%SRC_DIR%"

REM Compile all Java files
javac -cp "%PROJECT_DIR%lib\*" -d "%OUTPUT_DIR%" -sourcepath "%SRC_DIR%" ^
    com\careerpath\models\User.java ^
    com\careerpath\models\JobRequirement.java ^
    com\careerpath\models\AnalysisResult.java ^
    com\careerpath\services\CareerData.java ^
    com\careerpath\services\ResumeProcessor.java ^
    com\careerpath\services\UserDatabase.java ^
    com\careerpath\servlets\LoginServlet.java ^
    com\careerpath\servlets\SignupServlet.java ^
    com\careerpath\servlets\ResumeAnalysisServlet.java ^
    com\careerpath\servlets\LogoutServlet.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
) else (
    echo Compilation failed!
    exit /b 1
)

echo.
echo ============================================
echo Build Complete - Classes available in:
echo %OUTPUT_DIR%
echo ============================================
echo.
echo Next: Install Apache Tomcat 9+
echo Download from: https://tomcat.apache.org/
echo.
