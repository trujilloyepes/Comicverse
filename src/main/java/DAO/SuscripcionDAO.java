package DAO;

import DAO.Interfaces.IDAO;
import DAO.Interfaces.ISuscripcionDAO;
import dataAccess.ConnectionBD;
import model.Suscripcion;
import model.TipoSuscripcion;
import model.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa las operaciones de acceso a datos para la entidad Suscripcion.
 * Implementa IDAO<Suscripcion> para los métodos CRUD básicos e ISuscripcionDAO para
 * los métodos específicos de Suscripcion.
 */
public class SuscripcionDAO implements IDAO<Suscripcion>, ISuscripcionDAO {

    // Consultas SQL definidas como constantes para evitar repetir código
    private static final String SQL_ALL = "SELECT * FROM suscripcion";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM suscripcion WHERE id_suscripcion = ?";
    private static final String SQL_FIND_BY_USUARIO = "SELECT * FROM suscripcion WHERE id_usuario = ?";
    private static final String SQL_INSERT = "INSERT INTO suscripcion (tipo, fecha_inicio, fecha_fin, id_usuario) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE suscripcion SET tipo = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_suscripcion = ?";
    private static final String SQL_DELETE = "DELETE FROM suscripcion WHERE id_suscripcion = ?";

    /**
     * Devuelve una lista con todas las suscripciones de la base de datos.
     * @return lista con todas las suscripciones con su usuario
     */
    public List<Suscripcion> findAllTodasLasSuscripciones() {
        List<Suscripcion> suscripciones = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int id = rs.getInt("id_suscripcion");
                TipoSuscripcion tipo = TipoSuscripcion.valueOf(rs.getString("tipo").toUpperCase());
                LocalDate fechaInicio = rs.getDate("fecha_inicio").toLocalDate();
                LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
                int idUsuario = rs.getInt("id_usuario");
                // Buscamos el usuario y lo añadimos a la suscripción
                Usuario usuario = new UsuarioDAO().findById(idUsuario);
                suscripciones.add(new Suscripcion(id, tipo, fechaInicio, fechaFin, usuario));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suscripciones;
    }

    @Override
    public List<Suscripcion> findAll() {
        return List.of();
    }

    /**
     * Nos devuelve una suscripción buscándola por su id.
     * @param id identificador único de la suscripción
     * @return objeto Suscripcion si lo encuentra, null si no existe
     */
    @Override
    public Suscripcion findById(int id) {
        Suscripcion suscripcion = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TipoSuscripcion tipo = TipoSuscripcion.valueOf(rs.getString("tipo").toUpperCase());
                LocalDate fechaInicio = rs.getDate("fecha_inicio").toLocalDate();
                LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
                // dejamos el usuario en null
                suscripcion = new Suscripcion(id, tipo, fechaInicio, fechaFin, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suscripcion;
    }

    /**
     * Inserta una nueva suscripción en la base de datos.
     * Comprueba que la suscripción no sea null antes de insertarla.
     * @param suscripcion objeto Suscripcion a insertar
     * @return true si se ha insertado correctamente, false si no
     */
    @Override
    public boolean insert(Suscripcion suscripcion) {
        boolean inserted = false;
        // Comprobamos que la suscripción no sea null y que tenga usuario
        if (suscripcion != null && suscripcion.getUsuario() != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                // Convertimos el enum a String en minúsculas para que coincida con nuestra base de datos
                ps.setString(1, suscripcion.getTipo().name().toLowerCase());
                // Convertimos LocalDate a Date de SQL
                ps.setDate(2, Date.valueOf(suscripcion.getFechaInicio()));
                ps.setDate(3, Date.valueOf(suscripcion.getFechaFin()));
                ps.setInt(4, suscripcion.getUsuario().getId());
                ps.executeUpdate();
                inserted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return inserted;
    }

    /**
     * Actualiza una suscripción existente en la base de datos.
     * Comprueba que la suscripción existe antes de actualizarla.
     * @param suscripcion objeto Suscripcion con los nuevos datos
     * @return true si se ha actualizado correctamente, false si no
     */
    @Override
    public boolean update(Suscripcion suscripcion) {
        boolean updated = false;
        // Comprobamos que la suscripción no sea null y que exista en la base de datos
        if (suscripcion != null && findById(suscripcion.getId()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, suscripcion.getTipo().name().toLowerCase());
                ps.setDate(2, Date.valueOf(suscripcion.getFechaInicio()));
                ps.setDate(3, Date.valueOf(suscripcion.getFechaFin()));
                ps.setInt(4, suscripcion.getId());
                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Elimina una suscripción de la base de datos por su id.
     * Comprueba que la suscripción existe antes de eliminarla.
     * @param id identificador de la suscripción a eliminar
     * @return true si se ha eliminado correctamente, false si no
     */
    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        // Comprobamos que la suscripción existe antes de intentar eliminarla
        if (findById(id) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, id);
                ps.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }

    /**
     * Busca la suscripción de un usuario concreto.
     * Cada usuario solo puede tener una suscripción activa.
     * @param idUsuario identificador del usuario
     * @return objeto Suscripcion si la tiene, null si no tiene suscripción
     */
    @Override
    public Suscripcion findByUsuario(int idUsuario) {
        Suscripcion suscripcion = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_USUARIO)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_suscripcion");
                TipoSuscripcion tipo = TipoSuscripcion.valueOf(rs.getString("tipo").toUpperCase());
                LocalDate fechaInicio = rs.getDate("fecha_inicio").toLocalDate();
                LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
                // dejamos el usuario en null
                suscripcion = new Suscripcion(id, tipo, fechaInicio, fechaFin, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suscripcion;
    }
}
