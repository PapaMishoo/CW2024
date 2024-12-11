module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.level to javafx.fxml;
    opens com.example.demo.activeactor to javafx.fxml;
    opens com.example.demo.userinterface to javafx.fxml;
    exports com.example.demo;
}