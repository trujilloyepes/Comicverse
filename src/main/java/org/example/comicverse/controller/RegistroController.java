package org.example.comicverse.controller;

import DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;
import org.example.comicverse.SesionActual;

import java.io.IOException;

public class RegistroController {
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtContrasena;
    @FXML private CheckBox chkAdmin;
    @FXML private Label lblError;
    @FXML private Button btnRegistrar;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Este método se ejecuta cuando el usuario pulsa el botón Registrarme.
     * Comprueba que los campos no estén vacíos, crea el usuario y lo guarda
     * en la base de datos.
     */
    @FXML
    private void handleRegistro() {
        String email = txtEmail.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        if (email.isEmpty() || contrasena.isEmpty()) {
            lblError.setText("Por favor rellena todos los campos");
            return;
        }
        if (usuarioDAO.findByEmail(email) != null) {
            lblError.setText("Ese email ya está registrado");
            return;
        }
        // Si el checkbox está marcado es admin, si no es usuario normal
        // Esto lo que nos hace es buscar en la base de datos el número
        //true=1, false=0
        boolean esAdmin = chkAdmin.isSelected();
        Usuario usuario = new Usuario(0, email, contrasena, esAdmin);
        boolean insertado = usuarioDAO.insert(usuario);

        if (insertado) {
            SesionActual.setUsuario(usuarioDAO.findByEmail(email));
            try {
                Stage ventanaActual = (Stage) btnRegistrar.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/plan-view.fxml"));
                Scene nuevaEscena = new Scene(loader.load());
                ventanaActual.setScene(nuevaEscena);
            } catch (IOException e) {
                lblError.setText("Error al abrir la siguiente ventana");
            }
        } else {
            lblError.setText("Error al registrar el usuario");
        }
    }

    /**
     * Este método se ejecuta cuando el usuario pulsa el link de login.
     * Abre la ventana de login.
     */
    @FXML
    private void handleLogin() {
        try {
            Stage ventanaActual = (Stage) txtEmail.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/login-view.fxml"));
            Scene nuevaEscena = new Scene(loader.load());
            ventanaActual.setScene(nuevaEscena);
        } catch (IOException e) {
            lblError.setText("Error al abrir la ventana de login");
        }
    }
}
