package org.example.comicverse.controller;

import DAO.PerfilDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Perfil;
import org.example.comicverse.SesionActual;

import java.io.IOException;

public class NuevoPerfilController {

    @FXML private TextField txtNombre;
    @FXML private Label lblError;
    @FXML private Button btnCrear;

    private PerfilDAO perfilDAO = new PerfilDAO();

    /**
     * Este método se ejecuta cuando el usuario pulsa el botón Crear.
     * Comprueba que el nombre no esté vacío y crea el perfil en la BBDD.
     */
    @FXML
    private void handleCrear() {

        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            lblError.setText("El nombre no puede estar vacío");
            return;
        }
        Perfil perfil = new Perfil(0, nombre, SesionActual.getUsuario());
        boolean insertado = perfilDAO.insert(perfil);

        if (insertado) {
            try {
                Stage ventanaActual = (Stage) btnCrear.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/perfiles-view.fxml"));
                Scene nuevaEscena = new Scene(loader.load());
                ventanaActual.setScene(nuevaEscena);
            } catch (IOException e) {
                lblError.setText("Error al volver a perfiles");
            }
        } else {
            lblError.setText("Error al crear el perfil");
        }
    }

    /**
     * Este método se ejecuta cuando el usuario pulsa Cancelar.
     * Vuelve a la ventana de perfiles sin crear nada.
     */
    @FXML
    private void handleCancelar() {
        try {
            Stage ventanaActual = (Stage) btnCrear.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/perfiles-view.fxml"));
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);
        } catch (IOException e) {
            lblError.setText("Error al volver a perfiles");
        }
    }
}