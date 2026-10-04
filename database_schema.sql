-- Career Path Analyzer Database Schema
-- Create this database and tables before running the application

-- Create Database
CREATE DATABASE IF NOT EXISTS careerpath_db;
USE careerpath_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Resumes Table
CREATE TABLE IF NOT EXISTS resumes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    resume_text LONGTEXT NOT NULL,
    file_name VARCHAR(255),
    file_size BIGINT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Resume Analysis Results Table
CREATE TABLE IF NOT EXISTS resume_analysis (
    id INT PRIMARY KEY AUTO_INCREMENT,
    resume_id INT NOT NULL,
    user_id INT NOT NULL,
    resume_score INT,
    matched_job_title VARCHAR(255),
    match_percentage INT,
    extracted_skills LONGTEXT,
    missing_skills LONGTEXT,
    improvement_suggestions LONGTEXT,
    analyzed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Job Matches Table
CREATE TABLE IF NOT EXISTS job_matches (
    id INT PRIMARY KEY AUTO_INCREMENT,
    analysis_id INT NOT NULL,
    user_id INT NOT NULL,
    job_title VARCHAR(255) NOT NULL,
    company VARCHAR(255) NOT NULL,
    match_percentage DECIMAL(5,2),
    seniority VARCHAR(50),
    salary_range VARCHAR(100),
    matching_skills LONGTEXT,
    missing_skills LONGTEXT,
    matched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (analysis_id) REFERENCES resume_analysis(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Skill Learning Path Table
CREATE TABLE IF NOT EXISTS skill_roadmaps (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    skill_name VARCHAR(100) NOT NULL,
    current_level VARCHAR(50),
    target_level VARCHAR(50),
    estimated_months INT,
    job_market_demand VARCHAR(50),
    salary_boost DECIMAL(5,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Interview Questions Table
CREATE TABLE IF NOT EXISTS interview_questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    analysis_id INT NOT NULL,
    user_id INT NOT NULL,
    job_role VARCHAR(255) NOT NULL,
    question TEXT NOT NULL,
    category VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (analysis_id) REFERENCES resume_analysis(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- User Career Progress Table
CREATE TABLE IF NOT EXISTS career_progress (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    current_role VARCHAR(255),
    target_role VARCHAR(255),
    progress_percentage INT,
    skills_acquired INT,
    skills_needed INT,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create Indexes for better performance
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_resume_user ON resumes(user_id);
CREATE INDEX idx_analysis_user ON resume_analysis(user_id);
CREATE INDEX idx_analysis_resume ON resume_analysis(resume_id);
CREATE INDEX idx_job_match_analysis ON job_matches(analysis_id);
CREATE INDEX idx_job_match_user ON job_matches(user_id);
CREATE INDEX idx_roadmap_user ON skill_roadmaps(user_id);
CREATE INDEX idx_interview_user ON interview_questions(user_id);
CREATE INDEX idx_career_user ON career_progress(user_id);

-- Sample data (optional)
-- Insert a test user
INSERT INTO users (email, password, name) 
VALUES ('krishnami721@gmail.com', 'password123', 'Krishna MI')
ON DUPLICATE KEY UPDATE name='Krishna MI';

-- Display tables created
SHOW TABLES;
