package com.example.bibliotheque.controller;

import com.example.bibliotheque.model.Livre;
import com.example.bibliotheque.service.BibliothequeService;
import com.example.bibliotheque.exception.ValidationException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Contrôleur pour la gestion des livres (Module Personne 2 - Ijlal)
 */
public class LivreController {

    // Éléments de l'interface FXML (doivent correspondre aux fx:id du fichier LivreView.fxml)
    @FXML private TableView<Livre> tableLivres;
    @FXML private TableColumn<Livre, Integer> colId;
    @FXML private TableColumn<Livre, String> colTitre;
    @FXML private TableColumn<Livre, String> colAuteur;
    @FXML private TableColumn<Livre, String> colIsbn;
    @FXML private TableColumn<Livre, Integer> colAnnee;
    @FXML private TableColumn<Livre, Boolean> colDisponible;

    @FXML private TextField txtTitre;
    @FXML private TextField txtAuteur;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtAnnee;
    @FXML private CheckBox chkDisponible;

    // Instance du service de bibliothèque
    private BibliothequeService service = new BibliothequeService();

    /**
     * Méthode appelée automatiquement au chargement de la vue FXML
     */
    @FXML
    public void initialize() {
        // Configuration des colonnes pour lier les attributs de la classe Livre au tableau
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colAuteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colAnnee.setCellValueFactory(new PropertyValueFactory<>("anneePublication"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        // Chargement initial des données depuis la base de données
        rafraichirTableau();
    }

    /**
     * Gère l'action du bouton "Ajouter"
     */
    @FXML
    private void handleAjouter() {
        try {
            // Lecture des données depuis les champs de texte
            String titre = txtTitre.getText();
            String auteur = txtAuteur.getText();
            String isbn = txtIsbn.getText();

            // Conversion de l'année (gestion de l'erreur si ce n'est pas un nombre)
            int annee = Integer.parseInt(txtAnnee.getText());
            boolean dispo = chkDisponible.isSelected();

            // Création de l'objet Livre (ID 0 car auto-incrémenté par la base de données)
            Livre nouveauLivre = new Livre(0, titre, auteur, isbn, annee, dispo);

            // Appel du service pour ajouter le livre (vérifie les validations métier)
            service.ajouterLivre(nouveauLivre);

            // Mise à jour de l'interface
            rafraichirTableau();
            nettoyerChamps();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Livre ajouté avec succès !");

        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur de saisie", "L'année doit être un nombre entier valide.");
        } catch (ValidationException e) {
            // Affiche le message d'erreur défini dans BibliothequeService (ex: "Le titre est obligatoire")
            afficherAlerte(Alert.AlertType.ERROR, "Erreur de validation", e.getMessage());
        } catch (Exception e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur système", "Une erreur est survenue lors de l'ajout.");
            e.printStackTrace();
        }
    }

    /**
     * Gère l'action du bouton "Supprimer"
     */
    @FXML
    private void handleSupprimer() {
        // Récupération du livre sélectionné dans le tableau
        Livre livreSelectionne = tableLivres.getSelectionModel().getSelectedItem();

        if (livreSelectionne != null) {
            try {
                // Suppression via le service
                service.supprimerLivre(livreSelectionne.getId());
                rafraichirTableau();
                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Le livre a été supprimé.");
            } catch (Exception e) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer le livre.");
            }
        } else {
            afficherAlerte(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un livre dans le tableau.");
        }
    }

    /**
     * Charge ou rafraîchit la liste des livres dans le tableau
     */
    private void rafraichirTableau() {
        tableLivres.setItems(FXCollections.observableArrayList(service.getTousLesLivres()));
    }

    /**
     * Réinitialise les champs du formulaire
     */
    private void nettoyerChamps() {
        txtTitre.clear();
        txtAuteur.clear();
        txtIsbn.clear();
        txtAnnee.clear();
        chkDisponible.setSelected(true);
    }

    /**
     * Utilitaire pour afficher des messages à l'utilisateur
     */
    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}