package com.example.bibliotheque.controller; //

import com.example.bibliotheque.model.Membre;
import com.example.bibliotheque.dao.impl.MembreDAOImpl;
import com.example.bibliotheque.util.StringValidator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class MembreController {

    // Éléments de la table
    @FXML private TableView<Membre> tableMembres;
    @FXML private TableColumn<Membre, Integer> colId;
    @FXML private TableColumn<Membre, String> colNom;
    @FXML private TableColumn<Membre, String> colPrenom;
    @FXML private TableColumn<Membre, String> colEmail;
    @FXML private TableColumn<Membre, String> colTelephone;

    // Champs de saisie du formulaire
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelephone;

    // Instance du DAO pour les opérations MySQL
    private MembreDAOImpl dao = new MembreDAOImpl();

    @FXML
    public void initialize() {
        // Liaison des colonnes avec les attributs de l'objet Membre
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        // Chargement initial des données au démarrage de l'onglet
        chargerDonnees();
    }

    /**
     * Méthode appelée par le bouton "Ajouter" (onAction="#handleAjouter")
     */
    @FXML
    private void handleAjouter() {
        String email = txtEmail.getText();

        // Validation de l'email avec l'utilitaire de Badr
        if (!StringValidator.isValidEmail(email)) {
            afficherAlerte(Alert.AlertType.ERROR, "Email invalide", "Veuillez saisir un email correct (ex: nom@mail.com)");
            return;
        }

        // Création et enregistrement du membre
        Membre nouveau = new Membre(0, txtNom.getText(), txtPrenom.getText(), email, txtTelephone.getText());
        dao.save(nouveau);

        // Rafraîchir l'affichage et vider les champs
        chargerDonnees();
        nettoyerChamps();
        afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Membre ajouté avec succès !");
    }

    /**
     * Méthode appelée par le bouton "Supprimer" (onAction="#handleSupprimer")
     */
    @FXML
    private void handleSupprimer() {
        Membre selection = tableMembres.getSelectionModel().getSelectedItem();

        if (selection != null) {
            dao.delete(selection.getId());
            chargerDonnees();
            afficherAlerte(Alert.AlertType.INFORMATION, "Suppression", "Le membre a été supprimé.");
        } else {
            afficherAlerte(Alert.AlertType.WARNING, "Sélection", "Veuillez sélectionner un membre dans le tableau.");
        }
    }

    private void chargerDonnees() {
        List<Membre> membres = dao.findAll();
        tableMembres.setItems(FXCollections.observableArrayList(membres));
    }

    private void nettoyerChamps() {
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        txtTelephone.clear();
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}