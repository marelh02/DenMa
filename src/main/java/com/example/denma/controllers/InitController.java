package com.example.denma.controllers;

import com.example.denma.DenMaCore;
import com.example.denma.DenMaSQL;
import com.example.denma.HelloApplication;
import com.example.denma.Médicaments;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitController implements Initializable {

    @FXML
    private PasswordField admParam_mdp;

    @FXML
    private TextField admParam_pseudo;

    @FXML
    private TextField clinParam_adresse;

    @FXML
    private TextField clinParam_clinique;

    @FXML
    private TextField clinParam_dentiste;

    @FXML
    private TextField clinParam_tel;

    @FXML
    private Button initSaveButton;

    @FXML
    private TextField mysqlParam_bd;

    @FXML
    private TextField mysqlParam_compte;

    @FXML
    private PasswordField mysqlParam_mdp;

    @FXML
    private TextField mysqlParam_port;

    Stage ICStage=null;

    public InitController() {
        ICStage=new Stage();
        try{

            FXMLLoader ICLoader = new FXMLLoader(HelloApplication.class.getResource("init.fxml"));
            ICLoader.setController(this);
            ICStage.setScene(new Scene(ICLoader.load()));
            ICStage.setTitle("Configuration initiale");
            ICStage.getIcons().add(new Image("DenMa.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void showStage(){ICStage.show();}

    public void initialize(URL location, ResourceBundle resources){
        initSaveButton.setOnAction(actionEvent -> {
            DenMaCore.firstBoot();
            DenMaCore.writeAdminData(admParam_pseudo.getText(),admParam_mdp.getText());
            DenMaSQL.storeDBInformations(new DenMaSQL(mysqlParam_compte.getText(),mysqlParam_mdp.getText(),
                    mysqlParam_bd.getText(),Integer.parseInt(mysqlParam_port.getText())));
            DenMaCore.writeDentisteCabinetData(clinParam_dentiste.getText(),clinParam_clinique.getText(),
                    clinParam_adresse.getText(),clinParam_tel.getText());
            DenMaSQL.créerTablePatients();
            Médicaments.ajouterMédicamentsBasiques();
            ICStage.close();
            new DentistMenuController().showStage();
        });
    }
}
