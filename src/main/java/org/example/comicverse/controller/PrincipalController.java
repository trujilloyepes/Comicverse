package org.example.comicverse.controller;

import DAO.ComicDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Comic;
import org.example.comicverse.SesionActual;

import java.io.IOException;
import java.util.List;

public class PrincipalController {
    @FXML private Label lblBienvenida;
    @FXML private HBox hboxMarvel;
    @FXML private HBox hboxDC;
    private ComicDAO comicDAO = new ComicDAO();

    /**
     * Este método se ejecuta automáticamente al abrir la ventana.
     * Carga los cómics de Marvel y DC y los muestra en pantalla.
     */
    @FXML
    private void initialize() {
        lblBienvenida.setText("Bienvenido, " + SesionActual.getPerfil().getNombre());
        cargarComics(hboxMarvel, "Marvel");
        cargarComics(hboxDC, "DC");
    }

    /**
     * Método que carga los cómics de una editorial y crea un botón por cada uno.
     * @param hbox el contenedor donde se añaden los botones
     * @param editorial la editorial de los cómics a cargar
     */
    private void cargarComics(HBox hbox, String editorial) {
        List<Comic> comics = comicDAO.findByEditorial(editorial);
        for (Comic comic : comics) {
            Button btnComic = new Button(comic.getTitulo());
            btnComic.setStyle("-fx-background-color: #2a2a4a; -fx-text-fill: white; -fx-background-radius: 8;");
            btnComic.setPrefWidth(150);
            btnComic.setPrefHeight(200);
            hbox.getChildren().add(btnComic);
        }
    }

    /**
     * Este método se ejecuta cuando el usuario pulsa el botón Catálogo.
     * Abre la ventana del catálogo.
     */
    @FXML
    private void handleCatalogo() {
        try {
            Stage ventanaActual = (Stage) lblBienvenida.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/catalogo-view.fxml"));
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);
        } catch (IOException e) {
            System.out.println("Error al abrir catálogo");
        }
    }

    /**
     * Este método se ejecuta cuando el usuario pulsa el botón Biblioteca.
     * Abre la ventana de la biblioteca.
     */
    @FXML
    private void handleBiblioteca() {
        try {
            Stage ventanaActual = (Stage) lblBienvenida.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/biblioteca-view.fxml"));
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);
        } catch (IOException e) {
            System.out.println("Error al abrir biblioteca");
        }
    }

    /**
     * Este método se ejecuta cuando el usuario pulsa el botón Cerrar Sesión.
     * Limpia la sesión y vuelve al login.
     */
    @FXML
    private void handleCerrarSesion() {
        try {
            SesionActual.setUsuario(null);
            SesionActual.setPerfil(null);
            Stage ventanaActual = (Stage) lblBienvenida.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/login-view.fxml"));
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);
        } catch (IOException e) {
            System.out.println("Error al cerrar sesión");
        }
    }
}