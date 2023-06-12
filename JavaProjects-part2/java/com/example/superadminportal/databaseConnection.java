package com.example.superadminportal;
import java.sql.*;
public class databaseConnection {
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/ems";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DATABASE_DRIVER);
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
}
