module com.example.superadminportal {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.superadminportal to javafx.fxml;
    exports com.example.superadminportal;
}