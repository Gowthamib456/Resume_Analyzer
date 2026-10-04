package com.careerpath.servlets;

import com.careerpath.services.UserDatabase;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * SignupServlet - Handles user registration requests.
 * Demonstrates String validation and user creation.
 */
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validate input - String handling for validation
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("signupError", "Email is required");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("signupError", "Name is required");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("signupError", "Password is required");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
        email = email.trim();
        name = name.trim();
        password = password.trim();
        
        // Check password length
        if (password.length() < 6) {
            request.setAttribute("signupError", "Password must be at least 6 characters");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("signupError", "Passwords do not match");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
        try {
            // Check if user already exists
            if (UserDatabase.userExists(email)) {
                request.setAttribute("signupError", "User already exists");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return;
            }
            
            // Register new user
            boolean registered = UserDatabase.registerUser(email, name, password);
            
            if (registered) {
                request.setAttribute("success", "Registration successful. Please login.");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                request.setAttribute("signupError", "Registration failed. Please try again.");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("signupError", "Server error: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Signup - Career Path</title></head>");
        out.println("<body>");
        out.println("<h1>Please use POST method with email, name, password, and confirmPassword</h1>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
