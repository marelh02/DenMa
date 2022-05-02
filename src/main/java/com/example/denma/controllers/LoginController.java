package com.example.denma.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class LoginController {
    @FXML
    TextField pseudo;
    @FXML
    PasswordField mdp;
    @FXML
    Text connexionRq;

    public void login(ActionEvent event){
        String userName=pseudo.getText();
        String userPass=mdp.getText();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query="select pass from user where login = "+userName;
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/dentiste","root","roottt");
            ResultSet rs=con.createStatement().executeQuery(query);
            if(rs.next())
            {
                if (userPass.equals(rs.getString("pass"))) System.out.println("muy bien");
                else connexionRq.setText("mot de passe incorrect");
            }
            else connexionRq.setText("ce pseudo n'existe même pas, DZL...");
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
}
