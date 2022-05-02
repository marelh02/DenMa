
package com.example.denma.controllers;

import com.example.denma.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddingPatientController {

    Stage APCStage=null;

    ObservableList<String> sex = FXCollections.observableArrayList("Masculin","Féminin");
    @FXML
    TextField nomc;
    @FXML
    TextField prenomc;
    @FXML
    TextField cinc;
    @FXML
    TextField idcouvc;
    @FXML
    TextField typecouvc;

    @FXML
    DatePicker datenaic;
    @FXML
    ChoiceBox<String> sexec;

    @FXML
    Button ajBu;

    DentistMenuController dmc;

    public AddingPatientController(DentistMenuController dmcc) {
        APCStage=new Stage();
        dmc=dmcc;
        try{

            FXMLLoader APCLoader = new FXMLLoader(HelloApplication.class.getResource("addPatient.fxml"));
            APCLoader.setController(this);
            APCStage.setScene(new Scene(APCLoader.load()));
            APCStage.setTitle("Ajout d'un nouveau patient");
            APCStage.getIcons().add(new Image("DenMa.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showStage(){APCStage.show();}

    @FXML
    private  void initialize()
    {
        sexec.setItems(sex);
        ajBu.setOnAction(new EventHandler<ActionEvent>()
                         {
                             @Override
                             public void handle(ActionEvent actionEvent) {
                                 CouvertureMedicale cmed = new CouvertureMedicale(idcouvc.getText(), typecouvc.getText());
                                 char cc=sexec.getValue().compareTo("Masculin")==0?'M':'F';
                                 Patient pat = new Patient(DenMaCore.nbrePatients(),datenaic.getValue(), cinc.getText(), nomc.getText(), prenomc.getText(),cc,cmed);
                                 System.out.println(pat);
                                 DenMaSQL.insérerNouveauPatient(pat);
                                 DenMaCore.incrNbrePatients();
                                 dmc.fillPatientTV();
                                 APCStage.close();
                             }
                         });
    }

}
