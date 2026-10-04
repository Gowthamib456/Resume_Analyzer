<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Career Path</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        
        .error-container {
            background: white;
            border-radius: 10px;
            padding: 40px;
            text-align: center;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            max-width: 500px;
        }
        
        h1 {
            color: #c33;
            font-size: 48px;
            margin: 0;
        }
        
        p {
            color: #666;
            margin: 15px 0;
            font-size: 16px;
        }
        
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: 600;
        }
        
        a:hover {
            background: #5568d3;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>⚠️ Error</h1>
        <p>Sorry, something went wrong!</p>
        <p>The page you're looking for might not exist or there was an error processing your request.</p>
        <a href="/careerpath/index.jsp">Go Home</a>
    </div>
</body>
</html>
