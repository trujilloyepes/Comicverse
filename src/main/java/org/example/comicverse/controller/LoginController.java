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

        // Recogemos lo que ha escrito el usuario en los campos
        String email = txtEmail.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        // Si algún campo está vacío mostramos error y no seguimos
        if (email.isEmpty() || contrasena.isEmpty()) {
            lblError.setText("Por favor rellena todos los campos");
            return;
        }

        // Buscamos el usuario en la base de datos por su email
        Usuario usuario = usuarioDAO.findByEmail(email);

        // Si no existe el usuario mostramos error y no seguimos
        if (usuario == null) {
            lblError.setText("El email no está registrado");
            return;
        }

        // Si la contraseña no coincide mostramos error y no seguimos
        if (!usuario.getContrasena().equals(contrasena)) {
            lblError.setText("Contraseña incorrecta");
            return;
        }

        // Si llegamos aquí el login es correcto
        // Guardamos el usuario en una variable estática para usarlo en otras ventanas
        SesionActual.setUsuario(usuario);

        // Cargamos la siguiente ventana
        try {
            // Obtenemos la ventana actual
            Stage ventanaActual = (Stage) btnEntrar.getScene().getWindow();

            FXMLLoader loader;

            // Si es administrador va al panel admin
            if (usuario.isAdministrador()) {
                loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/admin-view.fxml"));
            } else {
                // Si es usuario normal va a elegir el plan
                loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/plan-view.fxml"));
            }

            // Cargamos el fxml y lo mostramos en la ventana actual
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);

        } catch (IOException e) {
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
            // Obtenemos la ventana actual
            Stage ventanaActual = (Stage) txtEmail.getScene().getWindow();
            // Cargamos la ventana de registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/registro-view.fxml"));
            // Mostramos la ventana de registro
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);

        } catch (IOException e) {
            /**
             * es una etiqueta vacía en la ventana que solo aparece con texto cuando algo sale mal.
             * Por ejemplo si el email está mal escrito, en vez de no pasar nada,
             * el usuario ve un mensaje en rojo que le explica qué ha fallado
             */
            lblError.setText("Error al abrir la ventana de registro");
        }
    }
}