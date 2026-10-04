package com.careerpath.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database connection manager using HikariCP for connection pooling
 * Manages MySQL database connections efficiently
 */
public class DatabaseConnection {
    private static HikariDataSource dataSource;
    private static boolean initialized = false;
    private static boolean connectionFailed = false;
    
    // Database configuration
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3307/careerpath_db?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Gowthami@123";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Initialize HikariCP connection pool - called lazily
     */
    private static void initializeConnectionPool() {
        if (initialized) return;
        
        try {
            // Load MySQL driver
            Class.forName(DB_DRIVER);
            
            // Configure HikariCP
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setUsername(DB_USER);
            config.setPassword(DB_PASSWORD);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setConnectionTimeout(5000); // 5 seconds timeout for quick failure
            config.setIdleTimeout(600000); // 10 minutes
            config.setMaxLifetime(1800000); // 30 minutes
            config.setAutoCommit(true);
            
            dataSource = new HikariDataSource(config);
            
            // Test the connection
            try (Connection testConn = dataSource.getConnection()) {
                System.out.println("✓ Database connection pool initialized successfully");
            }
            
            initialized = true;
            connectionFailed = false;
            
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL JDBC driver not found: " + e.getMessage());
            connectionFailed = true;
        } catch (SQLException e) {
            System.err.println("⚠ Database connection failed (using in-memory fallback): " + e.getMessage());
            connectionFailed = true;
            // Don't throw - allow app to work with in-memory storage
        }
    }
    
    /**
     * Get a connection from the pool
     */
    public static Connection getConnection() throws SQLException {
        if (!initialized) {
            initializeConnectionPool();
        }
        
        if (connectionFailed || dataSource == null) {
            throw new SQLException("Database not available - using in-memory fallback");
        }
        
        return dataSource.getConnection();
    }
    
    /**
     * Check if database is available
     */
    public static boolean isDatabaseAvailable() {
        if (!initialized) {
            initializeConnectionPool();
        }
        return !connectionFailed && dataSource != null;
    }
    
    /**
     * Close the connection pool
     */
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("✓ Connection pool closed");
        }
    }
    
    /**
     * Get pool statistics
     */
    public static String getPoolStats() {
        if (dataSource != null && !connectionFailed) {
            return "Active connections: " + dataSource.getHikariPoolMXBean().getActiveConnections() +
                   ", Idle connections: " + dataSource.getHikariPoolMXBean().getIdleConnections() +
                   ", Total connections: " + dataSource.getHikariPoolMXBean().getTotalConnections();
        }
        return "Connection pool not available";
    }
}
