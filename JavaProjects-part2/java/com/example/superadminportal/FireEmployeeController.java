package com.example.superadminportal;

import com.example.superadminportal.superAdminPortal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FireEmployeeController implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private TextField empUsernameTf;
    @FXML
    private Label lblError;
    @FXML
    private Button exitBtn, fireBtn, backBtn;
//    static final String DB_Driver = "com.mysql.cj.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost/ems";
//    static final String DB_USER = "root";
//    static final String DB_PASS = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitBtn.setOnAction(this);
        fireBtn.setOnAction(this);
        backBtn.setOnAction(this);

    }
    @Override
    public void handle(ActionEvent actionEvent) {
        Connection con;

        {
            try {
                con = databaseConnection.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(actionEvent.getSource().equals(fireBtn)){
            String username = empUsernameTf.getText();
            if (username.equals(""))
                lblError.setText("Field can not be Empty");
            else{
//            String sql = "DELETE `User` FROM user INNER JOIN account ON user.ID = account.Foreign_ID WHERE account.Username=?";
            String sql2 = "DELETE t1,t2 ,t3 FROM Account AS t1 JOIN User AS t2 ON t1.Foreign_ID = t2.ID JOIN employee_info AS t3 on t3.ID = t2.ID WHERE t1.Username = ?";
//            try {
//                Class.forName(DB_Driver);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
            try {
//                Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                PreparedStatement pstmt = con.prepareStatement(sql2);
                pstmt.setString(1,username);
                pstmt.execute();
                System.out.println(username+" deleted successfully");

            } catch (SQLException e) {
                lblError.setText("Username not found in the database");
            }}

        }
        if(actionEvent.getSource().equals(exitBtn)){
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
