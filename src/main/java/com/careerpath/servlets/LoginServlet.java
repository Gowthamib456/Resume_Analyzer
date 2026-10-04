package com.careerpath.servlets;

import com.careerpath.models.User;
import com.careerpath.services.UserDatabase;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * LoginServlet - Handles user login requests.
 * Demonstrates Servlet basics and String handling.
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validate input
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
        email = email.trim();
        password = password.trim();

        try {
            // Authenticate user
            User user = UserDatabase.authenticateUser(email, password);

            if (user != null) {
                // Create session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userEmail", email);
                session.setMaxInactiveInterval(30 * 60); // 30 minutes

                // Redirect to dashboard
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            } else {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Server error: " + e.getMessage());
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
        out.println("<head><title>Login - Career Path</title></head>");
        out.println("<body>");
        out.println("<h1>Please use POST method with email and password</h1>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
