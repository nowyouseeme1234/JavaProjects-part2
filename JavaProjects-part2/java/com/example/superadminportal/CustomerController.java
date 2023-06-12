package com.example.superadminportal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private Button searchOrderBtn, IrishrumOrderBtn, aboutBtn, bbqOrderBtn, beefBurgerOrderBtn, dorowotOrderBtn, drinksBtn, foodsBtn, friesOrderBtn, loginBtn, riceOrderBtn, pizzaOrderBtn, searchBtn, signupBtn, strawberryMilkshakeOrderBtn, tunavegiesOrderBtn;
    @FXML
    private AnchorPane searchDisplay;
    @FXML
    private TextField searchTextField;
    @FXML
    private Label foodNameLbl, weeklyReviewLbl;
    @FXML
    private ImageView searchImageBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setOnAction(this);
        signupBtn.setOnAction(this);
        searchBtn.setOnAction(this);
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        searchDisplay.setVisible(false);
       if (actionEvent.getSource().equals(loginBtn)){
           try {
               superAdminPortal.sceneFactory("Login");
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }
        if (actionEvent.getSource().equals(signupBtn)){
            try {
                superAdminPortal.sceneFactory("Login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(actionEvent.getSource().equals(searchBtn)){
            String searchTf = searchTextField.getText().toLowerCase().trim();
            try {
                Connection con = databaseConnection.getConnection();
                String sql = "select * from food";
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet res = pstmt.executeQuery();
                while(res.next()){
                    if(res.getString(1).equals(searchTf)){
                        System.out.println("Food Found");
                        foodNameLbl.setText(searchTf);
                        weeklyReviewLbl.setText("Weekly Review: stars");
                        Image image = new Image(new FileInputStream("C:\\Users\\NY\\Documents\\intellijProjects\\superadminportal\\src\\main\\resources\\com\\example\\superadminportal\\images\\search\\"+searchTf+".jpg"));
                        searchImageBtn.setImage(image);
                        searchDisplay.setVisible(true);


                    }
                }
            } catch (SQLException | ClassNotFoundException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

