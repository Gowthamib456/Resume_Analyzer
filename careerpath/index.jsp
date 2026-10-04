<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Career Path - Resume Analyzer</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        .container {
            background: white;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            max-width: 400px;
            width: 100%;
            padding: 40px;
        }
        
        .header {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .header h1 {
            color: #333;
            font-size: 28px;
            margin-bottom: 10px;
        }
        
        .header p {
            color: #666;
            font-size: 14px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }
        
        .form-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #e0e0e0;
            border-radius: 5px;
            font-size: 14px;
            transition: border-color 0.3s;
        }
        
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 5px rgba(102, 126, 234, 0.1);
        }
        
        .button-group {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 10px;
            margin-bottom: 20px;
        }
        
        button {
            padding: 12px;
            border: none;
            border-radius: 5px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .btn-login {
            background: #667eea;
            color: white;
        }
        
        .btn-login:hover {
            background: #5568d3;
            transform: translateY(-2px);
        }
        
        .btn-signup {
            background: #f0f0f0;
            color: #333;
        }
        
        .btn-signup:hover {
            background: #e0e0e0;
        }
        
        .alert {
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
            font-size: 14px;
            display: none;
        }
        
        .alert-error {
            background: #fee;
            color: #c33;
            border-left: 4px solid #c33;
            display: block;
        }
        
        .alert-success {
            background: #efe;
            color: #3c3;
            border-left: 4px solid #3c3;
            display: block;
        }
        
        .toggle-form {
            text-align: center;
            color: #666;
            font-size: 14px;
        }
        
        .toggle-form a {
            color: #667eea;
            text-decoration: none;
            cursor: pointer;
            font-weight: 600;
        }
        
        .toggle-form a:hover {
            text-decoration: underline;
        }
        
        .form-section {
            display: none;
        }
        
        .form-section.active {
            display: block;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🎯 Career Path</h1>
            <p>Find your perfect job match</p>
        </div>
        
        <!-- Login Form -->
        <div id="loginForm" class="form-section active">
            <div id="loginAlert" class="alert"></div>
            
            <div class="form-group">
                <label for="loginEmail">Email</label>
                <input type="email" id="loginEmail" placeholder="Enter your email">
            </div>
            
            <div class="form-group">
                <label for="loginPassword">Password</label>
                <input type="password" id="loginPassword" placeholder="Enter your password">
            </div>
            
            <div class="button-group">
                <button class="btn-login" onclick="handleLogin()">Login</button>
                <button class="btn-signup" onclick="toggleForm()">Sign Up</button>
            </div>
            
            <div class="toggle-form">
                Don't have an account? <a onclick="toggleForm()">Create one</a>
            </div>
        </div>
        
        <!-- Signup Form -->
        <div id="signupForm" class="form-section">
            <div id="signupAlert" class="alert"></div>
            
            <div class="form-group">
                <label for="signupName">Full Name</label>
                <input type="text" id="signupName" placeholder="Enter your name">
            </div>
            
            <div class="form-group">
                <label for="signupEmail">Email</label>
                <input type="email" id="signupEmail" placeholder="Enter your email">
            </div>
            
            <div class="form-group">
                <label for="signupPassword">Password</label>
                <input type="password" id="signupPassword" placeholder="Enter password (min 6 chars)">
            </div>
            
            <div class="form-group">
                <label for="signupConfirmPassword">Confirm Password</label>
                <input type="password" id="signupConfirmPassword" placeholder="Confirm password">
            </div>
            
            <div class="button-group">
                <button class="btn-signup" onclick="toggleForm()">Back</button>
                <button class="btn-login" onclick="handleSignup()">Sign Up</button>
            </div>
            
            <div class="toggle-form">
                Already have an account? <a onclick="toggleForm()">Login</a>
            </div>
        </div>
    </div>
    
    <script>
        function toggleForm() {
            document.getElementById('loginForm').classList.toggle('active');
            document.getElementById('signupForm').classList.toggle('active');
            clearAlerts();
        }
        
        function clearAlerts() {
            document.getElementById('loginAlert').className = 'alert';
            document.getElementById('signupAlert').className = 'alert';
        }
        
        function showAlert(elementId, message, type) {
            const alertElement = document.getElementById(elementId);
            alertElement.className = 'alert alert-' + type;
            alertElement.textContent = message;
        }
        
        function handleLogin() {
            const email = document.getElementById('loginEmail').value;
            const password = document.getElementById('loginPassword').value;
            
            if (!email || !password) {
                showAlert('loginAlert', 'Please fill in all fields', 'error');
                return;
            }
            
            fetch('/careerpath/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'email=' + encodeURIComponent(email) + '&password=' + encodeURIComponent(password)
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    showAlert('loginAlert', 'Login successful! Redirecting...', 'success');
                    setTimeout(() => {
                        window.location.href = '/careerpath/dashboard.jsp';
                    }, 1000);
                } else {
                    showAlert('loginAlert', data.message || 'Login failed', 'error');
                }
            })
            .catch(error => {
                showAlert('loginAlert', 'Error: ' + error.message, 'error');
            });
        }
        
        function handleSignup() {
            const name = document.getElementById('signupName').value;
            const email = document.getElementById('signupEmail').value;
            const password = document.getElementById('signupPassword').value;
            const confirmPassword = document.getElementById('signupConfirmPassword').value;
            
            if (!name || !email || !password || !confirmPassword) {
                showAlert('signupAlert', 'Please fill in all fields', 'error');
                return;
            }
            
            fetch('/careerpath/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'name=' + encodeURIComponent(name) + 
                      '&email=' + encodeURIComponent(email) + 
                      '&password=' + encodeURIComponent(password) + 
                      '&confirmPassword=' + encodeURIComponent(confirmPassword)
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    showAlert('signupAlert', data.message + ' Please login now.', 'success');
                    setTimeout(() => {
                        toggleForm();
                    }, 1500);
                } else {
                    showAlert('signupAlert', data.message || 'Signup failed', 'error');
                }
            })
            .catch(error => {
                showAlert('signupAlert', 'Error: ' + error.message, 'error');
            });
        }
        
        // Pre-fill demo credentials
        document.getElementById('loginEmail').value = 'krishnami721@gmail.com';
        document.getElementById('loginPassword').value = 'password123';
    </script>
</body>
</html>
