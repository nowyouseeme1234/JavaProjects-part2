package com.example.superadminportal;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.*;
import java.net.*;

import java.sql.*;
import java.util.ResourceBundle;

public class AdminController implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private Button createBtn, deleteBtn, backBtn, assignEmployeeBtn, refreshBtn;
    @FXML
    private TableColumn<superAdminController.tabelData, String> empNameColumn;

    @FXML
    private TableColumn<superAdminController.tabelData, String> empUsernameColumn;

    @FXML
    private TextField empUsernameTf;

    @FXML
    private TableColumn<superAdminController.tabelData, String> statusColumn;

    @FXML
    private TableView<superAdminController.tabelData> tableView;
    @FXML
    private Text description;
    static String adminEmpUsername;
    public static void setadminEmpUsername(String uname){
        adminEmpUsername=uname;
    }
    public static String getadminEmpUsername(){
        return adminEmpUsername;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBtn.setOnAction(this);
        deleteBtn.setOnAction(this);
        backBtn.setOnAction(this);
        refreshBtn.setOnAction(this);
        assignEmployeeBtn.setOnAction(this);
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
        if (actionEvent.getSource().equals(refreshBtn)){
            if(!AssignEmployee.getOrder().isEmpty()){
                description.setText(AssignEmployee.getOrder());
            }
            else{
                description.setText("No order has been placed");
                System.out.println("No order has been placed");}
            try {
                Connection con = databaseConnection.getConnection();
                String query = "select Name,t1.Username,t3.Status from User as t2 join Account as t1 on t1.Foreign_ID = t2.ID join employee_info as t3 on t1.Username = t3.Username where t1.Role = 'Employee'";
                Statement stmt = con.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                ObservableList<superAdminController.tabelData> data = FXCollections.observableArrayList();

                while (resultSet.next()) {
                    superAdminController.tabelData rowData = new superAdminController.tabelData();

                    rowData.setColumn2(resultSet.getString(1));
                    rowData.setColumn3(resultSet.getString(2));
                    rowData.setColumn4(resultSet.getString(3));
                    data.add(rowData);

                }

                empNameColumn.setCellValueFactory(new PropertyValueFactory<>("column2"));
                empUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("column3"));
                statusColumn.setCellValueFactory(new PropertyValueFactory<>("column4"));

                tableView.setItems(data);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        if (actionEvent.getSource().equals(assignEmployeeBtn)){

            try {
                String query = "select Username from employee_info where Username=? and Status='Free'";
                Connection con = databaseConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, empUsernameTf.getText());
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()){
                    description.setText("Employee is locked or is not registered");
                    System.out.println("Employee is not found in the database or is locked");
                }
                else {

                    String query2 = "update employee_info set Status = 'Locked' where Username = '"+empUsernameTf.getText()+ "'";
                    System.out.println(empUsernameTf.getText());
                    System.out.println(superAdminPortal.getUserName());
                    try {
                        Statement stm = con.createStatement();
                        int rs2 = stm.executeUpdate(query2);
                        if (rs2 > 0){
                            setadminEmpUsername(empUsernameTf.getText());
                            description.setText(empUsernameTf.getText()+ " Locked successfully");
                            System.out.println("Status updated correctly");
                        }
                        else{
                            System.out.println("Error updating the status");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("employee assigned");
                }
            } catch ( SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
