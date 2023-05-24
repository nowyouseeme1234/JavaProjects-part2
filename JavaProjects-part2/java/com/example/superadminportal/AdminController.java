package com.example.superadminportal;

import com.example.superadminportal.superAdminPortal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private Button createBtn, deleteBtn, backBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBtn.setOnAction(this);
        deleteBtn.setOnAction(this);
        backBtn.setOnAction(this);
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(createBtn)){
            try {
                superAdminPortal.sceneFactory("createEmployee");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(actionEvent.getSource().equals(deleteBtn)){
            try {
                superAdminPortal.sceneFactory("fireEmployee");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (actionEvent.getSource().equals(backBtn)){
            try {
                superAdminPortal.getLogOut();
                superAdminPortal.sceneFactory("Login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
