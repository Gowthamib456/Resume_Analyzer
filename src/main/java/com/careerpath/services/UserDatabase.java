package com.careerpath.services;

import com.careerpath.models.User;
import com.careerpath.database.DatabaseConnection;
import java.sql.*;
import java.util.*;

/**
 * UserDatabase service class with JDBC support.
 * Uses MySQL database for persistent user storage.
 * Falls back to HashMap if database is unavailable.
 */
public class UserDatabase {
    // In-memory fallback cache
    private static final Map<String, User> users = new LinkedHashMap<>();
    private static boolean databaseAvailable = false;

    // Static initialization
    static {
        // Pre-populate fallback cache
        users.put("krishnami721@gmail.com", new User("krishnami721@gmail.com", "Krishna", "password123", "user"));
        users.put("admin@careerpath.ai", new User("admin@careerpath.ai", "Admin", "admin123", "admin"));
        users.put("john@example.com", new User("john@example.com", "John Doe", "john123", "user"));
        users.put("jane@example.com", new User("jane@example.com", "Jane Smith", "jane123", "user"));
        
        // Test database connection
        testDatabaseConnection();
    }

    /**
     * Test if database is available
     */
    private static void testDatabaseConnection() {
        try {
            // Use the new method that handles failures gracefully
            databaseAvailable = DatabaseConnection.isDatabaseAvailable();
            
            if (databaseAvailable) {
                System.out.println("✓ Database connection successful");
            } else {
                System.out.println("⚠ Database unavailable, using in-memory storage");
            }
        } catch (Exception e) {
            databaseAvailable = false;
            System.out.println("⚠ Database unavailable, using in-memory storage: " + e.getMessage());
        }
    }

    /**
     * Register a new user with JDBC support
     * @param email User email
     * @param name User name
     * @param password User password
     * @return true if registration successful
     */
    public static boolean registerUser(String email, String name, String password) {
        if (databaseAvailable) {
            return registerUserWithDatabase(email, name, password);
        } else {
            return registerUserInMemory(email, name, password);
        }
    }

    /**
     * Register user in MySQL database
     */
    private static boolean registerUserWithDatabase(String email, String name, String password) {
        String sql = "INSERT INTO users (email, password, name) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.executeUpdate();
            
            // Also add to in-memory cache
            users.put(email, new User(email, name, password, "user"));
            return true;
            
        } catch (SQLException e) {
            System.err.println("Database registration error: " + e.getMessage());
            // Fall back to in-memory
            return registerUserInMemory(email, name, password);
        }
    }

    /**
     * Register user in in-memory HashMap
     */
    private static boolean registerUserInMemory(String email, String name, String password) {
        if (users.containsKey(email)) {
            return false;
        }
        users.put(email, new User(email, name, password, "user"));
        return true;
    }

    /**
     * Authenticate user with JDBC support
     * @param email User email
     * @param password User password
     * @return User object if authentication successful
     */
    public static User authenticateUser(String email, String password) {
        if (databaseAvailable) {
            return authenticateUserWithDatabase(email, password);
        } else {
            return authenticateUserInMemory(email, password);
        }
    }

    /**
     * Authenticate user against MySQL database
     */
    private static User authenticateUserWithDatabase(String email, String password) {
        String sql = "SELECT id, email, password, name FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) {
                    String name = rs.getString("name");
                    User user = new User(email, name, password, "user");
                    // Cache the user
                    users.put(email, user);
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Database authentication error: " + e.getMessage());
            // Fall back to in-memory
            return authenticateUserInMemory(email, password);
        }
        return null;
    }

    /**
     * Authenticate user in-memory
     */
    private static User authenticateUserInMemory(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Get user by email with JDBC support
     */
    public static User getUserByEmail(String email) {
        if (users.containsKey(email)) {
            return users.get(email);
        }
        
        if (databaseAvailable) {
            String sql = "SELECT id, email, password, name FROM users WHERE email = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    User user = new User(email, rs.getString("name"), rs.getString("password"), "user");
                    users.put(email, user);
                    return user;
                }
            } catch (SQLException e) {
                System.err.println("Error fetching user: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Get all users
     */
    public static Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * Check if email exists
     */
    public static boolean emailExists(String email) {
        return users.containsKey(email);
    }

    /**
     * Check if user exists (alias for emailExists)
     */
    public static boolean userExists(String email) {
        return users.containsKey(email);
    }

    /**
     * Get database status
     */
    public static String getDatabaseStatus() {
        return databaseAvailable ? "Connected to MySQL" : "Using in-memory storage (Database unavailable)";
    }
}
