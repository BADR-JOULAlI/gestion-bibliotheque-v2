package com.example.bibliotheque.util;

public class StringValidator {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidISBN(String isbn) {
        if (isEmpty(isbn)) return false;
        // Format simplifié : 10 ou 13 chiffres
        String clean = isbn.replace("-", "").replace(" ", "");
        return clean.matches("\\d{10}|\\d{13}");
    }
}