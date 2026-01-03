package com.example.bibliotheque.dao;

import com.example.bibliotheque.exception.DaoException;
import com.example.bibliotheque.model.Livre;

import java.util.List;

public interface LivreDAO {
    void save(Livre livre) throws DaoException;

    Livre findById(int id) throws DaoException;

    List<Livre> findAll() throws DaoException;

    void delete(int id) throws DaoException;

    List<Livre> findByTitre(String motCle) throws DaoException;
}
