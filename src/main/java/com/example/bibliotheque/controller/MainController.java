package com.example.bibliotheque.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainController {
    @FXML private StackPane contentArea;
    @FXML private Label lblCurrentView;

    @FXML
    public void showLivres() { loadPage("LivreView.fxml", "Catalogue des Livres"); }

    @FXML
    public void showMembres() { loadPage("MembreView.fxml", "Gestion des Membres"); }

    @FXML
    public void showEmprunts() { loadPage("EmpruntView.fxml", "Gestion des Emprunts"); }

    private void loadPage(String fxml, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/bibliotheque/view/" + fxml));
            contentArea.getChildren().setAll(root);
            lblCurrentView.setText(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleQuitter() { System.exit(0); }

}
