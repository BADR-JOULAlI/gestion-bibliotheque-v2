package com.example.bibliotheque.dao.impl; // Vérifie bien cette ligne !

import com.example.bibliotheque.dao.MembreDAO;
import com.example.bibliotheque.exception.DaoException;
import com.example.bibliotheque.model.Membre;
import com.example.bibliotheque.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDAOImpl implements MembreDAO {

    @Override
    public void save(Membre membre) {
        String sql = "INSERT INTO membres (nom, prenom, email, telephone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, membre.getNom());
            pstmt.setString(2, membre.getPrenom());
            pstmt.setString(3, membre.getEmail());
            pstmt.setString(4, membre.getTelephone());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erreur sauvegarde membre", e);
        }
    }

    @Override
    public List<Membre> findAll() {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT * FROM membres";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                membres.add(new Membre(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("email"), rs.getString("telephone")));
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur chargement membres", e);
        }
        return membres;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM membres WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erreur suppression membre", e);
        }
    }

    @Override
    public Membre findById(int id) {
        String sql = "SELECT * FROM membres WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Membre(rs.getInt("id"), rs.getString("nom"),
                            rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur recherche membre", e);
        }
        return null;
    }

    @Override
    public void update(Membre membre) {
        String sql = "UPDATE membres SET nom=?, prenom=?, email=?, telephone=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, membre.getNom());
            pstmt.setString(2, membre.getPrenom());
            pstmt.setString(3, membre.getEmail());
            pstmt.setString(4, membre.getTelephone());
            pstmt.setInt(5, membre.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erreur mise à jour membre", e);
        }
    }
}