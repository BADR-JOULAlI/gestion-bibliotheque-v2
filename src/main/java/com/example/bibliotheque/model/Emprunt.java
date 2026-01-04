package com.example.bibliotheque.model;
import java.time.LocalDate;

public class Emprunt {
    private int id;
    private int livreId;
    private int membreId;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;

    public Emprunt(int id, int livreId, int membreId, LocalDate dateEmprunt, LocalDate dateRetourPrevue) {
        this.id = id;
        this.livreId = livreId;
        this.membreId = membreId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
    }

    // Getters indispensables pour le PropertyValueFactory du Controller
    public int getId() { return id; }
    public int getLivreId() { return livreId; }
    public int getMembreId() { return membreId; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
}