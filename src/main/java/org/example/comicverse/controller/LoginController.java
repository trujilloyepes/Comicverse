package org.example.comicverse.controller;

import DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;

import java.io.IOException;

/**
 * Controlador de la ventana de login.
 * Recoge el email y la contraseña, comprueba si el usuario existe
 * en la base de datos y si es correcto abre la siguiente ventana.
 */
public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblError;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Método que se ejecuta cuando el usuario pulsa el botón "Entrar".
     * Comprueba que los campos no estén vacíos, busca el usuario por email
     * y comprueba que la contraseña sea correcta.
     */
    @FXML
    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        // Comprobamos que los campos no estén vacíos
        if (email.isEmpty() || contrasena.isEmpty()) {
            lblError.setText("Por favor rellena todos los campos");
            return;
        }

        // Buscamos el usuario por email en la base de datos
        Usuario usuario = usuarioDAO.findByEmail(email);

        // Comprobamos que el usuario existe y que la contraseña es correcta
        if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
            lblError.setText("Email o contraseña incorrectos");
            return;
        }

        // Si el usuario es administrador va al panel admin, si no va a elegir plan
        try {
            FXMLLoader loader;
            if (usuario.isAdministrador()) {
                loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/admin-view.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/plan-view.fxml"));
            }
            Stage stage = (Stage) btnEntrar.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            lblError.setText("Error al cargar la siguiente ventana");
        }
    }

    /**
     * Método que se ejecuta cuando el usuario pulsa "¿No tienes cuenta? Regístrate".
     * Abre la ventana de registro.
     */
    @FXML
    private void handleRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/registro-view.fxml"));
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            lblError.setText("Error al cargar la ventana de registro");
        }
    }
}