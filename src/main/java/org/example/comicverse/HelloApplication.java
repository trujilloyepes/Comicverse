package org.example.comicverse;

import dataAccess.ConnectionBD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {


        // Cargamos la ventana de login como ventana inicial
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ComicVerse");
        stage.setScene(scene);
        stage.show();
    }
}