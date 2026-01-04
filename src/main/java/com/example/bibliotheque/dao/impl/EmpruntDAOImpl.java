package com.example.bibliotheque.dao.impl;

import com.example.bibliotheque.dao.EmpruntDAO;
import com.example.bibliotheque.exception.DaoException;
import com.example.bibliotheque.model.Emprunt;
import com.example.bibliotheque.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAOImpl implements EmpruntDAO {

    @Override
    public void enregistrerEmprunt(Emprunt emprunt) {
        String sql = "INSERT INTO emprunts (livre_id, membre_id, date_emprunt, date_retour_prevue) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, emprunt.getLivreId());
            pstmt.setInt(2, emprunt.getMembreId());
            pstmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            pstmt.setDate(4, Date.valueOf(emprunt.getDateRetourPrevue()));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Erreur lors de l'insertion SQL de l'emprunt", e);
        }
    }

    @Override
    public void retournerLivre(int empruntId) {
        String sql = "UPDATE emprunts SET date_retour_effective = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(java.time.LocalDate.now()));
            pstmt.setInt(2, empruntId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erreur SQL lors du retour", e);
        }
    }

    @Override
    public List<Emprunt> findAll() {
        List<Emprunt> list = new ArrayList<>();
        String sql = "SELECT * FROM emprunts";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Emprunt(
                        rs.getInt("id"), rs.getInt("livre_id"), rs.getInt("membre_id"),
                        rs.getDate("date_emprunt").toLocalDate(),
                        rs.getDate("date_retour_prevue").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur lors du chargement des emprunts", e);
        }
        return list;
    }
}