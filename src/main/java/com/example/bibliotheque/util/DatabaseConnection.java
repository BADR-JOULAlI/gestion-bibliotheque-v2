package com.example.bibliotheque.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static volatile DatabaseConnection instance;
    private Connection connection;

    // Configuration issue de ta capture
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Constructeur privé
    private DatabaseConnection() {}

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    /**
     * LE CORRECTIF : Vérifie et rouvre la connexion si elle est fermée
     */
    public Connection getConnection() {
        try {
            // Si la connexion est nulle ou si elle a été fermée par un autre module
            if (connection == null || connection.isClosed()) {
                System.out.println("⚠️ Connexion fermée détectée -> Réouverture en cours...");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Erreur de reconnexion MySQL : " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}