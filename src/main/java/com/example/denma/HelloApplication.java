package com.example.denma;

import com.example.denma.controllers.DentistMenuController;
import com.example.denma.controllers.InitController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication  extends Application{
    @Override
    public void start(Stage stage) throws IOException {

        if (!DenMaCore.isFirstBoot()) new DentistMenuController().showStage();

        else new InitController().showStage();

    }

    public static void main(String[] args) {
        launch();
    }
}