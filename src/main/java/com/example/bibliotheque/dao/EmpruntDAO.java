package com.example.bibliotheque.dao;

import com.example.bibliotheque.model.Emprunt;
import java.util.List;

public interface EmpruntDAO {
    void enregistrerEmprunt(Emprunt emprunt);
    void retournerLivre(int empruntId);
    List<Emprunt> findAll();
}