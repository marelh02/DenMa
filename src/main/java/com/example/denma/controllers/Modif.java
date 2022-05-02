package com.example.denma.controllers;

import com.example.denma.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;



public class Modif<T> extends TableCell<T, Void> {


    private GridPane gp;

    public Modif() {
        gp = new GridPane();
        int size=40;
        Button voir = new Button("",new ImageView(new Image("carie.png",size,size,false,false)));
        voir.setMinWidth(size);
        voir.setMinHeight(size);
        voir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    FicheMédicalePatientController fmpc = new FicheMédicalePatientController((Patient) getTableView().getItems().get(getIndex()));
                    fmpc.showStage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //voir.setBackground(null);
        Button modifier = new Button("",new ImageView(new Image("modifier-loutil.png",size,size,false,false)));
        modifier.setMinWidth(size);
        modifier.setMinHeight(size);
        //modifier.setBackground(null);
        modifier.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            try {
                ModifingPatientController mpc=new ModifingPatientController((Patient) getTableView().getItems().get(getIndex()));
                mpc.showStage();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        });
        Button supprimer = new Button("",new ImageView(new Image("supprimer.png",size,size,false,false)));
        supprimer.setMinWidth(size);
        supprimer.setMinHeight(size);
        //supprimer.setBackground(null);
        supprimer.setOnAction(evt -> {
            DenMaSQL.deleteFromPatientTable(((Patient) getTableView().getItems().get(getIndex())).getIDPatient());
            FichMedPat.supprimerFiche(((Patient) getTableView().getItems().get(getIndex())).getIDPatient());
            getTableView().getItems().remove(getTableRow().getIndex());
        });

        gp.add(voir, 0, 0, 1, 1);
        gp.add(modifier, 1, 0, 1, 1);
        gp.add(supprimer, 2, 0, 1, 1);
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        setGraphic(empty ? null : gp);
    }

}
