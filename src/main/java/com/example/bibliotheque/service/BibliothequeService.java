package com.example.bibliotheque.service;

import com.example.bibliotheque.dao.LivreDAO;
import com.example.bibliotheque.dao.impl.LivreDAOImpl;
import com.example.bibliotheque.exception.DaoException;
import com.example.bibliotheque.exception.ValidationException;
import com.example.bibliotheque.model.Livre;

import java.util.List;

public class BibliothequeService {

    private final LivreDAO livreDAO;

    public BibliothequeService() {
        this.livreDAO = new LivreDAOImpl();
    }

    public void ajouterLivre(Livre livre) throws ValidationException {
        if (livre.getTitre() == null || livre.getTitre().trim().isEmpty()) {
            throw new ValidationException("Le titre est obligatoire");
        }

        if (livre.getAuteur() == null || livre.getAuteur().trim().isEmpty()) {
            throw new ValidationException("L'auteur est obligatoire");
        }

        try {
            livreDAO.save(livre);
        } catch (DaoException e) {
            throw new RuntimeException("Erreur lors de l'ajout du livre", e);
        }
    }

    public List<Livre> getTousLesLivres() {
        try {
            return livreDAO.findAll();
        } catch (DaoException e) {
            throw new RuntimeException("Erreur lors de la récupération des livres", e);
        }
    }

    public void supprimerLivre(int id) {
        try {
            livreDAO.delete(id);
        } catch (DaoException e) {
            throw new RuntimeException("Erreur lors de la suppression du livre", e);
        }
    }
}
