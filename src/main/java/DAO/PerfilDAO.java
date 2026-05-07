package DAO;

import DAO.Interfaces.IDAO;
import DAO.Interfaces.IPerfilDAO;
import dataAccess.ConnectionBD;
import model.Perfil;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerfilDAO implements IDAO<Perfil>, IPerfilDAO {

    // Consultas SQL definidas como constantes para evitar repetir código
    private static final String SQL_ALL = "SELECT * FROM perfil";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM perfil WHERE id_perfil = ?";
    private static final String SQL_FIND_BY_ID_USUARIO = "SELECT * FROM perfil WHERE id_usuario = ?";
    private static final String SQL_INSERT = "INSERT INTO perfil (nombre, id_usuario) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE perfil SET nombre = ? WHERE id_perfil = ?";
    private static final String SQL_DELETE = "DELETE FROM perfil WHERE id_perfil = ?";

    /**
     * Devuelve una lista con todos los perfiles de la base de datos.
     * @return lista con todos los perfiles
     */
    @Override
    public List<Perfil> findAll() {
        List<Perfil> perfiles = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int id = rs.getInt("id_perfil");
                String nombre = rs.getString("nombre");
                // Versión Lazy: no buscamos el usuario, lo dejamos como null
                perfiles.add(new Perfil(id, nombre, null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return perfiles;
    }

    /**
     * Devuelve una lista con todos los perfiles de la base de datos.
     * @return lista con todos los perfiles con su usuario
     */
    public List<Perfil> findAllEager() {
        List<Perfil> perfiles = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int id = rs.getInt("id_perfil");
                String nombre = rs.getString("nombre");
                int idUsuario = rs.getInt("id_usuario");
                // Versión Eager: buscamos el usuario y lo añadimos al perfil
                Usuario usuario = new UsuarioDAO().findById(idUsuario);
                perfiles.add(new Perfil(id, nombre, usuario));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return perfiles;
    }

    /**
     * Devuelve un perfil buscándolo por su id.
     * @param id identificador único del perfil
     * @return objeto Perfil si lo encuentra, null si no existe
     */
    @Override
    public Perfil findById(int id) {
        Perfil perfil = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                // Versión Lazy: no buscamos el usuario, lo dejamos como null
                perfil = new Perfil(id, nombre, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return perfil;
    }

    /**
     * Devuelve un perfil buscándolo por su id.
     * @param id identificador único del perfil
     * @return objeto Perfil con su usuario si lo encuentra, null si no existe
     */
    public Perfil findByIdEager(int id) {
        Perfil perfil = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                int idUsuario = rs.getInt("id_usuario");
                // Versión Eager: buscamos el usuario y lo añadimos al perfil
                Usuario usuario = new UsuarioDAO().findById(idUsuario);
                perfil = new Perfil(id, nombre, usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return perfil;
    }

    /**
     * Inserta un nuevo perfil en la base de datos.
     * Comprueba que el perfil no sea null antes de insertarlo.
     * @param perfil objeto Perfil a insertar
     * @return true si se ha insertado correctamente, false si no
     */
    @Override
    public boolean insert(Perfil perfil) {
        boolean inserted = false;
        // Comprobamos que el perfil no sea null y que tenga usuario
        if (perfil != null && perfil.getUsuario() != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, perfil.getNombre());
                ps.setInt(2, perfil.getUsuario().getId());
                ps.executeUpdate();
                inserted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return inserted;
    }

    /**
     * Actualiza un perfil existente en la base de datos.
     * Comprueba que el perfil existe antes de actualizarlo.
     * @param perfil objeto Perfil con los nuevos datos
     * @return true si se ha actualizado correctamente, false si no
     */
    @Override
    public boolean update(Perfil perfil) {
        boolean updated = false;
        // Comprobamos que el perfil no sea null y que exista en la base de datos
        if (perfil != null && findById(perfil.getId()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, perfil.getNombre());
                ps.setInt(2, perfil.getId());
                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Elimina un perfil de la base de datos por su id.
     * Comprueba que el perfil existe antes de eliminarlo.
     * @param id identificador del perfil a eliminar
     * @return true si se ha eliminado correctamente, false si no
     */
    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        // Comprobamos que el perfil existe antes de intentar eliminarlo
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
     * Busca todos los perfiles de un usuario concreto.
     * Un usuario puede tener varios perfiles.
     * @param idUsuario identificador del usuario
     * @return lista de perfiles que pertenecen a ese usuario
     */
    @Override
    public List<Perfil> findByIdUsuario(int idUsuario) {
        List<Perfil> perfiles = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID_USUARIO)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_perfil");
                String nombre = rs.getString("nombre");
                // Versión Lazy: no buscamos el usuario, lo dejamos como null
                perfiles.add(new Perfil(id, nombre, null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return perfiles;
    }
}
