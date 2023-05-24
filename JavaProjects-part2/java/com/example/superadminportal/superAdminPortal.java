package com.example.superadminportal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

public class superAdminPortal extends Application {
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
   public static String filePath = "C:\\Users\\hp\\IdeaProjects\\superAdminPortal2\\src\\main\\resources\\logs.txt";
   public static LocalDate currentDate = LocalDate.now();
    public static LocalTime currentTime = LocalTime.now();
    public static void getLogOut() throws IOException {
        String content = (R_name + " " + U_name + " has logged-Out in " + currentDate + " at "+currentTime.getHour()+ ":"+currentTime.getMinute()+":"+currentTime.getSecond());
        FileWriter writer = new FileWriter(filePath,true);
        PrintWriter out = new PrintWriter(writer);
        out.println(content);
        out.close();
    }
    public static void getLogIn() throws IOException {
        String content = (R_name + " " + U_name + " has logged-in in " + currentDate + " at "+currentTime.getHour()+ ":"+currentTime.getMinute()+":"+currentTime.getSecond());
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
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(superAdminPortal.class.getResource("Login.fxml"));
         scene = new Scene(fxmlLoader.load(), 700, 565);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("Fx Employee Management System");
        stage.setScene(scene);
        stage.show();
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
}

