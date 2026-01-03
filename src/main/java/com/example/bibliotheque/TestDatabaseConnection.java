package com.example.bibliotheque;

import com.example.bibliotheque.util.DatabaseConnection;
import java.sql.Connection;

public class TestDatabaseConnection {

    public static void main(String[] args) {
        try {
            Connection c = DatabaseConnection.getInstance().getConnection();

            if (c != null) {
                System.out.println("Connexion MySQL OK");
            } else {
                System.out.println(" Connexion MySQL ÉCHEC");
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion MySQL");
            e.printStackTrace();
        }
    }
}

