package DAO.Interfaces;

import model.Comic;
import java.util.List;
public interface IComicDAO {
    List<Comic> findByTitulo(String titulo);
    List<Comic> findByEditorial(String editorial);
}
