package DAO;

import DAO.Interfaces.IDAO;
import DAO.Interfaces.ILecturaDAO;
import dataAccess.ConnectionBD;
import model.Comic;
import model.Lectura;
import model.Perfil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturaDAO implements IDAO<Lectura>, ILecturaDAO {

    // Consultas SQL definidas como constantes para evitar repetir código
    private static final String SQL_ALL = "SELECT * FROM lectura";
    private static final String SQL_FIND_BY_ID_PERFIL = "SELECT * FROM lectura WHERE id_perfil = ?";
    private static final String SQL_FIND_BY_ID_COMIC = "SELECT * FROM lectura WHERE id_comic = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM lectura WHERE id_perfil = ? AND id_comic = ?";
    private static final String SQL_INSERT = "INSERT INTO lectura (id_perfil, id_comic, pagina_actual) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE lectura SET pagina_actual = ? WHERE id_perfil = ? AND id_comic = ?";
    private static final String SQL_DELETE = "DELETE FROM lectura WHERE id_perfil = ? AND id_comic = ?";

    /**
     * Devuelve una lista con todas las lecturas de la base de datos.
     * Versión Eager, rellena el perfil y el cómic de cada lectura.
     * @return lista con todas las lecturas
     */
    @Override
    public List<Lectura> findAll() {
        List<Lectura> lecturas = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                // Recogemos los ids de perfil y comic de la fila actual
                int idPerfil = rs.getInt("id_perfil");
                int idComic = rs.getInt("id_comic");
                int paginaActual = rs.getInt("pagina_actual");
                // Buscamos el perfil y el comic por su id para rellenar el objeto Lectura (versión Eager)
                Perfil perfil = new PerfilDAO().findById(idPerfil);
                Comic comic = new ComicDAO().findById(idComic);
                lecturas.add(new Lectura(0, perfil, paginaActual, comic));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lecturas;
    }

    /**
     * Devuelve una lectura buscándola por su id.
     * En la tabla lectura la clave primaria es la combinación de id_perfil e id_comic,
     * por eso este método no es el más usado, se usan más findByPerfil y findByComic.
     * @param id no se usa en esta tabla, se usa findByPerfil y findByComic
     * @return null, usar findByPerfil o findByComic en su lugar
     */
    @Override
    public Lectura findById(int id) {
        // En Lectura la clave primaria es compuesta (id_perfil + id_comic)
        // por eso este método no aplica, usamos findByPerfil y findByComic
        return null;
    }

    /**
     * Inserta una nueva lectura en la base de datos.
     * Comprueba que la lectura no sea null antes de insertarla.
     * @param lectura objeto Lectura a insertar
     * @return true si se ha insertado correctamente, false si no
     */
    @Override
    public boolean insert(Lectura lectura) {
        boolean inserted = false;
        // Comprobamos que la lectura no sea null y que tenga perfil y comic
        if (lectura != null && lectura.getPerfil() != null && lectura.getComic() != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setInt(1, lectura.getPerfil().getId());
                ps.setInt(2, lectura.getComic().getId());
                ps.setInt(3, lectura.getPaginaActual());
                ps.executeUpdate();
                inserted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return inserted;
    }

    /**
     * Actualiza la página actual de una lectura existente en la base de datos.
     * Comprueba que la lectura no sea null antes de actualizarla.
     * @param lectura objeto Lectura con los nuevos datos
     * @return true si se ha actualizado correctamente, false si no
     */
    @Override
    public boolean update(Lectura lectura) {
        boolean updated = false;
        // Comprobamos que la lectura no sea null y que tenga perfil y comic
        if (lectura != null && lectura.getPerfil() != null && lectura.getComic() != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setInt(1, lectura.getPaginaActual());
                ps.setInt(2, lectura.getPerfil().getId());
                ps.setInt(3, lectura.getComic().getId());
                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Elimina una lectura de la base de datos.
     * En este caso usamos el id del perfil para eliminar todas sus lecturas.
     * @param idPerfil identificador del perfil cuyas lecturas queremos eliminar
     * @return true si se ha eliminado correctamente, false si no
     */
    @Override
    public boolean delete(int idPerfil) {
        boolean deleted = false;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
            ps.setInt(1, idPerfil);
            ps.executeUpdate();
            deleted = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return deleted;
    }

    /**
     * Busca todas las lecturas de un perfil concreto.
     * Es decir, todos los cómics que está leyendo un perfil.
     * Versión Eager, rellena el perfil y el cómic de cada lectura.
     * @param idPerfil identificador del perfil
     * @return lista de lecturas de ese perfil
     */
    @Override
    public List<Lectura> findByPerfil(int idPerfil) {
        List<Lectura> lecturas = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID_PERFIL)) {
            ps.setInt(1, idPerfil);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idComic = rs.getInt("id_comic");
                int paginaActual = rs.getInt("pagina_actual");
                // Buscamos el perfil y el comic por su id (versión Eager)
                Perfil perfil = new PerfilDAO().findById(idPerfil);
                Comic comic = new ComicDAO().findById(idComic);
                lecturas.add(new Lectura(0, perfil, paginaActual, comic));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lecturas;
    }

    /**
     * Busca todas las lecturas de un cómic concreto.
     * Es decir, todos los perfiles que están leyendo ese cómic.
     * Versión Eager, rellena el perfil y el cómic de cada lectura.
     * @param idComic identificador del cómic
     * @return lista de lecturas de ese cómic
     */
    @Override
    public List<Lectura> findByComic(int idComic) {
        List<Lectura> lecturas = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID_COMIC)) {
            ps.setInt(1, idComic);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idPerfil = rs.getInt("id_perfil");
                int paginaActual = rs.getInt("pagina_actual");
                // Buscamos el perfil y el comic por su id (versión Eager)
                Perfil perfil = new PerfilDAO().findById(idPerfil);
                Comic comic = new ComicDAO().findById(idComic);
                lecturas.add(new Lectura(0, perfil, paginaActual, comic));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lecturas;
    }
}