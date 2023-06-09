package com.example.superadminportal;

import com.example.superadminportal.superAdminPortal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class employeeController implements Initializable, EventHandler<ActionEvent> {
    static String username;


    public static void setUserName(String x){
        username = x;
    }

    @FXML
    private Button attendanceBtn, ordersBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Label deliveryLbl;

    @FXML
    private Button departmentBtn;

    @FXML
    private Text descriptionTxt;

    @FXML
    private Label hiLbl;

    @FXML
    private Button logoutBtn;

    @FXML
    private Text qouteTxt;

    @FXML
    private Button recruitmentBtn;

    @FXML
    private Button revewBtn;

    @FXML
    private Button salaryBtn;

    @FXML
    private Button shitBtn,takeOrderBtn;

    @FXML
    private Button toContactBtn;

    @FXML
    private Label zengLbl;


Connection con;

    {
        try {
            con = databaseConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backBtn.setOnAction(this);
        attendanceBtn.setOnAction(this);
        toContactBtn.setOnAction(this);
        shitBtn.setOnAction(this);
        salaryBtn.setOnAction(this);
        revewBtn.setOnAction(this);
        recruitmentBtn.setOnAction(this);
        logoutBtn.setOnAction(this);
        departmentBtn.setOnAction(this);
        ordersBtn.setOnAction(this);
        takeOrderBtn.setOnAction(this);
        takeOrderBtn.setVisible(false);
    }
    @Override
    public void handle(ActionEvent actionEvent) {

        if(actionEvent.getSource().equals(logoutBtn)){
            try {
                superAdminPortal.getLogOut();
                superAdminPortal.sceneFactory("Login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(actionEvent.getSource().equals(backBtn)){
            descriptionTxt.setText("Welcome to the employee management portal. On behalf of Fx delivery company LTD we thank you for your service. Our company is working on  building a  condusive working enviroment,in this process we would like to invite you to participate and do your best !!!");

        }

        if(actionEvent.getSource().equals(recruitmentBtn)){
            takeOrderBtn.setVisible(false);
            try {

                String sql="select Entry_date,Age from employee_info where Username='"+username+"'";
                String sql2= "select Name from `user` AS t2 join `account` as t1 where t1.Foreign_ID = t2.ID and t1.Username='"+username+"'";

                Statement sethi = con.createStatement();
                ResultSet set_hi = sethi.executeQuery(sql2);
                set_hi.next();

                hiLbl.setText("Hi "+set_hi.getString(1)+"👋");
                Statement stm= con.createStatement();
                ResultSet res = stm.executeQuery(sql);
                res.next();
                int retirement=65-res.getInt(2);
                descriptionTxt.setText("Recruitment Day \t\t\t\t\t"+res.getString("Entry_date")+"\n Retirement Year \t\t\t\t\t"+ retirement);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
        if(actionEvent.getSource().equals(salaryBtn)){
            takeOrderBtn.setVisible(false);

            try {
                String sql="select Salary from employee_info where Username='"+username+"'";
                Statement stm= con.createStatement();
                ResultSet res = stm.executeQuery(sql);
                res.next();
                double netincome=res.getInt("Salary")-(0.15*res.getInt("Salary"));
                descriptionTxt.setText(" Gross salary...................."+res.getInt("Salary")+"\n\nAmount of Tax.................... 15%\n\n Net income ...................."+ netincome);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
        if(actionEvent.getSource().equals(revewBtn)){
            try {
                String sql="select Review from employee_info where Username='"+username+"'";
                Statement stm= con.createStatement();
                ResultSet res = stm.executeQuery(sql);
                res.next();
                descriptionTxt.setText("The monthly review on you performance is the accumulative result of your Punctrallity\nIndustriousness and Customer service. \n Total Result ..................."+res.getInt("Review")+"✨");

        }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
        if(actionEvent.getSource().equals(attendanceBtn)){
            takeOrderBtn.setVisible(false);
            try {
                String sql="select Attendance from employee_info where Username='"+username+"'";
                Statement stm= con.createStatement();
                ResultSet res = stm.executeQuery(sql);
                res.next();
                String info;
                if(res.getInt("Attendance")==7){
                     info="No absent days 🎉";
                }
                else{
                    int days =7-res.getInt("Attendance");
//
                    info=days + "days of absent 👎🏽";
                }
                descriptionTxt.setText("You have attended " +res.getInt("Attendance")+"days in this week\n\n"+info);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(actionEvent.getSource().equals(shitBtn)){
            takeOrderBtn.setVisible(false);
            try {
                String sql="select Shift from employee_info where Username='"+username+"'";
                Statement stm= con.createStatement();
                ResultSet res = stm.executeQuery(sql);
                res.next();
                String shift;
                if(res.getString("Shift").equals("night")){
                  shift="🕚"  ;
                }
                else{
                    shift="🕗";
                }
                descriptionTxt.setText("Your shift for this  month....................  " +res.getString("Shift")+shift);

            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(actionEvent.getSource().equals(departmentBtn)){
            takeOrderBtn.setVisible(false);
            try {
                String sql="select Department from employee_info where Username='"+username+"'";
                Statement stm= con.createStatement();
                ResultSet res = stm.executeQuery(sql);
                res.next();
                descriptionTxt.setText("You are working under Department of \n\n\t\t\t"+res.getString("Department")+"💪🏽");

            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(actionEvent.getSource().equals(toContactBtn)){

                descriptionTxt.setText("If you need any additional information or complaints regarding  the company's service  we would welcome!!\n\n Email:- zengdelivery@gmail.com\n\n Phone number:- +251924468974\n\t\t\t+251945494048");

        }
        if(actionEvent.getSource().equals(ordersBtn)){
            takeOrderBtn.setVisible(true);
            if (!AssignEmployee.getOrder().equals("")){
                if(AdminController.getadminEmpUsername().equals(superAdminPortal.getUserName())){
                    descriptionTxt.setText(AssignEmployee.getOrder()+"\n"+"You are assigned for the task");
                }
                else
                    descriptionTxt.setText(AssignEmployee.getOrder()+"\n"+"You are NOT assigned for the task");
            }
            else {
                descriptionTxt.setText("No Current order is Submitted");
            }

        }
        if(actionEvent.getSource().equals(takeOrderBtn)){
            if (!AssignEmployee.getOrder().equals("")){
                String query2 = "update employee_info set Status = 'Free' where Username = '"+superAdminPortal.getUserName()+ "'";
                System.out.println(superAdminPortal.getUserName());
                try {
                    Statement stm = con.createStatement();
                    int rs2 = stm.executeUpdate(query2);
                    if (rs2 > 0){
                        System.out.println("Status updated correctly");
                        descriptionTxt.setText("");
//                        AssignEmployee.setStatus("Assigned for the task");
                    }
                    else{
                        System.out.println("Error updating the status");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }
}
