package DAO;

import DAO.Interfaces.IComicDAO;
import DAO.Interfaces.IDAO;
import dataAccess.ConnectionBD;
import model.Comic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComicDAO implements IDAO<Comic>, IComicDAO {

    private static final String SQL_ALL = "SELECT * FROM comic";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM comic WHERE id_comic = ?";
    private static final String SQL_FIND_BY_TITULO = "SELECT * FROM comic WHERE titulo LIKE ?";
    private static final String SQL_FIND_BY_EDITORIAL = "SELECT * FROM comic WHERE editorial = ?";
    private static final String SQL_INSERT = "INSERT INTO comic (titulo, editorial, autor) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE comic SET titulo = ?, editorial = ?, autor = ? WHERE id_comic = ?";
    private static final String SQL_DELETE = "DELETE FROM comic WHERE id_comic = ?";

    /**
     * Devuelve una lista con todos los cómics de la base de datos.
     * @return lista con todos los cómics
     */
    @Override
    public List<Comic> findAll() {
        List<Comic> comics = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                // Recogemos cada campo de la fila actual
                int id = rs.getInt("id_comic");
                String titulo = rs.getString("titulo");
                String editorial = rs.getString("editorial");
                String autor = rs.getString("autor");
                // Creamos el objeto Comic y lo añadimos a la lista
                comics.add(new Comic(id, titulo, editorial, autor));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comics;
    }

    /**
     * Devuelve un cómic buscándolo por su id.
     * @param id identificador único del cómic
     * @return objeto Comic si lo encuentra, null si no existe
     */
    @Override
    public Comic findById(int id) {
        Comic comic = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            // Sustituimos el ? por el id que nos pasan
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String titulo = rs.getString("titulo");
                String editorial = rs.getString("editorial");
                String autor = rs.getString("autor");
                comic = new Comic(id, titulo, editorial, autor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comic;
    }

    /**
     * Inserta un nuevo cómic en la base de datos.
     * Comprueba primero que el cómic no sea null antes de insertarlo.
     * @param comic objeto Comic a insertar
     * @return true si se ha insertado correctamente, false si no
     */
    @Override
    public boolean insert(Comic comic) {
        boolean inserted = false;
        // Comprobamos que el cómic no sea null antes de intentar insertarlo
        if (comic != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                // Sustituimos cada ? por el valor correspondiente del objeto comic
                ps.setString(1, comic.getTitulo());
                ps.setString(2, comic.getEditorial());
                ps.setString(3, comic.getAutor());
                ps.executeUpdate();
                inserted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return inserted;
    }

    /**
     * Actualiza un cómic existente en la base de datos.
     * Comprueba que el cómic existe antes de actualizarlo.
     * @param comic objeto Comic con los nuevos datos
     * @return true si se ha actualizado correctamente, false si no
     */
    @Override
    public boolean update(Comic comic) {
        boolean updated = false;
        // Comprobamos que el cómic no sea null y que exista en la base de datos
        if (comic != null && findById(comic.getId()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, comic.getTitulo());
                ps.setString(2, comic.getEditorial());
                ps.setString(3, comic.getAutor());
                ps.setInt(4, comic.getId());
                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Elimina un cómic de la base de datos por su id.
     * Comprueba que el cómic existe antes de eliminarlo.
     * @param id identificador del cómic a eliminar
     * @return true si se ha eliminado correctamente, false si no
     */
    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        // Comprobamos que el cómic existe antes de intentar eliminarlo
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
     * Busca cómics que contengan el texto indicado en el título.
     * Usa LIKE en SQL para buscar coincidencias parciales.
     * Por ejemplo, si buscas "Bat" devuelve "Batman: Year One", etc...
     * @param titulo texto a buscar dentro del título
     * @return lista de cómics que contienen ese texto en el título
     */
    @Override
    public List<Comic> findByTitulo(String titulo) {
        List<Comic> comics = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_TITULO)) {
            // Los % alrededor del texto permiten buscar coincidencias parciales
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_comic");
                String tituloLeido = rs.getString("titulo");
                String editorial = rs.getString("editorial");
                String autor = rs.getString("autor");
                comics.add(new Comic(id, tituloLeido, editorial, autor));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comics;
    }

    /**
     * Busca todos los cómics de una editorial concreta.
     * @param editorial nombre de la editorial a buscar
     * @return lista de cómics de esa editorial
     */
    @Override
    public List<Comic> findByEditorial(String editorial) {
        List<Comic> comics = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_EDITORIAL)) {
            ps.setString(1, editorial);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_comic");
                String titulo = rs.getString("titulo");
                String editorialLeida = rs.getString("editorial");
                String autor = rs.getString("autor");
                comics.add(new Comic(id, titulo, editorialLeida, autor));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comics;
    }
}
