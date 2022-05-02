package com.example.denma;

import java.util.ArrayList;

public class Médicaments {
    private String nom,type,description;

    public Médicaments(String nom,String type,String description) {
        this.type = type;
        this.nom=nom;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static String[] typesMédicaments(){
        String[] types={"Antibiotique","Anti-inflammatoire","Antalgique","Bain de bouche"};
        return types;
    }

    public static void ajouterMédicamentsbasiques(){

        ArrayList<String[]> medBasique=new ArrayList<>();
    }
}
