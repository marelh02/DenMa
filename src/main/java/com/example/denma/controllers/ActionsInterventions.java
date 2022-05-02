package com.example.denma.controllers;

import com.example.denma.ActeMedPat;
import com.example.denma.Intervention;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class ActionsInterventions <T> extends TableCell<T, Void> {
    private GridPane gp;


    public ActionsInterventions(ActesMédicauxPatientsController AMPC, ActeMedPat amp) {
        gp = new GridPane();
        int size=30;
        Button voir = new Button("",new ImageView(new Image("perspicacite.png",size,size,false,false)));
        voir.setMinWidth(size);
        voir.setMinHeight(size);
        voir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    InterventionFXMLController ifc = new InterventionFXMLController(amp,AMPC, (Intervention) getTableView().getItems().get(getIndex()));
                    ifc.showStage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //voir.setBackground(null);
        Button supprimer = new Button("",new ImageView(new Image("supprimer.png",size,size,false,false)));


        /////////FAUT METTRE DU CODE POUR LA SUPPRESSION


        supprimer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    amp.setInterventions(removeIntervention(((Intervention) getTableView().getItems().get(getIndex())).getIDIntervention(),amp.getInterventions()));
                    AMPC.refreshAMP(amp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        gp.add(voir, 0, 0, 1, 1);
        gp.add(supprimer, 1, 0, 1, 1);
    }
    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        setGraphic(empty ? null : gp);
    }

    public ArrayList<Intervention> removeIntervention(String idIntervention, ArrayList<Intervention> interventions)
    {
        for(int i=0;i<interventions.size();i++) if(interventions.get(i).getIDIntervention().equals(idIntervention)) {interventions.remove(i);break;}
        return interventions;
    }
}
