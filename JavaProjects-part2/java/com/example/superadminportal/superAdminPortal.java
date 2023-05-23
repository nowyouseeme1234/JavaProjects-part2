package com.example.superadminportal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class superAdminPortal extends Application {
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