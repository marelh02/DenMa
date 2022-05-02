package com.example.denma.controllers;

import com.example.denma.ActeMedPat;
import com.example.denma.Radio;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.util.ArrayList;

public class ActionsRadios<T> extends TableCell<T, Void> {
    private GridPane gp;


    public ActionsRadios(ActesMédicauxPatientsController AMPC, ActeMedPat amp) {
        gp = new GridPane();
        int size=30;
        Button voir = new Button("",new ImageView(new Image("perspicacite.png",size,size,false,false)));
        voir.setMinWidth(size);
        voir.setMinHeight(size);
        voir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    RadioFXMLController rfc=new RadioFXMLController(AMPC,amp,(Radio) getTableView().getItems().get(getIndex()));
                    rfc.showStage();
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
                    String idRadio=((Radio) getTableView().getItems().get(getIndex())).getIDRadio();
                    amp.setRadios(removeRadio(idRadio,amp.getRadios()));
                    new File("C:\\DenMa\\RadiosPatients\\"+idRadio+".dat").delete();
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

    public ArrayList<Radio> removeRadio(String idradio, ArrayList<Radio> radios)
    {
        for(int i=0;i<radios.size();i++) if(radios.get(i).getIDRadio().equals(idradio)) {radios.remove(i);break;}
        return radios;
    }
}
