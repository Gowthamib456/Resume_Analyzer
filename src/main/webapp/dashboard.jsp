<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.careerpath.models.User" %>
<%
    // Check if user is logged in
    User loggedInUser = (User) session.getAttribute("user");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Career Path</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
        }
        
        .navbar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }
        
        .navbar h1 { font-size: 24px; font-weight: 700; }
        .navbar .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
        }
        .navbar button {
            background: rgba(255, 255, 255, 0.25);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s;
        }
        .navbar button:hover { background: rgba(255, 255, 255, 0.4); }
        
        .container {
            max-width: 1200px;
            margin: 40px auto;
            padding: 0 20px;
        }
        
        .card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            padding: 30px;
            margin-bottom: 30px;
            border: 1px solid #e8eef7;
        }
        
        .card h2 { color: #2c3e50; margin-bottom: 20px; font-size: 22px; }
        .card h3 { color: #34495e; margin: 20px 0 15px 0; font-size: 16px; }
        .card h4 { color: #7f8c8d; margin: 12px 0 8px 0; font-size: 14px; font-weight: 600; }
        
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #2c3e50;
            font-weight: 600;
        }
        .form-group textarea, .form-group input[type="file"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #e0e0e0;
            border-radius: 6px;
            font-size: 14px;
            font-family: inherit;
            transition: all 0.3s;
        }
        .form-group textarea:focus, .form-group input[type="file"]:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 8px rgba(102, 126, 234, 0.15);
        }
        .form-group textarea { min-height: 180px; resize: vertical; }
        
        .btn-analyze {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            padding: 14px 35px;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
        }
        .btn-analyze:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 18px rgba(102, 126, 234, 0.4);
        }
        
        .alert {
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-size: 14px;
            display: none;
            border-left: 4px solid;
        }
        .alert-error {
            background: #fde8e8;
            color: #c33;
            border-left-color: #c33;
        }
        .alert-error.show { display: block; }
        
        .loading {
            display: none;
            text-align: center;
            padding: 30px;
            color: #667eea;
            font-weight: 600;
            font-size: 16px;
        }
        .loading.show { display: block; }
        
        .results { display: none; }
        .results.show { display: block; }
        
        .score-display {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 12px;
            text-align: center;
            margin-bottom: 25px;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
        }
        .score-number {
            font-size: 56px;
            font-weight: 700;
            margin: 10px 0;
        }
        .score-label { font-size: 16px; opacity: 0.95; }
        
        .suggestion-item {
            background: #f0f4ff;
            border-left: 4px solid #667eea;
            padding: 15px;
            margin-bottom: 12px;
            border-radius: 6px;
            font-size: 14px;
            color: #2c3e50;
        }
        
        .ats-tip {
            background: #fffef0;
            border-left: 4px solid #f39c12;
            padding: 12px;
            margin-bottom: 10px;
            border-radius: 6px;
            font-size: 13px;
            color: #2c3e50;
        }
        
        .job-card {
            background: #f8f9fb;
            border: 1px solid #e8eef7;
            border-left: 4px solid #27ae60;
            padding: 20px;
            margin-bottom: 15px;
            border-radius: 8px;
            transition: all 0.3s;
        }
        .job-card:hover {
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            transform: translateY(-2px);
        }
        .job-header {
            display: flex;
            justify-content: space-between;
            align-items: start;
            margin-bottom: 12px;
        }
        .job-title { font-weight: 700; color: #2c3e50; font-size: 16px; }
        .job-company { color: #7f8c8d; font-size: 13px; }
        .match-badge {
            background: #27ae60;
            color: white;
            padding: 8px 14px;
            border-radius: 20px;
            font-weight: 600;
            font-size: 12px;
        }
        .job-meta {
            display: flex;
            gap: 20px;
            font-size: 13px;
            margin: 10px 0 15px 0;
            color: #7f8c8d;
        }
        .job-skills {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            margin-top: 12px;
        }
        .skill-tag {
            background: #e8eef7;
            color: #667eea;
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 500;
        }
        .skill-tag.missing {
            background: #fde8e8;
            color: #c33;
        }
        .skill-tag.matched {
            background: #d4edda;
            color: #27ae60;
        }
        
        .question-item {
            background: #f8f9fb;
            border-left: 4px solid #3498db;
            padding: 15px;
            margin-bottom: 12px;
            border-radius: 6px;
            font-size: 14px;
            color: #2c3e50;
        }
        
        .insight-box {
            background: #f8f9fb;
            border-left: 4px solid #9b59b6;
            padding: 15px;
            margin-bottom: 12px;
            border-radius: 6px;
            font-size: 13px;
        }
        .insight-label { font-weight: 600; color: #2c3e50; }
        .insight-value { color: #667eea; margin-top: 5px; }
        
        .tab-buttons {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
            flex-wrap: wrap;
        }
        .tab-btn {
            background: #e8eef7;
            color: #667eea;
            border: none;
            padding: 10px 20px;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 600;
            font-size: 13px;
            transition: all 0.3s;
        }
        .tab-btn.active {
            background: #667eea;
            color: white;
        }
        .tab-btn:hover { background: #667eea; color: white; }
        
        .tab-content { display: none; }
        .tab-content.active { display: block; }
        
        .two-column { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
        @media (max-width: 900px) { .two-column { grid-template-columns: 1fr; } }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>🎯 Career Path Analyzer</h1>
        <div class="user-info">
            <span>Welcome, <strong><%= loggedInUser.getName() %></strong></span>
            <button onclick="logout()">Logout</button>
        </div>
    </div>
    
    <div class="container">
        <div class="card">
            <h2>📄 Upload Your Resume</h2>
            <div id="alert" class="alert alert-error"></div>
            
            <div class="form-group">
                <label for="resumeFile">Upload Resume Photo (JPG, PNG):</label>
                <input type="file" id="resumeFile" accept="image/jpeg,image/png">
                <small style="color: #666; margin-top: 5px; display: block;">Upload a photo of your resume document for OCR analysis</small>
            </div>
            
            <div class="form-group">
                <label for="resumeText">Or paste your resume text:</label>
                <textarea id="resumeText" placeholder="Paste your complete resume content here..."></textarea>
            </div>
            
            <button class="btn-analyze" onclick="analyzeResume()">🚀 Analyze My Resume</button>
            <div id="loading" class="loading">🔄 Analyzing your resume and generating insights...</div>
        </div>
        
        <div id="resultsContainer" class="results">
            <div id="scoreSection"></div>
            
            <div class="card">
                <h2>📊 Comprehensive Career Analysis</h2>
                
                <div class="tab-buttons">
                    <button class="tab-btn active" onclick="switchTab('matching')">Job Matching</button>
                    <button class="tab-btn" onclick="switchTab('improvements')">Improvements</button>
                    <button class="tab-btn" onclick="switchTab('interviews')">Interview Prep</button>
                    <button class="tab-btn" onclick="switchTab('roadmap')">Skill Roadmap</button>
                    <button class="tab-btn" onclick="switchTab('insights')">Market Insights</button>
                </div>
                
                <div id="tab-matching" class="tab-content active">
                    <h3>🎯 Matching Job Opportunities</h3>
                    <div id="jobsList"></div>
                </div>
                
                <div id="tab-improvements" class="tab-content">
                    <div class="two-column">
                        <div>
                            <h3>💡 Resume Improvement Suggestions</h3>
                            <div id="suggestionsList"></div>
                        </div>
                        <div>
                            <h3>✅ ATS Optimization Tips</h3>
                            <div id="atsTipsList"></div>
                        </div>
                    </div>
                </div>
                
                <div id="tab-interviews" class="tab-content">
                    <h3>🎤 Likely Interview Questions for Your Target Role</h3>
                    <div id="questionsList"></div>
                </div>
                
                <div id="tab-roadmap" class="tab-content">
                    <div class="two-column">
                        <div>
                            <h3>🗺️ Recommended Skills to Learn</h3>
                            <div id="skillsList"></div>
                        </div>
                        <div>
                            <h3>📈 Your Career Progression Path</h3>
                            <div id="progressionList"></div>
                        </div>
                    </div>
                </div>
                
                <div id="tab-insights" class="tab-content">
                    <h3>📊 Job Market Insights & Trends</h3>
                    <div id="insightsList"></div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        // Disable JSP EL parsing in JS - use only plain JavaScript
        const contextPath = '<%= request.getContextPath() %>';

        function analyzeResume() {
            const resumeFile = document.getElementById('resumeFile');
            const resumeText = document.getElementById('resumeText').value;
            let dataToSend = null;
            
            if (resumeFile.files.length > 0) {
                const file = resumeFile.files[0];
                if (!['image/jpeg', 'image/png'].includes(file.type)) {
                    showAlert('Please upload a JPG or PNG image');
                    return;
                }
                dataToSend = new FormData();
                dataToSend.append('resumeFile', file);
            } 
            else if (resumeText.trim()) {
                dataToSend = new URLSearchParams();
                dataToSend.append('resumeText', resumeText);
            }
            else {
                showAlert('Please upload a resume photo or paste your resume text');
                return;
            }
            
            document.getElementById('loading').classList.add('show');
            document.getElementById('resultsContainer').classList.remove('show');
            document.getElementById('alert').classList.remove('show');
            
            const fetchOptions = {
                method: 'POST',
                body: dataToSend
            };
            
            if (!(dataToSend instanceof FormData)) {
                fetchOptions.headers = { 'Content-Type': 'application/x-www-form-urlencoded' };
            }
            
            fetch(contextPath + '/analyze', fetchOptions)
            .then(response => response.json())
            .then(data => {
                document.getElementById('loading').classList.remove('show');
                if (data.success) {
                    displayComprehensiveResults(data);
                    document.getElementById('resultsContainer').classList.add('show');
                    document.getElementById('resumeText').value = '';
                    document.getElementById('resumeFile').value = '';
                } else {
                    showAlert(data.message || 'Analysis failed');
                }
            })
            .catch(error => {
                document.getElementById('loading').classList.remove('show');
                showAlert('Error: ' + error.message);
            });
        }
        
        function displayComprehensiveResults(data) {
            let scoreHTML = '<div class="card"><div class="score-display">' +
                '<div class="score-label">Your Resume Quality Score</div>' +
                '<div class="score-number">' + data.resumeScore + '/100</div>';
            
            if (data.resumeScore >= 80) {
                scoreHTML += '<div class="score-label">Excellent! Your resume is well-structured.</div>';
            } else if (data.resumeScore >= 60) {
                scoreHTML += '<div class="score-label">Good! Here are some tips to make it even better.</div>';
            } else {
                scoreHTML += '<div class="score-label">Let\'s improve your resume with the suggestions below.</div>';
            }
            scoreHTML += '</div></div>';
            document.getElementById('scoreSection').innerHTML = scoreHTML;
            
            let jobsHTML = '';
            if (data.matchedJobs && data.matchedJobs.length > 0) {
                data.matchedJobs.forEach(job => {
                    let matchingSkillsHTML = '';
                    job.matchingSkills.forEach(skill => {
                        matchingSkillsHTML += '<span class="skill-tag matched">✓ ' + skill + '</span>';
                    });
                    let missingSkillsHTML = '';
                    job.missingSkills.forEach(skill => {
                        missingSkillsHTML += '<span class="skill-tag missing">✗ ' + skill + '</span>';
                    });
                    
                    jobsHTML += '<div class="job-card">' +
                        '<div class="job-header">' +
                        '<div><div class="job-title">' + job.title + '</div><div class="job-company">' + job.company + '</div></div>' +
                        '<div class="match-badge">' + job.matchPercentage.toFixed(0) + '% Match</div>' +
                        '</div>' +
                        '<div class="job-meta">' +
                        '<span>Level: ' + job.seniority + '</span>' +
                        '<span>Salary: ' + job.salary + '</span>' +
                        '</div>' +
                        '<div><strong>Your Skills:</strong></div>' +
                        '<div class="job-skills">' + matchingSkillsHTML + '</div>' +
                        '<div style="margin-top: 12px;"><strong>To Learn:</strong></div>' +
                        '<div class="job-skills">' + missingSkillsHTML + '</div>' +
                        '</div>';
                });
            }
            document.getElementById('jobsList').innerHTML = jobsHTML || '<p>No matching jobs found</p>';
            
            let suggestionsHTML = '';
            if (data.improvementSuggestions) {
                data.improvementSuggestions.forEach(suggestion => {
                    suggestionsHTML += '<div class="suggestion-item">💡 ' + suggestion + '</div>';
                });
            }
            document.getElementById('suggestionsList').innerHTML = suggestionsHTML;
            
            let tipsHTML = '';
            if (data.atsOptimizationTips) {
                data.atsOptimizationTips.forEach(tip => {
                    tipsHTML += '<div class="ats-tip">✓ ' + tip + '</div>';
                });
            }
            document.getElementById('atsTipsList').innerHTML = tipsHTML;
            
            let questionsHTML = '';
            if (data.interviewQuestions) {
                data.interviewQuestions.forEach((q, i) => {
                    questionsHTML += '<div class="question-item"><strong>Q' + (i+1) + ':</strong> ' + q + '</div>';
                });
            }
            document.getElementById('questionsList').innerHTML = questionsHTML;
            
            let skillsHTML = '';
            if (data.skillRoadmap && data.skillRoadmap.recommendedSkills) {
                data.skillRoadmap.recommendedSkills.forEach(skill => {
                    skillsHTML += '<div class="skill-tag" style="display: inline-block; margin-bottom: 8px;">' + skill + '</div><br>';
                });
            }
            document.getElementById('skillsList').innerHTML = skillsHTML;
            
            let progressionHTML = '';
            if (data.skillRoadmap && data.skillRoadmap.careerProgression) {
                data.skillRoadmap.careerProgression.forEach((step, i) => {
                    if (i === 0) {
                        progressionHTML += '<div class="skill-tag" style="display: block; background: #d4edda; margin-bottom: 8px; font-weight: 700;">📍 ' + step + ' (Current)</div>';
                    } else {
                        progressionHTML += '<div style="text-align: center; margin: 8px 0; color: #667eea;">⬇️</div>' +
                            '<div class="skill-tag" style="display: block; margin-bottom: 8px;">📍 ' + step + '</div>';
                    }
                });
            }
            document.getElementById('progressionList').innerHTML = progressionHTML;
            
            let insightsHTML = '';
            if (data.jobMarketInsights) {
                insightsHTML += '<div class="insight-box"><div class="insight-label">🔥 Top Demanded Skills</div><div class="insight-value">' + data.jobMarketInsights.topSkills + '</div></div>' +
                    '<div class="insight-box"><div class="insight-label">🚀 Emerging Technologies</div><div class="insight-value">' + data.jobMarketInsights.emergingTech + '</div></div>' +
                    '<div class="insight-box"><div class="insight-label">💰 Salary Trends</div><div class="insight-value">' + data.jobMarketInsights.salaryInfo + '</div></div>';
            }
            document.getElementById('insightsList').innerHTML = insightsHTML;
        }
        
        function switchTab(tabName) {
            const tabs = document.querySelectorAll('.tab-content');
            tabs.forEach(tab => tab.classList.remove('active'));
            
            const buttons = document.querySelectorAll('.tab-btn');
            buttons.forEach(btn => btn.classList.remove('active'));
            
            document.getElementById('tab-' + tabName).classList.add('active');
            event.target.classList.add('active');
        }
        
        function showAlert(message) {
            const alertElement = document.getElementById('alert');
            alertElement.textContent = message;
            alertElement.classList.add('show');
        }
        
        function logout() {
            if (confirm('Are you sure you want to logout?')) {
                fetch(contextPath + '/logout', { method: 'POST' })
                    .then(() => {
                        window.location.href = contextPath + '/index.jsp';
                    });
            }
        }
    </script>
</body>
</html>

