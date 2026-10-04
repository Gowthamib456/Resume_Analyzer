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
            background: #f5f7fa;
        }
        
        .navbar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        
        .navbar h1 {
            font-size: 24px;
        }
        
        .navbar .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
        }
        
        .navbar button {
            background: rgba(255, 255, 255, 0.3);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: 600;
        }
        
        .navbar button:hover {
            background: rgba(255, 255, 255, 0.5);
        }
        
        .container {
            max-width: 1000px;
            margin: 40px auto;
            padding: 0 20px;
        }
        
        .card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            margin-bottom: 30px;
        }
        
        .card h2 {
            color: #333;
            margin-bottom: 20px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }
        
        .form-group textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #e0e0e0;
            border-radius: 5px;
            font-size: 14px;
            font-family: inherit;
            min-height: 200px;
            resize: vertical;
        }
        
        .form-group textarea:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 5px rgba(102, 126, 234, 0.1);
        }
        
        button.btn-analyze {
            background: #667eea;
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 5px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        button.btn-analyze:hover {
            background: #5568d3;
            transform: translateY(-2px);
        }
        
        .results {
            display: none;
        }
        
        .results.show {
            display: block;
        }
        
        .result-item {
            background: #f9f9f9;
            border-left: 4px solid #667eea;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 5px;
        }
        
        .result-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        
        .result-header h3 {
            color: #333;
            font-size: 18px;
        }
        
        .match-percentage {
            background: #667eea;
            color: white;
            padding: 8px 16px;
            border-radius: 20px;
            font-weight: 600;
        }
        
        .skills-section {
            margin-top: 15px;
        }
        
        .skills-section h4 {
            color: #666;
            font-size: 14px;
            margin-bottom: 8px;
            margin-top: 15px;
        }
        
        .skills-list {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
        }
        
        .skill-tag {
            background: #e8eef7;
            color: #667eea;
            padding: 6px 12px;
            border-radius: 15px;
            font-size: 13px;
            font-weight: 500;
        }
        
        .skill-tag.missing {
            background: #fde8e8;
            color: #c33;
        }
        
        .alert {
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            font-size: 14px;
            display: none;
        }
        
        .alert-error {
            background: #fee;
            color: #c33;
            border-left: 4px solid #c33;
        }
        
        .alert-error.show {
            display: block;
        }
        
        .loading {
            display: none;
            text-align: center;
            color: #667eea;
            font-weight: 600;
        }
        
        .loading.show {
            display: block;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>🎯 Career Path</h1>
        <div class="user-info">
            <span>Welcome, <strong><%= loggedInUser.getName() %></strong></span>
            <button onclick="logout()">Logout</button>
        </div>
    </div>
    
    <div class="container">
        <div class="card">
            <h2>📄 Resume Content</h2>
            
            <div id="alert" class="alert alert-error"></div>
            
            <div class="form-group">
                <label for="resumeText">Paste your resume text here:</label>
                <textarea id="resumeText" placeholder="Paste your resume content here..."></textarea>
            </div>
            
            <button class="btn-analyze" onclick="analyzeResume()">Analyze Resume</button>
            <div id="loading" class="loading">Analyzing your resume...</div>
        </div>
        
        <div id="resultsContainer" class="card results">
            <h2>📊 Analysis Results</h2>
            <div id="resultsList"></div>
        </div>
    </div>
    
    <script>
        function analyzeResume() {
            const resumeText = document.getElementById('resumeText').value;
            
            if (!resumeText.trim()) {
                showAlert('Please paste your resume content');
                return;
            }
            
            document.getElementById('loading').classList.add('show');
            document.getElementById('resultsContainer').classList.remove('show');
            
            fetch('/careerpath/analyze', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'resumeText=' + encodeURIComponent(resumeText)
            })
            .then(response => response.json())
            .then(data => {
                document.getElementById('loading').classList.remove('show');
                
                if (data.success) {
                    displayResults(data.results);
                    document.getElementById('resultsContainer').classList.add('show');
                } else {
                    showAlert(data.message || 'Analysis failed');
                }
            })
            .catch(error => {
                document.getElementById('loading').classList.remove('show');
                showAlert('Error: ' + error.message);
            });
        }
        
        function displayResults(results) {
            const resultsList = document.getElementById('resultsList');
            resultsList.innerHTML = '';
            
            results.forEach(result => {
                let extractedSkillsHTML = '';
                if (result.extractedSkills && result.extractedSkills.length > 0) {
                    result.extractedSkills.forEach(skill => {
                        extractedSkillsHTML += '<span class="skill-tag">' + skill + '</span>';
                    });
                }

                let missingSkillsHTML = '';
                if (result.missingSkills && result.missingSkills.length > 0) {
                    result.missingSkills.forEach(skill => {
                        missingSkillsHTML += '<span class="skill-tag missing">' + skill + '</span>';
                    });
                }

                const resultHTML = '' +
                    '<div class="result-item">' +
                        '<div class="result-header">' +
                            '<h3>' + result.jobTitle + '</h3>' +
                            '<span class="match-percentage">' + result.matchPercentage + '% Match</span>' +
                        '</div>' +
                        '<div class="skills-section">' +
                            '<h4>✓ Skills You Have:</h4>' +
                            '<div class="skills-list">' + extractedSkillsHTML + '</div>' +
                        '</div>' +
                        '<div class="skills-section">' +
                            '<h4>✗ Skills You Need:</h4>' +
                            '<div class="skills-list">' + missingSkillsHTML + '</div>' +
                        '</div>' +
                    '</div>';
                
                resultsList.innerHTML += resultHTML;
            });
        }
        
        function showAlert(message) {
            const alertElement = document.getElementById('alert');
            alertElement.textContent = message;
            alertElement.classList.add('show');
        }
        
        function logout() {
            if (confirm('Are you sure you want to logout?')) {
                fetch('/careerpath/logout', { method: 'POST' })
                    .then(() => {
                        window.location.href = '/careerpath/index.jsp';
                    });
            }
        }
    </script>
</body>
</html>
