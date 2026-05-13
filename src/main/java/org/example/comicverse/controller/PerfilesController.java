package org.example.comicverse.controller;

import DAO.PerfilDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Perfil;
import org.example.comicverse.SesionActual;

import java.io.IOException;
import java.util.List;

public class PerfilesController {

    @FXML private HBox hboxPerfiles;
    @FXML private Label lblError;

    private PerfilDAO perfilDAO = new PerfilDAO();

    /**
     * Este método se ejecuta automáticamente al abrir la ventana.
     * Carga los perfiles del usuario actual y los muestra en pantalla.
     */
    @FXML
    private void initialize() {
        cargarPerfiles();
    }

    /**
     * Método que carga los perfiles del usuario actual desde la base de datos
     * y crea un botón por cada perfil para que el usuario pueda seleccionarlo.
     */
    private void cargarPerfiles() {
        List<Perfil> perfiles = perfilDAO.findByIdUsuario(SesionActual.getUsuario().getId());
        for (Perfil perfil : perfiles) {
            Button btnPerfil = new Button(perfil.getNombre());
            btnPerfil.setOnAction(e -> seleccionarPerfil(perfil));
            hboxPerfiles.getChildren().add(btnPerfil);
        }

        Button btnAnadir = new Button("+ Añadir perfil");
        btnAnadir.setOnAction(e -> handleNuevoPerfil());
        hboxPerfiles.getChildren().add(btnAnadir);
    }

    /**
     * Método que se ejecuta cuando el usuario selecciona un perfil.
     * Guarda el perfil en la sesión y abre la ventana principal.
     * @param perfil el perfil seleccionado
     */
    private void seleccionarPerfil(Perfil perfil) {
        SesionActual.setPerfil(perfil);
        try {
            Stage ventanaActual = (Stage) hboxPerfiles.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/principal-view.fxml"));
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);
        } catch (IOException e) {
            lblError.setText("Error al abrir la ventana principal");
        }
    }

    /**
     * Método que se ejecuta cuando el usuario pulsa el botón de añadir perfil.
     * Abre la ventana de nuevo perfil.
     */
    @FXML
    private void handleNuevoPerfil() {
        try {
            Stage ventanaActual = (Stage) hboxPerfiles.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/nuevo-perfil.fxml"));
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);
        } catch (IOException e) {
            lblError.setText("Error al abrir la ventana de nuevo perfil");
        }
    }
}