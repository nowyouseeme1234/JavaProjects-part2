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

import java.io.*;
import java.net.*;

import java.sql.*;
import java.util.ResourceBundle;


public class CustomerController implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private Button confirmBtn, searchOrderBtn,takeOrderBtn, IrishrumOrderBtn, aboutBtn, bbqOrderBtn, beefBurgerOrderBtn, dorowotOrderBtn, friesOrderBtn, loginBtn, riceOrderBtn, pizzaOrderBtn, searchBtn, strawberryMilkshakeOrderBtn, tunavegiesOrderBtn;
    @FXML
    private AnchorPane searchDisplay;
    @FXML
    private TextField searchTextField;
    @FXML
    private Label foodNameLbl, weeklyReviewLbl, confirmedLbl;
    @FXML
    private ImageView searchImageBtn;
    @FXML
    private TextField quantityTf;

    String Order;
    String acctualOrder;
    String name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setOnAction(this);
        searchBtn.setOnAction(this);
        bbqOrderBtn.setOnAction(this);
        IrishrumOrderBtn.setOnAction(this);
        beefBurgerOrderBtn.setOnAction(this);
        dorowotOrderBtn.setOnAction(this);
        friesOrderBtn.setOnAction(this);
        riceOrderBtn.setOnAction(this);
        pizzaOrderBtn.setOnAction(this);
        strawberryMilkshakeOrderBtn.setOnAction(this);
        tunavegiesOrderBtn.setOnAction(this);
        searchDisplay.setVisible(false);
        confirmBtn.setOnAction(this);
        quantityTf.setVisible(false);
        confirmBtn.setVisible(false);
        confirmedLbl.setText("");
    }
    @Override
    public void handle(ActionEvent actionEvent) {


       if (actionEvent.getSource().equals(loginBtn)){
           try {
               superAdminPortal.sceneFactory("Login");
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }

        if(actionEvent.getSource().equals(searchBtn)){
            quantityTf.setVisible(true);
            confirmBtn.setVisible(true);
            String searchTf = searchTextField.getText().toLowerCase().trim();
            try {
                Connection con = databaseConnection.getConnection();
                String sql = "select foodName from food";
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet res = pstmt.executeQuery();
                while(res.next()){
                    if(res.getString(1).equals(searchTf)){
                        System.out.println("Food Found");
                        foodNameLbl.setText(searchTf);
                        weeklyReviewLbl.setText("Weekly Review: stars");
                        Image image2=null;
                        Image image = new Image(new FileInputStream("C:\\Users\\NY\\Documents\\intellijProjects\\superadminportal\\src\\main\\resources\\com\\example\\superadminportal\\images\\search\\"+searchTf+".jpg"));
                        if(image.isError()){
                            image2= new Image(new FileInputStream("C:\\Users\\NY\\Documents\\intellijProjects\\superadminportal\\src\\main\\resources\\com\\example\\superadminportal\\images\\search\\"+searchTf+".png"));
                            searchImageBtn.setImage(image2);
                        }
                        else
                            searchImageBtn.setImage(image);
                        searchDisplay.setVisible(true);


                    }
                }
            } catch (SQLException | ClassNotFoundException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        if (actionEvent.getSource().equals(bbqOrderBtn)){
            quantityTf.setVisible(true);
            confirmBtn.setVisible(true);
            try {
                String query = "select address, tel from account where Username = '"+superAdminPortal.getUserName()+"'";

                Connection con = databaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if(rs.next())
                    {
                        name = "special bbq";
                    Order = "Food Name: Special BBQ\nCustomer Address: "+rs.getString("address")+"\nCustomer Tel: "+rs.getString("tel");

                    System.out.println("Order sent!");
                    }
                else
                    System.out.println("Something wrong with your query");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        if (actionEvent.getSource().equals(IrishrumOrderBtn)){
            quantityTf.setVisible(true);
            confirmBtn.setVisible(true);
            name = "irish rum";
            try {
                String query = "select address, tel from account where Username = '"+superAdminPortal.getUserName()+"'";

                Connection con = databaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if(rs.next())
                {
                    Order = "Food Name: Irish Rum\nCustomer Address: "+rs.getString("address")+"\nCustomer Tel: "+rs.getString("tel");

                    System.out.println("Order sent!");
                }
                else
                    System.out.println("Something wrong with your query");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        if (actionEvent.getSource().equals(beefBurgerOrderBtn)){
            quantityTf.setVisible(true);
            confirmBtn.setVisible(true);
            name= "beef burger";
            try {
                String query = "select address, tel from account where Username = '"+superAdminPortal.getUserName()+"'";

                Connection con = databaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if(rs.next())
                {
                    Order = "Food Name: Beef Burger\nCustomer Address: "+rs.getString("address")+"\nCustomer Tel: "+rs.getString("tel");

                    System.out.println("Order sent!");
                }
                else
                    System.out.println("Something wrong with your query");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        if (actionEvent.getSource().equals(dorowotOrderBtn)){
            quantityTf.setVisible(true);
            confirmBtn.setVisible(true);
            name = "doro wot";
            try {
                String query = "select address, tel from account where Username = '"+superAdminPortal.getUserName()+"'";

                Connection con = databaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if(rs.next())
                {
                    Order = "Food Name: Doro wot\nCustomer Address: "+rs.getString("address")+"\nCustomer Tel: "+rs.getString("tel");

                    System.out.println("Order sent!");
                }
                else
                    System.out.println("Something wrong with your query");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        if (actionEvent.getSource().equals(friesOrderBtn)){
            quantityTf.setVisible(true);
            confirmBtn.setVisible(true);
            name = "french fries";
            try {
                String query = "select address, tel from account where Username = '"+superAdminPortal.getUserName()+"'";

                Connection con = databaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if(rs.next())
                {
                    Order = "Food Name:French Fries\nCustomer Address: "+rs.getString("address")+"\nCustomer Tel: "+rs.getString("tel");

                    System.out.println("Order sent!");
                }
                else
                    System.out.println("Something wrong with your query");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        if (actionEvent.getSource().equals(riceOrderBtn)){
            quantityTf.setVisible(true);
            confirmBtn.setVisible(true);
            name = "rice";
            try {
                String query = "select address, tel from account where Username = '"+superAdminPortal.getUserName()+"'";

                Connection con = databaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if(rs.next())
                {
                    Order = "Food Name: Rice\nCustomer Address: "+rs.getString("address")+"\nCustomer Tel: "+rs.getString("tel");

                    System.out.println("Order sent!");
                }
                else
                    System.out.println("Something wrong with your query");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        if (actionEvent.getSource().equals(pizzaOrderBtn)){
            quantityTf.setVisible(true);
            confirmBtn.setVisible(true);
            name = "pizza";
            try {
                String query = "select address, tel from account where Username = '"+superAdminPortal.getUserName()+"'";

                Connection con = databaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if(rs.next())
                {
                    Order = "Food Name: Pizza\nCustomer Address: "+rs.getString("address")+"\nCustomer Tel: "+rs.getString("tel");

                    System.out.println("Order sent!");
                }
                else
                    System.out.println("Something wrong with your query");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        if (actionEvent.getSource().equals(strawberryMilkshakeOrderBtn)){
            quantityTf.setVisible(true);
            confirmBtn.setVisible(true);
            name = "strawberry milkshake";
            try {
                String query = "select address, tel from account where Username = '"+superAdminPortal.getUserName()+"'";

                Connection con = databaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if(rs.next())
                {
                    Order = "Food Name: Strawberry Milkshake\nCustomer Address: "+rs.getString("address")+"\nCustomer Tel: "+rs.getString("tel");
                    System.out.println("Order sent!");
                }
                else
                    System.out.println("Something wrong with your query");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        if (actionEvent.getSource().equals(tunavegiesOrderBtn)){
            quantityTf.setVisible(true);
            confirmBtn.setVisible(true);
            name = "tuna vegies";
            try {
                String query = "select address, tel from account where Username = '"+superAdminPortal.getUserName()+"'";

                Connection con = databaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if(rs.next())
                {

                    Order = "Food Name: Tunavegies\nCustomer Address: "+rs.getString("address")+"\nCustomer Tel: "+rs.getString("tel");

                    System.out.println("Order sent!");
                }
                else
                    System.out.println("Something wrong with your query");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        if(actionEvent.getSource().equals(confirmBtn)){
          int  quantity = Integer.parseInt(quantityTf.getText());
            try {
                acctualOrder = Order+"\nFood Price: "+superAdminPortal.selectPrice(name)*quantity+"ETB";
                AssignEmployee.setOrder(acctualOrder);
                confirmedLbl.setText("Confirmed!");
                System.out.println("confirmed");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }


}

