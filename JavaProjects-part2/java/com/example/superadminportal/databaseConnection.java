package com.example.superadminportal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class databaseConnection {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/EMS";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0940";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
}
