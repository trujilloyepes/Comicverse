package DAO;

import DAO.Interfaces.IDAO;
import DAO.Interfaces.IUsuarioDAO;
import dataAccess.ConnectionBD;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa las operaciones de acceso a datos para la entidad Usuario.
 * Implementa IDAO<Usuario> para los métodos CRUD básicos e IUsuarioDAO para
 * los métodos específicos de Usuario.
 */
public class UsuarioDAO implements IDAO<Usuario>, IUsuarioDAO {

    // Consultas SQL definidas como constantes para evitar repetir código
    private static final String SQL_ALL = "SELECT * FROM usuario";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM usuario WHERE id_usuario = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM usuario WHERE email = ?";
    private static final String SQL_INSERT = "INSERT INTO usuario (email, contrasena, administrador) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE usuario SET email = ?, contrasena = ?, administrador = ? WHERE id_usuario = ?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE id_usuario = ?";

    /**
     * Devuelve una lista con todos los usuarios de la base de datos.
     * @return lista con todos los usuarios
     */
    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String email = rs.getString("email");
                String contrasena = rs.getString("contrasena");
                // El campo administrador es TINYINT(1) en la BBDD, getBoolean lo convierte a boolean
                boolean administrador = rs.getBoolean("administrador");
                usuarios.add(new Usuario(id, email, contrasena, administrador));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    /**
     * Devuelve un usuario buscándolo por su id.
     * @param id identificador único del usuario
     * @return objeto Usuario si lo encuentra, null si no existe
     */
    @Override
    public Usuario findById(int id) {
        Usuario usuario = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String email = rs.getString("email");
                String contrasena = rs.getString("contrasena");
                boolean administrador = rs.getBoolean("administrador");
                usuario = new Usuario(id, email, contrasena, administrador);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     * Comprueba que el email no esté ya registrado antes de insertarlo.
     * @param usuario objeto Usuario a insertar
     * @return true si se ha insertado correctamente, false si no
     */
    @Override
    public boolean insert(Usuario usuario) {
        boolean inserted = false;
        // Comprobamos que el usuario no sea null y que el email no esté ya registrado
        if (usuario != null && findByEmail(usuario.getEmail()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, usuario.getEmail());
                ps.setString(2, usuario.getContrasena());
                // Convertimos boolean a int para la BBDD (true=1, false=0)
                ps.setInt(3, usuario.isAdministrador() ? 1 : 0);
                ps.executeUpdate();
                inserted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return inserted;
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     * Comprueba que el usuario existe antes de actualizarlo.
     * @param usuario objeto Usuario con los nuevos datos
     * @return true si se ha actualizado correctamente, false si no
     */
    @Override
    public boolean update(Usuario usuario) {
        boolean updated = false;
        // Comprobamos que el usuario no sea null y que exista en la base de datos
        if (usuario != null && findById(usuario.getId()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, usuario.getEmail());
                ps.setString(2, usuario.getContrasena());
                ps.setInt(3, usuario.isAdministrador() ? 1 : 0);
                ps.setInt(4, usuario.getId());
                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Elimina un usuario de la base de datos por su id.
     * Comprueba que el usuario existe antes de eliminarlo.
     * @param id identificador del usuario a eliminar
     * @return true si se ha eliminado correctamente, false si no
     */
    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        // Comprobamos que el usuario existe antes de intentar eliminarlo
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
     * Busca un usuario por su email.
     * Muy útil para el login de la aplicación, ya que el usuario
     * introduce su email y contraseña para acceder.
     * @param email email del usuario a buscar
     * @return objeto Usuario si lo encuentra, null si no existe
     */
    @Override
    public Usuario findByEmail(String email) {
        Usuario usuario = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_EMAIL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_usuario");
                String contrasena = rs.getString("contrasena");
                boolean administrador = rs.getBoolean("administrador");
                usuario = new Usuario(id, email, contrasena, administrador);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }
}
