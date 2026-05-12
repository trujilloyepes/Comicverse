package DAO;

import DAO.Interfaces.IDAO;
import dataAccess.ConnectionBD;
import model.Comic;
import model.Compra;
import model.Perfil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa las operaciones de acceso a datos para la entidad Compra.
 * Implementa IDAO<Compra> para los métodos CRUD básicos.
 * Una compra representa la adquisición permanente de un cómic por parte de un perfil.
 */
public class CompraDAO implements IDAO<Compra> {

    private static final String SQL_ALL = "SELECT * FROM compra";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM compra WHERE id_compra = ?";
    private static final String SQL_FIND_BY_PERFIL = "SELECT * FROM compra WHERE id_perfil = ?";
    private static final String SQL_FIND_BY_COMIC = "SELECT * FROM compra WHERE id_comic = ?";
    private static final String SQL_INSERT = "INSERT INTO compra (id_perfil, id_comic, fecha_compra, precio) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE compra SET fecha_compra = ?, precio = ? WHERE id_compra = ?";
    private static final String SQL_DELETE = "DELETE FROM compra WHERE id_compra = ?";

    /**
     * Método que devuelve una lista con todas las compras de la base de datos.
     * Versión Eager, rellena el perfil y el cómic de cada compra.
     * @return lista con todas las compras
     */
    @Override
    public List<Compra> findAll() {
        List<Compra> compras = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_compra");
                int idPerfil = rs.getInt("id_perfil");
                int idComic = rs.getInt("id_comic");
                LocalDate fechaCompra = rs.getDate("fecha_compra").toLocalDate();
                double precio = rs.getDouble("precio");
                Perfil perfil = new PerfilDAO().findById(idPerfil);
                Comic comic = new ComicDAO().findById(idComic);
                compras.add(new Compra(id, perfil, comic, fechaCompra, precio));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return compras;
    }

    /**
     * Método que devuelve una compra buscándola por su id.
     * @param id identificador único de la compra
     * @return objeto Compra si lo encuentra, null si no existe
     */
    @Override
    public Compra findById(int id) {
        Compra compra = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idPerfil = rs.getInt("id_perfil");
                int idComic = rs.getInt("id_comic");
                LocalDate fechaCompra = rs.getDate("fecha_compra").toLocalDate();
                double precio = rs.getDouble("precio");
                Perfil perfil = new PerfilDAO().findById(idPerfil);
                Comic comic = new ComicDAO().findById(idComic);
                compra = new Compra(id, perfil, comic, fechaCompra, precio);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return compra;
    }

    /**
     * Método que devuelve todas las compras de un perfil concreto.
     * Es decir, todos los cómics que ese perfil ha comprado para siempre.
     * @param idPerfil identificador del perfil
     * @return lista de compras de ese perfil
     */
    public List<Compra> findByPerfil(int idPerfil) {
        List<Compra> compras = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_PERFIL)) {
            ps.setInt(1, idPerfil);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_compra");
                int idComic = rs.getInt("id_comic");
                LocalDate fechaCompra = rs.getDate("fecha_compra").toLocalDate();
                double precio = rs.getDouble("precio");
                Perfil perfil = new PerfilDAO().findById(idPerfil);
                Comic comic = new ComicDAO().findById(idComic);
                compras.add(new Compra(id, perfil, comic, fechaCompra, precio));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return compras;
    }

    /**
     * Método que devuelve todas las compras de un cómic concreto.
     * Es decir, todos los perfiles que han comprado ese cómic.
     * @param idComic identificador del cómic
     * @return lista de compras de ese cómic
     */
    public List<Compra> findByComic(int idComic) {
        List<Compra> compras = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_COMIC)) {
            ps.setInt(1, idComic);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_compra");
                int idPerfil = rs.getInt("id_perfil");
                LocalDate fechaCompra = rs.getDate("fecha_compra").toLocalDate();
                double precio = rs.getDouble("precio");
                Perfil perfil = new PerfilDAO().findById(idPerfil);
                Comic comic = new ComicDAO().findById(idComic);
                compras.add(new Compra(id, perfil, comic, fechaCompra, precio));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return compras;
    }

    /**
     * Método que inserta una nueva compra en la base de datos.
     * Comprueba que la compra no sea null antes de insertarla.
     * @param compra objeto Compra a insertar
     * @return true si se ha insertado correctamente, false si no
     */
    @Override
    public boolean insert(Compra compra) {
        boolean inserted = false;
        if (compra != null && compra.getPerfil() != null && compra.getComic() != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setInt(1, compra.getPerfil().getId());
                ps.setInt(2, compra.getComic().getId());
                ps.setDate(3, Date.valueOf(compra.getFechaCompra()));
                ps.setDouble(4, compra.getPrecio());
                ps.executeUpdate();
                inserted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return inserted;
    }

    /**
     * Método que actualiza una compra existente en la base de datos.
     * Comprueba que la compra existe antes de actualizarla.
     * @param compra objeto Compra con los nuevos datos
     * @return true si se ha actualizado correctamente, false si no
     */
    @Override
    public boolean update(Compra compra) {
        boolean updated = false;
        if (compra != null && findById(compra.getId()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setDate(1, Date.valueOf(compra.getFechaCompra()));
                ps.setDouble(2, compra.getPrecio());
                ps.setInt(3, compra.getId());
                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Método que elimina una compra de la base de datos por su id.
     * Comprueba que la compra existe antes de eliminarla.
     * @param id identificador de la compra a eliminar
     * @return true si se ha eliminado correctamente, false si no
     */
    @Override
    public boolean delete(int id) {
        boolean deleted = false;
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
}
