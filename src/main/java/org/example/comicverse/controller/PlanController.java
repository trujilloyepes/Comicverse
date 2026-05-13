package org.example.comicverse.controller;

import DAO.SuscripcionDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Suscripcion;
import model.TipoSuscripcion;
import org.example.comicverse.SesionActual;

import java.io.IOException;
import java.time.LocalDate;

public class PlanController {

    @FXML private Button btnMensual;
    @FXML private Button btnAnual;
    @FXML private Label lblError;

    private SuscripcionDAO suscripcionDAO = new SuscripcionDAO();

    /**
     * Este método se ejecuta cuando el usuario pulsa el botón de plan mensual.
     * Crea una suscripción mensual y la guarda en la base de datos.
     */
    @FXML
    private void handleMensual() {
        crearSuscripcion(TipoSuscripcion.MENSUAL);
    }

    /**
     * Este método se ejecuta cuando el usuario pulsa el botón de plan anual.
     * Crea una suscripción anual y la guarda en la base de datos.
     */
    @FXML
    private void handleAnual() {
        crearSuscripcion(TipoSuscripcion.ANUAL);
    }

    /**
     * Método que crea la suscripción y abre la ventana de perfiles.
     * @param tipo el tipo de suscripción elegido (MENSUAL o ANUAL)
     */
    private void crearSuscripcion(TipoSuscripcion tipo) {

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin;

        if (tipo == TipoSuscripcion.MENSUAL) {
            fechaFin = fechaInicio.plusMonths(1);
        } else {
            fechaFin = fechaInicio.plusYears(1);
        }

        Suscripcion suscripcion = new Suscripcion(0, tipo, fechaInicio, fechaFin, SesionActual.getUsuario());
        boolean insertado = suscripcionDAO.insert(suscripcion);

        if (insertado) {
            try {
                Stage ventanaActual = (Stage) btnMensual.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/comicverse/perfiles-view.fxml"));
                Scene nuevaEscena = new Scene(loader.load());
                ventanaActual.setScene(nuevaEscena);
            } catch (IOException e) {
                lblError.setText("Error al abrir la siguiente ventana");
            }
        } else {
            lblError.setText("Error al guardar la suscripción");
        }
    }
}
