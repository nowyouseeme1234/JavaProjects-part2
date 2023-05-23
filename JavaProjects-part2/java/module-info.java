module com.example.superadminportal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.superadminportal to javafx.fxml;
    exports com.example.superadminportal;
}