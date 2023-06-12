package com.example.superadminportal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FireEmployeeController implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private TextField empUsernameTf;
    @FXML
    private Label lblError;
    @FXML
    private Button exitBtn, fireBtn, backBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitBtn.setOnAction(this);
        fireBtn.setOnAction(this);
        backBtn.setOnAction(this);

    }
    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(fireBtn)) {
            String username = empUsernameTf.getText();
            ResultSet resultSet;
            try {
                resultSet = superAdminPortal.selectEmloyeeUserName(username);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (username.equals(""))
                lblError.setText("Field can not be Empty");
            else {
                try {
                    if (resultSet.next())
                        {
                            try {
                                superAdminPortal.removeEmployee(username);
                                String rol = "Employee";
                                superAdminPortal.setDelete_usernameName(username, rol);
                                superAdminPortal.getDelete();
                                System.out.println(username + " deleted successfully");

                            } catch (SQLException e) {
                                e.printStackTrace();
                                lblError.setText("Username not found in the database");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    else{
                        lblError.setText("Not An Employee");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

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
