package emsgroup.ems;

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
                App.sceneFactory("createEmployee");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(actionEvent.getSource().equals(deleteBtn)){
            try {
                App.sceneFactory("fireEmployee");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (actionEvent.getSource().equals(backBtn)){
            try {
                App.sceneFactory("InitialLogin");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
