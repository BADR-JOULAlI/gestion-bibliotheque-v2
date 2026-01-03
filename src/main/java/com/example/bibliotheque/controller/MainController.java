package com.example.bibliotheque.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class MainController {
    @FXML
    private void handleQuitter() {
        Platform.exit();
    }
}