package com.dev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtility {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/UserAccessManagement";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "root";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error connecting to the database", e);
        }
    }
}
