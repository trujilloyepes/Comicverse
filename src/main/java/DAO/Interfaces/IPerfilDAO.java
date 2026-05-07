package DAO.Interfaces;

import model.Perfil;
import java.util.List;
public interface IPerfilDAO {
    List<Perfil> findByIdUsuario(int idUsuario);
}
