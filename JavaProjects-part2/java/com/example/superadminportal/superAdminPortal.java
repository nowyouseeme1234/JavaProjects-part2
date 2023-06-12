package com.example.superadminportal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class superAdminPortal extends Application {
    static Connection con;

    static {
        try {
            con = databaseConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static String U_name;
    static String R_name;
    static String delete_username;
    static String delete_role;


    public static void setUserName(String x, String rol){
        U_name = x;
        R_name = rol;
    }
    public static void setDelete_usernameName(String x, String rol){
        delete_username = x;
        delete_role = rol;
    }
    public  static String  getUserName(){
        return U_name;
    }
    public  static String  getRoleName(){
        return R_name;
    }
   public static String filePath = "C:\\Users\\NY\\Documents\\intellijProjects\\superadminportal\\src\\main\\resources\\logs.txt";
   public static LocalDate currentDate = LocalDate.now();
    public static LocalTime currentTime = LocalTime.now();
    public static void getLogOut() throws IOException {
        String content = (R_name + " " + U_name + " has logged-Out on " + currentDate + " at "+currentTime.getHour()+ ":"+currentTime.getMinute()+":"+currentTime.getSecond());
        FileWriter writer = new FileWriter(filePath,true);
        PrintWriter out = new PrintWriter(writer);
        out.println(content);
        out.close();
    }
    public static void getLogIn() throws IOException {
        String content = (R_name + " " + U_name + " has logged-in on " + currentDate + " at "+currentTime.getHour()+ ":"+currentTime.getMinute()+":"+currentTime.getSecond());
        FileWriter writer = new FileWriter(filePath,true);
        PrintWriter out = new PrintWriter(writer);
        out.println(content);
        out.close();
    }
    public static void getDelete() throws IOException {
        String content = (R_name + " " + U_name + " has deleted " +delete_role+" "+delete_username +" in "+ currentDate + " at "+currentTime.getHour()+ ":"+currentTime.getMinute()+":"+currentTime.getSecond());
        FileWriter writer = new FileWriter(filePath,true);
        PrintWriter out = new PrintWriter(writer);
        out.println(content);
        out.close();
    }

    private Stage stage;

    private static  Scene scene;
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        Statement stmt = con.createStatement();
        String sql = "select * from user";
        ResultSet result = stmt.executeQuery(sql);
         if(result.next())
         {
        FXMLLoader fxmlLoader = new FXMLLoader(superAdminPortal.class.getResource("Login.fxml"));
        scene = new Scene(fxmlLoader.load(), 780, 550);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        ImageView logo = new ImageView("C:\\Users\\NY\\Documents\\intellijProjects\\superadminportal\\src\\main\\resources\\com\\example\\superadminportal\\images\\favicon\\favicon-32x32.png");
        stage.getIcons().add(logo.getImage());
        stage.setResizable(false);
        stage.setTitle("Delivery App");
        stage.setScene(scene);
        stage.show();
             }
          else
          {
            insertIntoUserAndAccountTable("SuperAdmin", "SuperA", "123", "Super Admin");
                       FXMLLoader fxmlLoader = new FXMLLoader(superAdminPortal.class.getResource("superAdmin-view.fxml"));
                       scene = new Scene(fxmlLoader.load(), 700, 565);
                       scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                       stage.setTitle("Delivery App");
                       stage.setScene(scene);
                       stage.show();

                   }

        }

    public static void sceneFactory(String fxml) throws IOException{
        scene.setRoot(loadFXML(fxml));
        scene.getWindow().sizeToScene();
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        return loader.load(superAdminPortal.class.getResource(fxml+".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }
    public static void insertIntoUserAndAccountTable(String name, String username, String password, String role) throws SQLException, IOException {

        String sqlU = "Insert Into user(Name) values(?)";
        PreparedStatement pstmtU = con.prepareStatement(sqlU, Statement.RETURN_GENERATED_KEYS);
        pstmtU.setString(1, name);
        int resultU = pstmtU.executeUpdate();
        if(resultU>0) {
            System.out.println(name+" Inserted Into User table successfully");
            ResultSet resSetU = pstmtU.getGeneratedKeys();
            resSetU.next();
            int foreignId = resSetU.getInt(1);
            String sqlA = "Insert Into account(Username, Password, Foreign_ID, Role) values(?,?,?,?)";
            PreparedStatement pstmtA = con.prepareStatement(sqlA);
            pstmtA.setString(1, username);
            pstmtA.setString(2, password);
            pstmtA.setInt(3, foreignId);
            pstmtA.setString(4, role);
            int resultA = pstmtA.executeUpdate();
            if (resultA>0){
                System.out.println(username+" Inserted Into Account table Successfully");
                setUserName(username, role);
                getLogIn();

            }
            else
            {
                System.out.println("failed to insert "+username+" into account table");
            }

        }
        else {
            System.out.println("failed to insert "+name+" into user table");
        }
    }
    public static void insertIntoUserAndAccountTable(String name, String username, String password, String role, String address, String tel) throws SQLException, IOException {

        String sqlU = "Insert Into user(Name) values(?)";
        PreparedStatement pstmtU = con.prepareStatement(sqlU, Statement.RETURN_GENERATED_KEYS);
        pstmtU.setString(1, name);
        int resultU = pstmtU.executeUpdate();
        if(resultU>0) {
            System.out.println("Customer: "+name+" Inserted Into User table successfully");
            ResultSet resSetU = pstmtU.getGeneratedKeys();
            resSetU.next();
            int foreignId = resSetU.getInt(1);
            String sqlA = "Insert Into account(Username, Password, Foreign_ID, Role, Address, Tel) values(?,?,?,?,?,?)";
            PreparedStatement pstmtA = con.prepareStatement(sqlA);
            pstmtA.setString(1, username);
            pstmtA.setString(2, password);
            pstmtA.setInt(3, foreignId);
            pstmtA.setString(4, role);
            pstmtA.setString(5, address);
            pstmtA.setString(6, tel);

            int resultA = pstmtA.executeUpdate();
            if (resultA>0){
                System.out.println("Customer: "+username+" Inserted Into Account table Successfully");
                setUserName(username, role);
                getLogIn();

            }
            else
            {
                System.out.println("failed to insert "+username+" into account table");
            }

        }
        else {
            System.out.println("failed to insert "+name+" into user table");
        }
    }
    public static ResultSet selectEmloyeeUserName(String username) throws SQLException{
        String sql = "select Username from account where Username='"+username+"' and Role = 'Employee'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeQuery();
    }
    public static ResultSet selectUserName() throws SQLException {
        String sql = "select Username from account";
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeQuery();
    }
    public static void createEmployeeAccount(String empName, String empUsername, String empPass, String empAge, String empShift, String empDept, String empSalary) throws SQLException {
        String sql = "INSERT INTO `user`(`Name`) VALUES (?)";
        String sql2 = "INSERT INTO `account`(`Username`, `Password`, `Foreign_ID`, `Role`) VALUES (?,?,?,?)";
        String sql3 ="Insert into `employee_info` (`ID`, `Age`, `Review`, `Attendance`, `Shift`, `Department`, `Salary`, `Username`) Values(?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, empName);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();

         if(rs.next())
             {
            int FI = rs.getInt(1);
            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setString(1, empUsername);
            pstmt2.setString(2, empPass);
            pstmt2.setInt(3, FI);
            pstmt2.setString(4, "Employee");
            int rs2 = pstmt2.executeUpdate();

            if(rs2>0)
            {

                PreparedStatement pstmt3 = con.prepareStatement(sql3);
                pstmt3.setInt(1, FI);
                pstmt3.setInt(2, Integer.parseInt(empAge));
                pstmt3.setInt(3, 9);
                pstmt3.setInt(4, 7);
                pstmt3.setString(5, empShift);
                pstmt3.setString(6, empDept);
                pstmt3.setInt(7, Integer.parseInt(empSalary));
                pstmt3.setString(8, empUsername);
                int rs3 = pstmt3.executeUpdate();

                if (rs3>0){
                    System.out.println("Employee Account Created successfully");
                }
                else {
                    System.out.println("Failed to insert into employee_info table");
                }

            }
            else {
                System.out.println("Failed to insert into account table");
            }
        }
         else {
             System.out.println("Failed to insert into user table");
         }
    }
    public static void clearFields(TextField shiftInput, TextField deptInput, TextField salaryInput,TextField ageInput){
        shiftInput.setText("");
        deptInput.setText("");
        salaryInput.setText("");
        ageInput.setText("");
    }
    public static void removeEmployee(String username) throws SQLException {
        String sql = "DELETE t2 FROM Account AS t1 JOIN User AS t2 ON t1.Foreign_ID = t2.ID JOIN employee_info AS t3 on t3.ID = t2.ID WHERE t1.Username = ? and t1.Role= 'Employee'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,username);
        pstmt.execute();
    }
}


