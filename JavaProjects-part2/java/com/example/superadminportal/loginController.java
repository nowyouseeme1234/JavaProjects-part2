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
    private Label Password,Username,loginTxt,name,homeTitle,errorMsg;
    @FXML
    private PasswordField PasswordInp;
    @FXML
    private TextField nameInp,usernameInp;
    @FXML
    private AnchorPane loginAnchor,service,welocmeAnchor;
    @FXML
    private Button cancelBtn,loginBtn,contactBtn,aboutBtn,serviceBtn,loginHomeBtn,signUpHomeBtn;
    @FXML
    private void initialize() {
        String filePath = "C:\\Users\\hp\\IdeaProjects\\superAdminPortal\\src\\main\\resources\\logs.txt";
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
        });
        serviceBtn.setOnAction(actionEvent -> {
            welocmeAnchor.setVisible(false);
            homeTitle.setText("Service");
            service.setVisible(true);
            service.setStyle("-fx-background-color: #2B4865;");
        });
        aboutBtn.setOnAction(actionEvent -> {
            welocmeAnchor.setVisible(false);
            homeTitle.setText("About");
            service.setVisible(true);
            service.setStyle("-fx-background-color: #002B5B;");
        });
        contactBtn.setOnAction(actionEvent -> {
            welocmeAnchor.setVisible(false);
            homeTitle.setText("Contact Us");
            service.setVisible(true);
            service.setStyle("-fx-background-color: #2C3333;");
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
                            superAdminPortal.sceneFactory("employe");
                        }
                        else if(Role.equals("Admin")){
                            superAdminPortal.sceneFactory("Admin");
                        }
                        else if(Role.equals("Customer")){
                            superAdminPortal.sceneFactory("Customer");
                        }

                        //==================================file writer

                        LocalDate currentDate = LocalDate.now();
                        LocalTime currentTime = LocalTime.now();
                        String content = (Role + " " +Username + " has logged-in in " + currentDate + " at "+currentTime.getHour()+ ":"+currentTime.getMinute()+":"+currentTime.getSecond());
                        FileWriter writer = new FileWriter(filePath,true);
                        PrintWriter out = new PrintWriter(writer);
                        out.println(content);
                        out.close();

                        //====================================file writer ends

                        System.out.println(content);
                        errorMsg.setText("");
                    } else {
                        System.out.println("This user is not registered");
                        errorMsg.setText("User is not registered");
                    }
                    usernameInp.setText("");
                    PasswordInp.setText("");
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            else if (loginBtn.getText().equals("Sign Up") && !nameInp.getText().isEmpty() && !PasswordInp.getText().isEmpty() && !usernameInp.getText().isEmpty()) {
                String Username = usernameInp.getText();
                String Password = PasswordInp.getText();
                String Role = "Customer";
                String Name = nameInp.getText();
                System.out.println(Username);
                System.out.println(Password);
                System.out.println(Role);

                try {
                    Random random = new Random();
                    int randomId = Math.abs(random.nextInt());

                    //=============================database connection starts

                    try (Connection connection = databaseConnection.getConnection()) {
                        String sql = "INSERT INTO User (ID, Name) VALUES (?,?)";
                        String sql2 = "INSERT INTO Account (Username, Password, Foreign_ID, Role) VALUES (?,?,?,?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);

                        String selectUsername = "select Username from Account ";
                        ResultSet resultSet = preparedStatement2.executeQuery(selectUsername);
                        while (resultSet.next()) {
                            String checkUsername = resultSet.getString("Username");
                            if (checkUsername.equals(Username) ){
                                errorMsg.setText("This Username has taken");
                                return;
                            }
                        }
                        //=======================================first table

                        preparedStatement.setInt(1, randomId);
                        preparedStatement.setString(2, Name);

                        //=======================================second table

                        preparedStatement2.setString(1, Username);
                        preparedStatement2.setString(2, Password);
                        preparedStatement2.setInt(3, randomId);
                        preparedStatement2.setString(4, Role);

                        int rowsAffected1 = preparedStatement.executeUpdate();
                        int rowsAffected2 = preparedStatement2.executeUpdate();

                        if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                            System.out.println("Data Insertion successfully.");
                            errorMsg.setText("");
                        } else {
                            System.out.println("No rows were affected. The Insertion may have failed.");
                            errorMsg.setText("Something went wrong");
                        }

                        //===================================database connection and prepared statements  ends
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                nameInp.setText("");
                usernameInp.setText("");
                PasswordInp.setText("");
                return;
            }
            errorMsg.setText("Please Insert values");
        });
        cancelBtn.setOnAction(actionEvent -> {
            usernameInp.setText("");
            PasswordInp.setText("");
            nameInp.setText("");
            errorMsg.setText("");
        });
    }

}
