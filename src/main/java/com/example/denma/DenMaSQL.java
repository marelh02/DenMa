package com.example.denma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;



public class DenMaSQL implements Serializable{
    String user,password,DB,port;
    private static final long serialVersionUID = -6509469138837573087L;

    public DenMaSQL(String user,String password,String DB,int port) {
        this.user = user;
        this.password=password;
        this.port=""+port;
        this.DB=DB;
    }

    public static void main(String[] args) {

    }
    //GETTER ET SETTERS
    public String getDB() {return DB;}
    public void setDB(String DB) {this.DB = DB;}
    public String getPort() {return port;}
    public void setPort(int port) {this.port = ""+port;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getUser() {return user;}
    public void setUser(String user) {this.user = user;}


    ////////////////////////////////////////OP2RATIONS SYST7ME SUR LA BD////////////////////////////////////////////

    ////MANIPULATION DES DONN2ES DE CONNECTION MYSQL

    //pour stocker les données de connection

    public static void storeDBInformations(DenMaSQL dmsql) {
        try
        {
            FileOutputStream fos = new FileOutputStream(DenMaCore.path()+"/DenMaSQL.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dmsql);
            oos.close();
            fos.close();
            Path path = Paths.get(DenMaCore.path()+"/DenMaSQL.dat");
            Files.setAttribute(path, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    //pour avoir les données de connection

    public static DenMaSQL getDBInformation() {
        DenMaSQL dmsql=null;
        try
        {
            FileInputStream fis = new FileInputStream(DenMaCore.path()+"/DenMaSQL.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            dmsql = (DenMaSQL) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return dmsql;
    }



    //pour avoir la liste des utilisateurs

    public static ObservableList<User> getUsersSQL() {
        DenMaSQL dmsql=getDBInformation();
        ObservableList<User> obsList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rs=con.createStatement().executeQuery("select * from user");
            while(rs.next())
            {
                obsList.add(new User(rs.getString("login"),rs.getString("pass") ));
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obsList;
    }

    //////////////////// POur les patients ////////////////

    public static void créerTablePatients() {
        DenMaSQL dmsql=getDBInformation();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            String query ="create table patient (idPatient int primary key, dateNaissance Date, cin varchar(10), nom varchar(20), prenom varchar(20), sexe char,idCouverture varchar(10), typeCouverture varchar(20), UNIQUE (idCouverture))";
            con.createStatement().execute(query);
            System.out.println("Création de la table des patients avec succés\n");
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    public static void supprimerTablePatients() {
        DenMaSQL dmsql=getDBInformation();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            String query ="DROP TABLE patient";
            con.createStatement().execute(query);
            System.out.println("Suppression faite avec succés\n");
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    public static void insérerNouveauPatient(Patient pat) {
        DenMaSQL dmsql=getDBInformation();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            String query =String.format("insert into patient(idPatient,dateNaissance, cin, nom, prenom, sexe,idCouverture, typeCouverture) values(%s,'%s','%s','%s','%s','%s','%s','%s')",pat.getIDPatient(),pat.getDateNaissance().toString(),pat.getCIN(),pat.getNom(),pat.getPrenom(),pat.getSexe(),pat.getCm().getIDCouverture(),pat.getCm().getTypeCouverture());
            int qs=con.createStatement().executeUpdate(query);
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    public static ObservableList<Patient> getPatientsSQL() {
        DenMaSQL dmsql=getDBInformation();
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from patient");
            while(rss.next())
            {
                patientList.add(new Patient(rss.getInt("idPatient"),rss.getDate("dateNaissance").toLocalDate(),rss.getString("cin")
                        ,rss.getString("nom" ),rss.getString("prenom"),rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
            }
            rss.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientList;
    }

    public static void deleteFromPatientTable(int id) {
        DenMaSQL dmsql=getDBInformation();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            con.createStatement().executeUpdate("delete from patient where idPatient="+id);
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////


     //////////////////REQUETE STATISTIQUE POUR LE SEXE DES PATIENTS/////////////////////////

    public static int[] sexNum() {
        int[] count=new int[2];
        String patN="SELECT count(*) FROM patient";
        String sexQ="SELECT count(*) FROM patient WHERE sexe='M'";
        DenMaSQL dmsql=getDBInformation();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rs=con.createStatement().executeQuery(patN);
            if(rs.next()){
                count[0]=rs.getInt(1);
            }
            rs=con.createStatement().executeQuery(sexQ);
            if(rs.next()){
                count[1]=rs.getInt(1);
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

     ///////////////////////////////////////////////////////
}
