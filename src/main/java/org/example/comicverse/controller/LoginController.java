package org.example.comicverse.controller;

import DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;
import org.example.comicverse.SesionActual;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblError;
    @FXML private Button btnEntrar;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Este método se ejecuta cuando el usuario pulsa el botón Entrar.
     * Comprueba que los campos no estén vacíos, busca el usuario en la
     * base de datos y si todo es correcto abre la siguiente ventana.
     */
    @FXML
    private void handleLogin() {

        String email = txtEmail.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (email.isEmpty() || contrasena.isEmpty()) {
            lblError.setText("Por favor rellena todos los campos");
            return;
        }

        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario == null) {
            lblError.setText("El email no está registrado");
            return;
        }
        if (!usuario.getContrasena().equals(contrasena)) {
            lblError.setText("Contraseña incorrecta");
            return;
        }
        SesionActual.setUsuario(usuario);
        System.out.println("Usuario en sesión: " + SesionActual.getUsuario());
        try {
            Stage ventanaActual = (Stage) btnEntrar.getScene().getWindow();

            FXMLLoader loader;
            if (usuario.isAdministrador()) {
                loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/admin-view.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/perfiles-view.fxml"));
            }
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);

        } catch (IOException e) {
            e.printStackTrace();
            lblError.setText("Error al abrir la siguiente ventana");
        }
    }

    /**
     * Este método se ejecuta cuando el usuario pulsa el link de registro.
     * Abre la ventana de registro.
     */
    @FXML
    private void handleRegistro() {
        try {
            Stage ventanaActual = (Stage) txtEmail.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/registro-view.fxml"));
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);
        } catch (IOException e) {
            lblError.setText("Error al abrir la ventana de registro");
        }
    }
}