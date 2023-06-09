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

    @FXML
    private TextField employeeNameTf, employeeUsernameTf, employeeShiftTf, employeeDeptTf, employeeSalaryTf, employeeAgeTf;

    @FXML
    private PasswordField employeePasswordPf;

    @FXML
    private Button cancelBtn, createBtn, backBtn;
    @FXML
    private Label lblError;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBtn.setOnAction(this);
        cancelBtn.setOnAction(this);
        backBtn.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(createBtn)) {
            String empAge = employeeAgeTf.getText();
            String empShift = employeeShiftTf.getText();
            String empDept = employeeDeptTf.getText();
            String empSalary = employeeSalaryTf.getText();
            String empName = employeeNameTf.getText();
            String empUsername = employeeUsernameTf.getText();
            String empPass = employeePasswordPf.getText();

            if (empAge.equals("") || empSalary.equals("") || empDept.equals("") || empName.equals("") || empShift.equals("") || empUsername.equals("") || empPass.equals(""))
                lblError.setText("You Left a Field empty");
            else if (!empAge.matches("^[0-9]+$") || !empSalary.matches("^[0-9]+$")) {
                lblError.setText("Age and Salary can only contain numbers");
            } else if (Integer.parseInt(empAge) < 18 || Integer.parseInt(empAge) > 65) {
                lblError.setText("Employee must be between 18 - 65 years of age");
            } else {
                try {
                    ResultSet resultSet = superAdminPortal.selectUserName();
                    while (resultSet.next()) {
                        String checkUsername = resultSet.getString("Username");
                        if (checkUsername.equals(empUsername)) {
                            lblError.setText("This username is already in use");
                            return;
                        }
                    }
                    try {
                        superAdminPortal.createEmployeeAccount(empName, empUsername, empPass, empAge, empShift, empDept, empSalary);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (actionEvent.getSource().equals(cancelBtn)) {
            System.exit(0);
        }
        if (actionEvent.getSource().equals(backBtn)) {
            try {
                superAdminPortal.sceneFactory("Admin");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
