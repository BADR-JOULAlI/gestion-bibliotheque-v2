package com.example.bibliotheque.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    // Calcule la date de retour prévue (ex: aujourd'hui + 14 jours)
    public static LocalDate calculerDateRetour(LocalDate dateEmprunt, int jours) {
        return dateEmprunt.plusDays(jours);
    }

    // Calcule le nombre de jours de retard
    public static long calculerJoursRetard(LocalDate dateRetourPrevue) {
        LocalDate aujourdhui = LocalDate.now();
        if (aujourdhui.isAfter(dateRetourPrevue)) {
            return ChronoUnit.DAYS.between(dateRetourPrevue, aujourdhui);
        }
        return 0;
    }
}