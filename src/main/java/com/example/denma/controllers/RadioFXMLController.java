package com.example.denma.controllers;

import com.example.denma.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RadioFXMLController implements Initializable {

    private Stage RICStage=null;

    private ObservableList<String> nomTypesRadios = FXCollections.observableArrayList(DenMaCore.nomsTypesRadios());

    @FXML
    private TextField idRadioTF;

    @FXML
    private TextArea rqsGeneralesTA;

    @FXML
    private TextArea rqsNegativesTA;

    @FXML
    private TextArea rqsPositivesTA;

    @FXML
    private ChoiceBox<String> typesRadiosChoicebox;

    @FXML
    private Button ajouterButton;

    @FXML
    private Button chercherURLButton;

    @FXML
    private DatePicker dateRadioTA;

    @FXML
    private GridPane radioImgUrl;

    private String radioURL=null;

    ActeMedPat amp=null;

    ActesMédicauxPatientsController AMPC=null;

    Radio radio;

    ///////////////////// INTERACTION RADIO/////////////////////
    //NON, NON CE N4EST PAS MANUEL, OF COURSE....

    @FXML
    private RadioButton tooth1;

    @FXML
    private RadioButton tooth2;

    @FXML
    private RadioButton tooth3;

    @FXML
    private RadioButton tooth4;

    @FXML
    private RadioButton tooth5;

    @FXML
    private RadioButton tooth6;

    @FXML
    private RadioButton tooth7;

    @FXML
    private RadioButton tooth8;

    @FXML
    private RadioButton tooth9;

    @FXML
    private RadioButton tooth10;

    @FXML
    private RadioButton tooth11;

    @FXML
    private RadioButton tooth12;

    @FXML
    private RadioButton tooth13;

    @FXML
    private RadioButton tooth14;

    @FXML
    private RadioButton tooth15;

    @FXML
    private RadioButton tooth16;

    @FXML
    private RadioButton tooth17;

    @FXML
    private RadioButton tooth18;

    @FXML
    private RadioButton tooth19;

    @FXML
    private RadioButton tooth20;

    @FXML
    private RadioButton tooth21;

    @FXML
    private RadioButton tooth22;

    @FXML
    private RadioButton tooth23;

    @FXML
    private RadioButton tooth24;

    @FXML
    private RadioButton tooth25;

    @FXML
    private RadioButton tooth26;

    @FXML
    private RadioButton tooth27;

    @FXML
    private RadioButton tooth28;

    @FXML
    private RadioButton tooth29;

    @FXML
    private RadioButton tooth30;

    @FXML
    private RadioButton tooth31;

    @FXML
    private RadioButton tooth32;

    private int[] tch;
    /////////////////////////////////////////////////////////

    public RadioFXMLController(ActesMédicauxPatientsController AMPC,ActeMedPat amp, Radio radio) {
        this.AMPC=AMPC;
        this.radio=radio;
        this.amp=amp;
        RICStage = new Stage();
        try{

            FXMLLoader RICLoader = new FXMLLoader(HelloApplication.class.getResource("radio.fxml"));
            RICLoader.setController(this);
            RICStage.setScene(new Scene(RICLoader.load()));
            RICStage.setTitle("Radio du patient");
            RICStage.getIcons().add(new Image("DenMa.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showStage(){
        RICStage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillComponentsRadioMedInformations();
        typesRadiosChoicebox.setItems(nomTypesRadios);
        final FileChooser fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);
        initTeethchart();
        chercherURLButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                File radioFile= fileChooser.showOpenDialog(RICStage);
                System.out.println(radioFile.toString());
                radioURL=DenMaCore.movingRadioImage(radioFile,radio.getIDRadio());
                setRadioImageLink(radio.getIDRadio(),radioURL);
            }
        }
        );
        ajouterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                enregistrerRadio();
            }
        });
    }
    private void configuringFileChooser(FileChooser fileChooser) {

        fileChooser.setTitle("Choisir image de la radiographie");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

    }

    private void setRadioImageLink(String RILink,String imPath)
    {
        Hyperlink hl = new Hyperlink(RILink);
        //radioImageURL=imPath;
        hl.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Desktop dtp=Desktop.getDesktop();
                try {
                    dtp.open(new File(imPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        radioImgUrl.getChildren().add(hl);

    }

    private void fillComponentsRadioMedInformations(){
        if(radio!=null)
        {
            idRadioTF.setText(radio.getIDRadio());
            rqsGeneralesTA.setText(radio.getRemarquesGenerales());
            rqsNegativesTA.setText(radio.getRemarquesNegatives());
            rqsPositivesTA.setText(radio.getRemarquesPositives());
            dateRadioTA.setValue(radio.getDateRadio());
            radioURL=radio.getCheminimage();
            if (radio.getCheminimage()!=null) setRadioImageLink(radio.getIDRadio(),radio.getCheminimage());
            typesRadiosChoicebox.setValue(radio.getTypeRadio());
        }
        else {
            String num="";
            if (amp.getRadios()!=null)
            {
                if(amp.getRadios().size()>0)
                {
                    int i=0;
                    do {
                        num= amp.getActeMedicale().getIDSoin()+"RAD"+(amp.getRadios().size()+i);
                        i++;
                    }while (num.equals(amp.getRadios().get(amp.getRadios().size()-1)));
                }
                else num= amp.getActeMedicale().getIDSoin()+"RAD0";
            }
            else num= amp.getActeMedicale().getIDSoin()+"RAD0";
            radio=new Radio(num,"","","", LocalDate.now(),"","");
            idRadioTF.setText(num);
        }
    }



    public void enregistrerRadio()
    {
        radio=new Radio(idRadioTF.getText(),rqsPositivesTA.getText(),rqsNegativesTA.getText(),
                rqsGeneralesTA.getText(),dateRadioTA.getValue(),radioURL,typesRadiosChoicebox.getValue());
        ArrayList<Radio> radList;
        radList = amp.getRadios();
        if (radList!=null)
        {
            int z=0;
            for(int i=0;i<radList.size();i++)
            {
                if(radList.get(i).getIDRadio().equals(radio.getIDRadio()))
                {
                    radList.set(i,radio);
                    z=1;
                    break;
                }
            }
            if (z==0) radList.add(radio);
        }
        else
        {
            radList=new ArrayList<Radio>();
            radList.add(radio);
        }
        saveTeeth();
        amp=new ActeMedPat(amp.getActeMedicale(),amp.getInterventions(),radList,amp.getMédicaments());
        AMPC.refreshAMP(amp);
        RICStage.close();
    }

    public void initTeethchart()
    {
        RadioButton[] teeth={tooth1,tooth2,tooth3,tooth4,tooth5,tooth6,tooth7,tooth8,
                tooth9,tooth10,tooth11,tooth12,tooth13,tooth14,tooth15,tooth16,
                tooth17,tooth18,tooth19,tooth20,tooth21,tooth22,tooth23,tooth24,
                tooth25,tooth26,tooth27,tooth28,tooth29,tooth30,tooth31,tooth32};
        tch=TeethChart.getChart(radio.getIDRadio());
        if (tch!=null)
        {
            for(int i=0;i<32;i++) if(tch[i]==1) teeth[i].setSelected(true);
        }
    }

    public void saveTeeth()
    {
        RadioButton[] teeth={tooth1,tooth2,tooth3,tooth4,tooth5,tooth6,tooth7,tooth8,
                tooth9,tooth10,tooth11,tooth12,tooth13,tooth14,tooth15,tooth16,
                tooth17,tooth18,tooth19,tooth20,tooth21,tooth22,tooth23,tooth24,
                tooth25,tooth26,tooth27,tooth28,tooth29,tooth30,tooth31,tooth32};
        tch=new int[32];
        for(int i=0;i<32;i++) if(teeth[i].isSelected())tch[i]=1;
        TeethChart.storeChart(radio.getIDRadio(),tch);
    }

}
