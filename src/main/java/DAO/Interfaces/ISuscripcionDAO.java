package DAO.Interfaces;

import model.Suscripcion;
public interface ISuscripcionDAO {
    Suscripcion findByUsuario(int idUsuario);
}
