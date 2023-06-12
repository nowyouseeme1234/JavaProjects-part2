package com.example.superadminportal;

import javafx.fxml.FXML;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class loginController {
    @FXML
    private Label Password,Username,loginTxt,name,homeTitle,errorMsg, Addres, tel;
    @FXML
    private PasswordField PasswordInp;
    @FXML
    private TextField nameInp,usernameInp, addressInput, telInput;
    @FXML
    private AnchorPane loginAnchor,service,welocmeAnchor;
    @FXML
    private Button cancelBtn,loginBtn,contactBtn,aboutBtn,serviceBtn,loginHomeBtn,signUpHomeBtn;
    @FXML
    private void initialize() {
        String filePath = "C:\\Users\\NY\\Documents\\intellijProjects\\superadminportal\\src\\main\\resources\\logs.txt";
        File logs = new File(filePath);
        try {
            if (logs.createNewFile()){
                System.out.println("New file is created!");
            }
            else {
                System.out.println("File already existed!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        welocmeAnchor.setVisible(true);
        service.setVisible(false);
        loginAnchor.setVisible(false);
        loginHomeBtn.setOnAction(actionEvent -> {
            welocmeAnchor.setVisible(false);
            service.setVisible(false);
            loginTxt.setText("Log In");
            name.setVisible(false);
            nameInp.setVisible(false);
            loginBtn.setText("Log In");
            loginAnchor.setVisible(true);
            errorMsg.setText("");
            tel.setVisible(false);
            addressInput.setVisible(false);
            telInput.setVisible(false);
            Addres.setVisible(false);

        });
        signUpHomeBtn.setOnAction(actionEvent -> {
            welocmeAnchor.setVisible(false);
            service.setVisible(false);
            loginTxt.setText("Sign Up");
            name.setVisible(true);
            nameInp.setVisible(true);
            loginBtn.setText("Sign Up");
            loginAnchor.setVisible(true);
            errorMsg.setText("");
            tel.setVisible(true);
            addressInput.setVisible(true);
            telInput.setVisible(true);
            Addres.setVisible(true);
        });
        serviceBtn.setOnAction(actionEvent -> {
            welocmeAnchor.setVisible(false);
            homeTitle.setText("Service");
            service.setVisible(true);
            service.setStyle("-fx-background-color: #2B4865;");
            tel.setVisible(false);
            addressInput.setVisible(false);
            telInput.setVisible(false);
            Addres.setVisible(false);
        });
        aboutBtn.setOnAction(actionEvent -> {
            welocmeAnchor.setVisible(false);
            homeTitle.setText("About");
            service.setVisible(true);
            service.setStyle("-fx-background-color: #002B5B;");
            tel.setVisible(false);
            addressInput.setVisible(false);
            telInput.setVisible(false);
            Addres.setVisible(false);
        });
        contactBtn.setOnAction(actionEvent -> {
            welocmeAnchor.setVisible(false);
            homeTitle.setText("Contact Us");
            service.setVisible(true);
            service.setStyle("-fx-background-color: #2C3333;");
            tel.setVisible(false);
            addressInput.setVisible(false);
            telInput.setVisible(false);
            Addres.setVisible(false);
        });
        loginBtn.setOnAction(actionEvent -> {
            if (loginBtn.getText().equals("Log In") && !PasswordInp.getText().isEmpty() && !usernameInp.getText().isEmpty()){
                try {
                    String Username = usernameInp.getText();
                    String Password = PasswordInp.getText();
                    System.out.println(Username);
                    System.out.println(Password);
                    Connection connection = databaseConnection.getConnection();
                    String selectSql = "select * from Account where Username = ? AND Password = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
                    String selectedUsername = Username;
                    String selectedPassword = Password;

                    preparedStatement.setString(1, selectedUsername);
                    preparedStatement.setString(2, selectedPassword);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        String Role = resultSet.getString("Role");
                        if (Role.equals("Super Admin") ){
                            superAdminPortal.sceneFactory("superAdmin-view");
                        }
                        else if(Role.equals("Employee")){
                            employeeController.setUserName(Username);
                            superAdminPortal.sceneFactory("employefxml");
                        }
                        else if(Role.equals("Admin")){
                            superAdminPortal.sceneFactory("Admin");
                        }
                        else if(Role.equals("Customer")){
                            superAdminPortal.sceneFactory("customer");
                        }

                        //==================================file writer

                        superAdminPortal.setUserName(Username,Role);
                        superAdminPortal.getLogIn();
                        //====================================file writer ends

//                        System.out.println();
                        errorMsg.setText("");
                    } else {
                        System.out.println("This user is not registered");
                        errorMsg.setText("User is not registered");
                    }
                    errorMsg.setText("");
                    usernameInp.setText("");
                    PasswordInp.setText("");
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();

                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            else if (loginBtn.getText().equals("Sign Up") && !nameInp.getText().isEmpty() && !PasswordInp.getText().isEmpty() && !usernameInp.getText().isEmpty()) {
                String Username = usernameInp.getText();
                String Password = PasswordInp.getText();
                String Address = addressInput.getText();
                String Tel = telInput.getText();
                String Role = "Customer";
                String Name = nameInp.getText();
                System.out.println(Username);
                System.out.println(Password);
                System.out.println(Role);

                  //=============================database connection starts

                try  {

                    ResultSet resultSet = superAdminPortal.selectUserName();
                    while (resultSet.next()) {
                        String checkUsername = resultSet.getString("Username");
                        if (checkUsername.equals(Username) ){
                            errorMsg.setText("This username is already in use");
                            return;
                        }
                    }
                    superAdminPortal.insertIntoUserAndAccountTable(Name, Username, Password, Role, Address, Tel);

                    //===================================database connection and prepared statements  ends
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
                telInput.setText("");
                addressInput.setText("");
                nameInp.setText("");
                usernameInp.setText("");
                PasswordInp.setText("");
                errorMsg.setText("");
                return;
            }
            errorMsg.setText("Please Insert values");
        });
        cancelBtn.setOnAction(actionEvent -> {
            telInput.setText("");
            addressInput.setText("");
            usernameInp.setText("");
            PasswordInp.setText("");
            nameInp.setText("");
            errorMsg.setText("");
        });
    }

}
