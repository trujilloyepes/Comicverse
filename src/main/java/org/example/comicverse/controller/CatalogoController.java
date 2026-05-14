package org.example.comicverse.controller;

import DAO.ComicDAO;
import DAO.GeneroDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.Comic;
import model.Genero;

import java.io.IOException;
import java.util.List;

public class CatalogoController {

    @FXML private TextField txtBuscar;
    @FXML private FlowPane flowPaneComics;
    @FXML private Label lblError;

    private ComicDAO comicDAO = new ComicDAO();
    /**
     * Este método se ejecuta automáticamente al abrir la ventana.
     * Carga todos los cómics y los muestra en pantalla.
     */
    @FXML
    private void initialize() {
        cargarTodos();
    }
    /**
     * Método que carga todos los cómics de la base de datos
     * y los muestra en el FlowPane.
     */
    private void cargarTodos() {
        flowPaneComics.getChildren().clear();
        List<Comic> comics = comicDAO.findAll();
        mostrarComics(comics);
    }
    /**
     * Método que crea un botón por cada cómic y lo añade al FlowPane.
     * @param comics lista de cómics a mostrar
     */
    private void mostrarComics(List<Comic> comics) {
        flowPaneComics.getChildren().clear();
        for (Comic comic : comics) {
            Button btnComic = new Button(comic.getTitulo() + "\n" + comic.getEditorial());
            btnComic.setStyle("-fx-background-color: #2a2a4a; -fx-text-fill: white; -fx-background-radius: 8;");
            btnComic.setPrefWidth(150);
            btnComic.setPrefHeight(180);
            flowPaneComics.getChildren().add(btnComic);
        }
    }
    /**
     * Este método se ejecuta cuando el usuario escribe en el buscador.
     * Busca cómics que contengan el texto escrito.
     */
    @FXML
    private void handleBuscar() {
        String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            cargarTodos();
        } else {
            List<Comic> comics = comicDAO.findByTitulo(texto);
            mostrarComics(comics);
        }
    }
    /**
     * Este método filtra los cómics por editorial Marvel.
     */
    @FXML
    private void handleMarvel() {
        List<Comic> comics = comicDAO.findByEditorial("Marvel");
        mostrarComics(comics);
    }
    /**
     * Este método filtra los cómics por editorial DC.
     */
    @FXML
    private void handleDC() {
        List<Comic> comics = comicDAO.findByEditorial("DC");
        mostrarComics(comics);
    }
    /**
     * Este método muestra todos los cómics sin filtro.
     */
    @FXML
    private void handleTodos() {
        cargarTodos();
    }
    /**
     * Este método vuelve a la ventana principal.
     */
    @FXML
    private void handleVolver() {
        try {
            Stage ventanaActual = (Stage) txtBuscar.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/principal-view.fxml"));
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);
        } catch (IOException e) {
            lblError.setText("Error al volver al menú principal");
        }
    }
}