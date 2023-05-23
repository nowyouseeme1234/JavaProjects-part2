package com.example.superadminportal;


import com.example.superadminportal.superAdminPortal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
public class CreateEmployeeController implements Initializable, EventHandler<ActionEvent> {
    Connection con;

    {
        try {
            con = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private TextField employeeNameTf, employeeUsernameTf, employeeShiftTf, employeeDeptTf, employeeSalaryTf, employeeAgeTf;

    @FXML
    private PasswordField employeePasswordPf;

    @FXML
    private Button cancelBtn, createBtn, backBtn;
    @FXML
    private Label lblError;

//    static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost/ems";
//    static final String DB_USER = "root";
//    static final String DB_PASS = "";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBtn.setOnAction(this);
        cancelBtn.setOnAction(this);
        backBtn.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(createBtn)){
            String empAge = employeeAgeTf.getText();
            String empShift = employeeShiftTf.getText();
            String empDept = employeeDeptTf.getText();
            String empSalary = employeeSalaryTf.getText();
            String empName = employeeNameTf.getText();
            String empUsername = employeeUsernameTf.getText();
            String empPass = employeePasswordPf.getText();
            if (empAge.equals("") || empSalary.equals("")||empDept.equals("") || empName.equals("")||empShift.equals("") || empUsername.equals("")|| empPass.equals(""))
                lblError.setText("You Left a Field empty");
            else if (!empAge.matches("^[0-9]+$")||!empSalary.matches("^[0-9]+$")) {
                lblError.setText("Age and Salary can only contain numbers");
            }
            else if (Integer.parseInt(empAge)<18 || Integer.parseInt(empAge)>65){
                lblError.setText("Employee must be between 18 - 65 years of age");
            }
            else {
//            try {
//                Class.forName(DB_DRIVER);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
            try {

                String sql = "INSERT INTO `user`(`Name`) VALUES (?)";
                String sql2 = "INSERT INTO `account`(`Username`, `Password`, `Foreign_ID`, `Role`) VALUES (?,?,?,?)";
                String sql3 ="Insert into `employee_info` (`ID`, `Age`, `Review`, `Attendance`, `Shift`, `Department`, `Salary`, `Username`) Values(?,?,?,?,?,?,?,?)";
//                Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, empName);
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                int FI = rs.getInt(1);

                PreparedStatement pstmt2 = con.prepareStatement(sql2);
                pstmt2.setString(1, empUsername);
                pstmt2.setString(2, empPass);
                pstmt2.setInt(3, FI);
                pstmt2.setString(4, "Employee");
                pstmt2.executeUpdate();


                PreparedStatement pstmt3 = con.prepareStatement(sql3);
                pstmt3.setInt(1, FI);
                pstmt3.setInt(2, Integer.parseInt(empAge));
                pstmt3.setInt(3, 9);
                pstmt3.setInt(4,7);
                pstmt3.setString(5, empShift);
                pstmt3.setString(6, empDept);
                pstmt3.setInt(7, Integer.parseInt(empSalary));
                pstmt3.setString(8,empUsername);
                pstmt3.executeUpdate();

                pstmt.close();
                pstmt2.close();
                pstmt3.close();
                con.close();


                System.out.println("Employee Account Created successfully");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }}


        }
        if(actionEvent.getSource().equals(cancelBtn)){
            System.exit(0);
        }
        if(actionEvent.getSource().equals(backBtn)){
            try {
                superAdminPortal.sceneFactory("Admin");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

