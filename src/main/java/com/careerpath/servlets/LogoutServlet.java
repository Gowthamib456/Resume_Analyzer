package com.careerpath.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * LogoutServlet - Handles user logout requests.
 * Demonstrates session management.
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Get session and invalidate it
            HttpSession session = request.getSession(false);
            
            if (session != null) {
                session.invalidate();
                out.println("{\"success\": true, \"message\": \"Logged out successfully\"}");
            } else {
                out.println("{\"success\": true, \"message\": \"No active session\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"success\": false, \"message\": \"Logout failed\"}");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        doGet(request, response);
    }
}
