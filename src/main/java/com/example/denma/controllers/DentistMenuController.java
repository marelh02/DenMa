package com.example.denma.controllers;

import com.example.denma.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class DentistMenuController implements Initializable {
    
    Stage DMCStage=null;
    
    
    ObservableList<User> obsList = FXCollections.observableArrayList();

    ObservableList<Patient> patientList = FXCollections.observableArrayList();

    ////////////////////LES COMPOSANTES CLIQUABLES

    @FXML
    private Text adminN;

    @FXML
    private Button ajouterNouveauPatient;


    //colonnes de la table des utilisateurs

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User,String> pseudo;

    @FXML
    private TableColumn<User,String> mdp;

    @FXML
    private TableView<Patient> table_patients;
    
    @FXML
    private TableColumn<Patient, String> col_cin;

    @FXML
    private TableColumn<Patient, String> col_idcm;

    @FXML
    private TableColumn<Patient, String> col_typecm;

    @FXML
    private TableColumn<Patient, LocalDate> col_dateNaissance;

    @FXML
    private TableColumn<Patient, LocalDate> col_sexe;

    @FXML
    private TableColumn<Patient, Integer> col_idPatient;

    @FXML
    private TableColumn<Patient, String> col_nom;

    @FXML
    private TableColumn<Patient, String> col_prenom;

    @FXML
    private TableColumn<Patient, Void> col_actions;

    public DentistMenuController() {
        DMCStage =new Stage();
        try{

            FXMLLoader DMCLoader = new FXMLLoader(HelloApplication.class.getResource("dentist-menu.fxml"));
            DMCLoader.setController(this);
            DMCStage.setScene(new Scene(DMCLoader.load()));
            DMCStage.setTitle("DenMa : Menu dentiste");
            DMCStage.getIcons().add(new Image("DenMa.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showStage(){DMCStage.show();}

    public void initialize(URL location, ResourceBundle resources){
        try{
            sysSectionInit();
            setUserTVCollumns();
            fillUserTV();
            setPatientTVCollumns();
            fillPatientTV();
            radLVInit();
            interventionLVInit();
            sexStatInit();
            soinsStatInit();
            evoStatsInit();
            radioStatsInit();
            interventionStatsInit();
            ajouterNouveauPatient.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    interactWithAPC();
                }
            });

        }
        catch(Exception e){ e.printStackTrace();}
    }

    public void interactWithAPC()
    {
        new AddingPatientController(this).showStage();
    }

    public void setUserTVCollumns() {
        pseudo.setCellValueFactory(new PropertyValueFactory<>("login"));
        mdp.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    public void fillUserTV() {
        obsList=DenMaSQL.getUsersSQL();
        usersTable.setItems(obsList);
    }

    public void setPatientTVCollumns() {
        col_idPatient.setCellValueFactory(new PropertyValueFactory<>("IDPatient"));
        col_cin.setCellValueFactory(new PropertyValueFactory<>("CIN"));
        col_dateNaissance.setCellValueFactory(new PropertyValueFactory<>("DateNaissance"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        col_sexe.setCellValueFactory(new PropertyValueFactory<>("Sexe"));
        col_actions.setCellFactory(tc -> new Modif<>());
        col_idcm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCm().getIDCouverture()));
        col_typecm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCm().getTypeCouverture()));
    }

    public void fillPatientTV() {
        patientList=DenMaSQL.getPatientsSQL();
        table_patients.setItems(patientList);
    }

    ///////////////////////////////////Gestion des RADIOS//////////////////////////

    //////////////////////////LES COMPOSANTES DES RADIOS//////////////////
    @FXML
    private TextArea secRad_descta;

    @FXML
    private TextField secRad_idtf;

    @FXML
    private TextField secRad_nomtf;

    @FXML
    private Button secRad_rb;

    @FXML
    private Button secRad_sb;

    @FXML
    private ListView<TypeRadio> radLV;

    //////////////////////////////////////////////////////////////////////

    public void radLVInit(){
        ArrayList<TypeRadio> atr = DenMaCore.typesRadios();
        for(TypeRadio tr : atr)  radLV.getItems().add(tr);
        secRad_sb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { updateRadioList();}});

        secRad_rb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { deleteFromRadioList();}});

        radLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TypeRadio>() {
            @Override
            public void changed(ObservableValue<? extends TypeRadio> observable, TypeRadio oldValue, TypeRadio newValue) {
                secRad_idtf.setText(newValue.getIDTypeRadio());
                secRad_nomtf.setText(newValue.getNomTypeRadio());
                secRad_descta.setText(newValue.getDescription());
            }
        });

        radLV.setCellFactory(cell->new ListCell<TypeRadio>() {
            Tooltip tooltip = new Tooltip();
            @Override
            protected void updateItem(TypeRadio tr, boolean empty) {
                super.updateItem(tr, empty);

                if (tr == null || empty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(tr.getIDTypeRadio()+": "+tr.getNomTypeRadio());
                    tooltip.setText("Nom du radio: "+tr.getNomTypeRadio()+"\nÀ propos de ce Radio:\n"+tr.getDescription());
                    setTooltip(tooltip);
                    tooltip.setWrapText(true);
                    tooltip.setPrefWidth(250);
                }
            }
        });
    }

    public void updateRadioList() {
        DenMaCore.supprimerTypeRadio(secRad_idtf.getText());
        DenMaCore.ajouterTypeRadio(new TypeRadio(secRad_idtf.getText(),secRad_nomtf.getText(),secRad_descta.getText()));
        radLV.getItems().clear();
        radLVInit();
    }

    public void deleteFromRadioList() {
        DenMaCore.supprimerTypeRadio(secRad_idtf.getText());
        radLV.getItems().clear();
        radLVInit();
    }

    /////////////////////////////////////////////////////////



    //////POUR LA GESTION DES INTERVENTIONS M2DICALES

    ////////////LES COMPOSANTES DES INTERVENTIONS////////////
    @FXML
    private TextField secInt_idtf;

    @FXML
    private TextField secInt_prixtf;

    @FXML
    private Button secInt_rb;

    @FXML
    private Button secInt_sb;

    @FXML
    private TextField secInt_typetf;

    @FXML
    private ListView<CategorieIntervention> interventionLV;

    ///////////////////////////////////////////////////

    public void interventionLVInit(){
        ArrayList<CategorieIntervention> aci = DenMaCore.CategoriesInterventions();
        for(CategorieIntervention ci : aci)  interventionLV.getItems().add(ci);

        secInt_rb.setOnAction(actionEvent -> deleteFromInterList());

        secInt_sb.setOnAction(actionEvent -> updateIntervList());

        interventionLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CategorieIntervention>() {
            @Override
            public void changed(ObservableValue<? extends CategorieIntervention> observable, CategorieIntervention oldValue, CategorieIntervention newValue) {
            secInt_idtf.setText(newValue.getIDCategorie());
            secInt_typetf.setText(newValue.getType());
            secInt_prixtf.setText(newValue.getPrixBase()+"");
            }
        });

        interventionLV.setCellFactory(cell->new ListCell<CategorieIntervention>() {
            Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(CategorieIntervention catIn, boolean empty) {
                super.updateItem(catIn, empty);

                if (catIn == null || empty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(catIn.getIDCategorie()+": "+catIn.getType());
                    tooltip.setText("Type de l'intervention: " + catIn.getType() + "\nPrix:\n" + catIn.getPrixBase());
                    setTooltip(tooltip);
                }
            }
        });
    }

    public void updateIntervList() {
        DenMaCore.supprimerCategoriesInterventions(secInt_idtf.getText());
        //System.out.println(Double.parseDouble(secInt_prixtf.getText()));
        DenMaCore.ajouterCategorieIntervention(new CategorieIntervention(secInt_idtf.getText(),secInt_typetf.getText(),Double.parseDouble(secInt_prixtf.getText())));
        interventionLV.getItems().clear();
        interventionLVInit();
    }

    public void deleteFromInterList() {
        DenMaCore.supprimerCategoriesInterventions(secInt_idtf.getText());
        interventionLV.getItems().clear();
        interventionLVInit();
    }

    //////////////////////////////////////////



    /////////////////////////// POUR L4ONGLET LES STATS///////////////////////////////

    ////////////////LES COMPOSANTES STATS///////////////

    @FXML
    private PieChart sexPieChart;

    @FXML
    private PieChart soinsPieChart;

    @FXML
    private LineChart<String,Number> clientEvo;

    @FXML
    private PieChart radioStats;

    @FXML
    private PieChart interventionsStats;

    @FXML
    private Button deleteStats;

    ///////////////////////////////////////


    public void sexStatInit() {
        int[] count=DenMaSQL.sexNum();
        PieChart.Data h = new PieChart.Data("Hommes", count[1]);
        PieChart.Data f = new PieChart.Data("Femmes", count[0]-count[1]);
        sexPieChart.getData().add(h);
        sexPieChart.getData().add(f);
        h.getNode().setStyle("-fx-pie-color: #034373;");
        f.getNode().setStyle("-fx-pie-color: #f06aa4;");
        sexPieChart.setLegendVisible(false);
        sexPieChart.setLabelLineLength(10);
    }

    public void soinsStatInit() {
        int[] actsCount=DenMaStatsNDocs.interventionsFiniesStats();
        PieChart.Data h = new PieChart.Data("Actifs", actsCount[0]);
        PieChart.Data f = new PieChart.Data("Términés", actsCount[1]);
        soinsPieChart.getData().add(h);
        soinsPieChart.getData().add(f);
        h.getNode().setStyle("-fx-pie-color: #ffa500;");
        f.getNode().setStyle("-fx-pie-color: #a0db8e;");
        soinsPieChart.setLegendVisible(false);
        soinsPieChart.setLabelLineLength(10);
    }

    public void evoStatsInit() {
        deleteStats.setOnAction(actionEvent -> {DenMaCore.deleteFile("evoStats.dat");clientEvo.getData().clear();});
        XYChart.Series series = new XYChart.Series();
        for(DenMaStatsNDocs dsnd:DenMaStatsNDocs.trouverStats())
            series.getData().add(new XYChart.Data(dsnd.getCdate()+"",Integer.parseInt(dsnd.getNum())));
        series.setName("Evolution du nombre des clients dans le temps");
        clientEvo.getData().add(series);
    }

    public void radioStatsInit() {
        int[] stats=DenMaStatsNDocs.radioStats();
        ArrayList<String> ntr = DenMaCore.nomsTypesRadios();
        for (int i=0;i< ntr.size();i++) radioStats.getData().add(new PieChart.Data(ntr.get(i),stats[i]));
        radioStats.setLabelsVisible(false);
        radioStats.setLabelLineLength(10);
    }

    public void interventionStatsInit() {
        int[] stats=DenMaStatsNDocs.interventionStats();
        ArrayList<String> ntr = DenMaCore.nomsCatégoriesInterventions();
        for (int i=0;i< ntr.size();i++) interventionsStats.getData().add(new PieChart.Data(ntr.get(i),stats[i]));
        interventionsStats.setLabelsVisible(false);
        interventionsStats.setLabelLineLength(10);
    }

    ///////////////////////////////////////////////////////////////

    //////////////////////////Pour la gestion des médicaments///////

    @FXML
    private ListView<?> medSec_antalgiques;

    @FXML
    private ListView<?> medSec_antibiotiques;

    @FXML
    private ListView<?> medSec_antiinflammatoire;

    @FXML
    private ListView<?> medSec_bainsbouches;

    @FXML
    private Button medSec_delb;

    @FXML
    private TextArea medSec_descta;

    @FXML
    private TextField medSec_nomtf;

    @FXML
    private Button medSec_sauvb;

    @FXML
    private ChoiceBox<String> medSec_typecb;

    public void initMedSection(){
        medSec_typecb.setItems(FXCollections.observableArrayList(Médicaments.typesMédicaments()));
        //medSec_typecb.setOnAction(actionEvent -> );
    }


    ///////////////////////////////////////////////////////////////

    ////////////////////////////////POUR L4ONGLET DES PARAM7TRES SYST7ME

    //////LES COMPOSANTES////

    @FXML
    private TextField sys_admin;

    @FXML
    private TextField sys_bd_account;

    @FXML
    private TextField sys_bd_bd;

    @FXML
    private TextField sys_bd_port;

    @FXML
    private PasswordField sys_bd_pwd;

    @FXML
    private Button sys_bd_saveButton;

    @FXML
    private PasswordField sys_pwd;

    @FXML
    private Button sys_reset;

    @FXML
    private Button sys_rmdb;

    @FXML
    private Button sys_saveButton;
    //////////////////////////////////

    public void sysSectionInit() {

        fillSysComponents();

        sys_rmdb.setOnAction(actionEvent -> {
            DenMaSQL.supprimerTablePatients();
            DenMaSQL.créerTablePatients();
            DMCStage.close();
        });

        sys_reset.setOnAction(actionEvent -> {
            DenMaSQL.supprimerTablePatients();
            DenMaCore.deleteFile("");
            DMCStage.close();
        });

        sys_saveButton.setOnAction(actionEvent ->{
            //SVP ajouter une alerte avec confirmation du mot de passe de l'admin
            //et vérifie si les champs sont nulls ou pas
            DenMaCore.writeAdminData(sys_admin.getText(),sys_pwd.getText());
        });

        sys_bd_saveButton.setOnAction(actionEvent -> {
            //SVP ajouter une alerte avec confirmation du mot de passe de l'admin
            //et vérifie si les champs sont nulls ou pas
            //String user,password,DB,port;
            DenMaSQL.storeDBInformations(new DenMaSQL(sys_bd_account.getText(),
                    sys_bd_pwd.getText(),sys_bd_bd.getText(),Integer.parseInt(sys_bd_port.getText())));
        });

    }

    public void fillSysComponents() {
        String[] admin=DenMaCore.getAdminData();
        if(admin!=null)
        {
            sys_admin.setText(admin[0]);
            sys_pwd.setText(admin[1]);
        }
        DenMaSQL dms=DenMaSQL.getDBInformation();
        if (dms!=null)
        {
            sys_bd_account.setText(dms.getUser());
            sys_bd_bd.setText(dms.getDB());
            sys_bd_port.setText(dms.getPort());
            sys_bd_pwd.setText(dms.getPassword());
        }
    }

    /////////////////////////////////////////

}
