package com.example.bibliotheque;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label(" Gestion de Bibliothèque - JavaFX OK !");
        StackPane root = new StackPane(label);

        Scene scene = new Scene(root, 400, 200);

        stage.setTitle("Gestion Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

