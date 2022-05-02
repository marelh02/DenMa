module com.example.denma {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires kernel;
    requires layout;
    requires io;
    requires commons.io;


    opens com.example.denma to javafx.fxml;
    exports com.example.denma;
    exports com.example.denma.controllers;
    opens com.example.denma.controllers to javafx.fxml;
}