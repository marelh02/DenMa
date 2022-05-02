package com.example.denma.controllers;

import com.example.denma.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;



public class ActionsFMP<T> extends TableCell <T, Void> {
    private GridPane gp;
    FicheMédicalePatientController fmpc=null;

    public ActionsFMP(Patient patient,FicheMédicalePatientController fmpc) {
        this.fmpc=fmpc;
        gp = new GridPane();
        int size=40;
        Button voir = new Button("",new ImageView(new Image("perspicacite.png",size,size,false,false)));
        voir.setMinWidth(size);
        voir.setMinHeight(size);
        voir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    String ids =((ActeMedicale) getTableView().getItems().get(getIndex())).getIDSoin();
                    ActesMédicauxPatientsController ampc=new ActesMédicauxPatientsController(patient,getIndex(),trouverfmp(ids),fmpc);
                    ampc.showStage();

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
                    FichMedPat.supprimerActeMedPat(patient.getIDPatient(),((ActeMedicale) getTableView().getItems().get(getIndex())).getIDSoin());
                    DenMaStatsNDocs.decrStats();
                    fmpc.refreshfmp();
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

    public  ActeMedPat trouverfmp(String idSoin)
    {
        for(ActeMedPat amp: fmpc.fmp.getAmp()) if(amp.getActeMedicale().getIDSoin().equals(idSoin)) return amp;
        return null;
    }

}
