package DAO.Interfaces;

import model.Lectura;
import java.util.List;
public interface ILecturaDAO {
    List<Lectura> findByPerfil(int idPerfil);
    List<Lectura> findByComic(int idComic);
}
