package com.example.bibliotheque.controller;

import com.example.bibliotheque.dao.impl.EmpruntDAOImpl;
import com.example.bibliotheque.dao.impl.LivreDAOImpl;
import com.example.bibliotheque.dao.impl.MembreDAOImpl;
import com.example.bibliotheque.model.Emprunt;
import com.example.bibliotheque.model.Livre;
import com.example.bibliotheque.model.Membre;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import java.time.LocalDate;

public class EmpruntController {
    @FXML private ComboBox<Livre> comboLivres;
    @FXML private ComboBox<Membre> comboMembres;
    @FXML private DatePicker dateRetourPrevue;
    @FXML private TableView<Emprunt> tableEmprunts;
    @FXML private TableColumn<Emprunt, Integer> colLivre, colMembre;
    @FXML private TableColumn<Emprunt, LocalDate> colDateEmprunt, colDateRetour;

    private EmpruntDAOImpl empruntDAO = new EmpruntDAOImpl();
    private LivreDAOImpl livreDAO = new LivreDAOImpl();
    private MembreDAOImpl membreDAO = new MembreDAOImpl();

    @FXML
    public void initialize() {
        // 1. Configurer l'affichage des ComboBox (pour voir les titres/noms et non des codes)
        configurerComboBox();

        // 2. Charger les données initiales
        chargerDonneesCombos();

        // 3. Liaison des colonnes du tableau
        colLivre.setCellValueFactory(new PropertyValueFactory<>("livreId"));
        colMembre.setCellValueFactory(new PropertyValueFactory<>("membreId"));
        colDateEmprunt.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        colDateRetour.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevue"));

        chargerTableau();
    }

    private void chargerDonneesCombos() {
        comboLivres.setItems(FXCollections.observableArrayList(livreDAO.findAll()));
        comboMembres.setItems(FXCollections.observableArrayList(membreDAO.findAll()));
    }

    @FXML
    private void handleEnregistrer() {
        Livre livre = comboLivres.getValue();
        Membre membre = comboMembres.getValue();
        LocalDate date = dateRetourPrevue.getValue();

        if (livre != null && membre != null && date != null) {
            Emprunt e = new Emprunt(0, livre.getId(), membre.getId(), LocalDate.now(), date);
            empruntDAO.enregistrerEmprunt(e);
            chargerTableau();
            afficherAlerte("Succès", "L'emprunt a été enregistré dans MySQL.");
        } else {
            afficherAlerte("Erreur", "Veuillez remplir tous les champs avant de valider.");
        }
    }

    @FXML
    private void handleRetour() {
        Emprunt selection = tableEmprunts.getSelectionModel().getSelectedItem();
        if (selection != null) {
            empruntDAO.retournerLivre(selection.getId());
            chargerTableau();
            afficherAlerte("Retour", "Le livre a été marqué comme retourné.");
        }
    }

    private void chargerTableau() {
        tableEmprunts.setItems(FXCollections.observableArrayList(empruntDAO.findAll()));
    }

    private void configurerComboBox() {
        comboLivres.setConverter(new StringConverter<Livre>() {
            @Override public String toString(Livre l) { return l == null ? "" : l.getTitre(); }
            @Override public Livre fromString(String s) { return null; }
        });
        comboMembres.setConverter(new StringConverter<Membre>() {
            @Override public String toString(Membre m) { return m == null ? "" : m.getNom() + " " + m.getPrenom(); }
            @Override public Membre fromString(String s) { return null; }
        });
    }

    private void afficherAlerte(String titre, String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}