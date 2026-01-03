package com.example.bibliotheque.util;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Attention au chemin : il commence par / car c'est une ressource
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/bibliotheque/view/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Système de Gestion de Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}