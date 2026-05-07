package DAO;

import DAO.Interfaces.IDAO;
import DAO.Interfaces.IGeneroDAO;
import dataAccess.ConnectionBD;
import model.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GeneroDAO implements IDAO<Genero>, IGeneroDAO {

    private static final String SQL_ALL = "SELECT * FROM genero";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM genero WHERE id_genero = ?";
    private static final String SQL_FIND_BY_NOMBRE = "SELECT * FROM genero WHERE nombre = ?";
    private static final String SQL_INSERT = "INSERT INTO genero (nombre) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE genero SET nombre = ? WHERE id_genero = ?";
    private static final String SQL_DELETE = "DELETE FROM genero WHERE id_genero = ?";

    /**
     * Devuelve una lista con todos los géneros de la base de datos.
     * @return lista con todos los géneros
     */
    @Override
    public List<Genero> findAll() {
        List<Genero> generos = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int id = rs.getInt("id_genero");
                String nombre = rs.getString("nombre");
                generos.add(new Genero(id, nombre));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return generos;
    }

    /**
     * Devuelve un género buscándolo por su id.
     * @param id identificador único del género
     * @return objeto Género si lo encuentra y si no lo encuentra nos dará null porque no existe
     */
    @Override
    public Genero findById(int id) {
        Genero genero = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                genero = new Genero(id, nombre);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genero;
    }

    /**
     * Inserta un nuevo género en la base de datos.
     * Comprueba que el género no exista ya antes de insertarlo.
     * @param genero objeto Genero a insertar
     * @return true si se ha insertado correctamente, false si no hemos conseguido insertarlo de una forma correcta
     */
    @Override
    public boolean insert(Genero genero) {
        boolean inserted = false;
        // Comprobamos que el género no sea null y que no exista ya en la base de datos
        if (genero != null && findByNombre(genero.getNombre()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, genero.getNombre());
                ps.executeUpdate();
                inserted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return inserted;
    }

    /**
     * Actualiza un género existente en la base de datos.
     * Comprueba que el género existe antes de actualizarlo.
     * @param genero objeto Genero con los nuevos datos
     * @return true si se ha actualizado correctamente, false si no
     */
    @Override
    public boolean update(Genero genero) {
        boolean updated = false;
        // Comprobamos que el género no sea null y que exista en la base de datos
        if (genero != null && findById(genero.getId()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, genero.getNombre());
                ps.setInt(2, genero.getId());
                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Elimina un género de la base de datos por su id.
     * Comprueba que el género existe antes de eliminarlo.
     * @param id identificador del género a eliminar
     * @return true si se ha eliminado correctamente, false si no
     */
    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        // Comprobamos que el género existe antes de intentar eliminarlo
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
     * Busca un género por su nombre.
     * Útil para comprobar si un género ya existe antes de insertarlo.
     * @param nombre nombre del género a buscar
     * @return objeto Genero si lo encuentra, null si no existe
     */
    @Override
    public Genero findByNombre(String nombre) {
        Genero genero = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_NOMBRE)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_genero");
                genero = new Genero(id, nombre);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genero;
    }
}
