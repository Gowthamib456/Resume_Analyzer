package com.careerpath.database;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try {
            System.out.println("Getting connection...");
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Connection: " + conn);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
