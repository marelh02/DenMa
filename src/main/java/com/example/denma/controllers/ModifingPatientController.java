package com.example.denma.controllers;

import com.example.denma.HelloApplication;
import com.example.denma.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ModifingPatientController {
    private Stage MPCStage;

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


    public ModifingPatientController(Patient mpa){
        MPCStage=new Stage();
        try{

            FXMLLoader MPCLoader = new FXMLLoader(HelloApplication.class.getResource("modPatient.fxml"));
            MPCLoader.setController(this);
            MPCStage.setScene(new Scene(MPCLoader.load()));
            MPCStage.setTitle("Modification des informations du patient");
            MPCStage.getIcons().add(new Image("DenMa.png"));
            giveIt(mpa);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showStage(){
        MPCStage.show();
    }


    @FXML
    private  void initialize()
    {
        sexec.setItems(sex);
    }

    public void giveIt(Patient modPa){
        nomc.setText(modPa.getNom());
        prenomc.setText(modPa.getPrenom());
        cinc.setText(modPa.getCIN());
        /*idcouvc.setText(modPa.getCm().getIDCouverture());
        typecouvc.setText(modPa.getCm().getTypeCouverture());*/
        datenaic.setValue(modPa.getDateNaissance());
        if (modPa.getSexe()=='M') sexec.getSelectionModel().select(0);
        else sexec.getSelectionModel().select(1);
    }
    /*@FXML
    private void nouveauPatient()
    {
        CouvertureMedicale cmed = new CouvertureMedicale(idcouvc.getText(), typecouvc.getText());
        char cc=sexec.getValue().compareTo("Masculin")==0?'M':'F';
        Patient pat = new Patient(datenaic.getValue(), cinc.getText(), nomc.getText(), prenomc.getText(),cc,cmed);
        System.out.println(pat);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String table="dentiste",user="root",password ="roottt";
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/"+table,user,password);
            String query =String.format("insert into patient(idPatient,dateNaissance, cin, nom, prenom, sexe,idCouverture, typeCouverture) values(%s,'%s','%s','%s','%s','%s','%s','%s')",pat.getIDPatient(),pat.getDateNaissance().toString(),pat.getCIN(),pat.getNom(),pat.getPrenom(),pat.getSexe(),pat.getCm().getIDCouverture(),pat.getCm().getTypeCouverture());
            int qs=con.createStatement().executeUpdate(query);
            con.close();
            System.out.println("insertion faite avec succés");
        }catch(Exception e){ System.out.println(e);}
    }*/


}
