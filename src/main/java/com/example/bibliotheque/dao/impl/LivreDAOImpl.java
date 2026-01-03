package com.example.bibliotheque.dao.impl;

import com.example.bibliotheque.dao.LivreDAO;
import com.example.bibliotheque.exception.DaoException;
import com.example.bibliotheque.model.Livre;
import com.example.bibliotheque.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAOImpl implements LivreDAO {

    private static final String INSERT_SQL =
            "INSERT INTO livres (titre, auteur, isbn, annee_publication, disponible) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, titre, auteur, isbn, annee_publication, disponible FROM livres WHERE id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT id, titre, auteur, isbn, annee_publication, disponible FROM livres";

    private static final String DELETE_SQL =
            "DELETE FROM livres WHERE id = ?";

    private static final String SELECT_BY_TITRE_SQL =
            "SELECT id, titre, auteur, isbn, annee_publication, disponible FROM livres WHERE titre LIKE ?";

    @Override
    public void save(Livre livre) throws DaoException {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setString(3, livre.getIsbn());
            ps.setInt(4, livre.getAnneePublication());
            ps.setBoolean(5, livre.isDisponible());

            ps.executeUpdate();

            // Récupérer l'id auto-incrémenté
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    livre.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Erreur SQL lors de l'insertion du livre.", e);
        }
    }

    @Override
    public Livre findById(int id) throws DaoException {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToLivre(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new DaoException("Erreur SQL lors de la recherche du livre par id.", e);
        }
    }

    @Override
    public List<Livre> findAll() throws DaoException {
        List<Livre> livres = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                livres.add(mapRowToLivre(rs));
            }

            return livres;

        } catch (SQLException e) {
            throw new DaoException("Erreur SQL lors de la récupération de tous les livres.", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Erreur SQL lors de la suppression du livre.", e);
        }
    }

    @Override
    public List<Livre> findByTitre(String motCle) throws DaoException {
        List<Livre> livres = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_TITRE_SQL)) {

            ps.setString(1, "%" + motCle + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    livres.add(mapRowToLivre(rs));
                }
            }

            return livres;

        } catch (SQLException e) {
            throw new DaoException("Erreur SQL lors de la recherche des livres par titre.", e);
        }
    }

    private Livre mapRowToLivre(ResultSet rs) throws SQLException {
        Livre livre = new Livre();
        livre.setId(rs.getInt("id"));
        livre.setTitre(rs.getString("titre"));
        livre.setAuteur(rs.getString("auteur"));
        livre.setIsbn(rs.getString("isbn"));
        livre.setAnneePublication(rs.getInt("annee_publication"));
        livre.setDisponible(rs.getBoolean("disponible"));
        return livre;
    }
}
