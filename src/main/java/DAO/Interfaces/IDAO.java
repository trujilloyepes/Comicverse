package DAO.Interfaces;

import java.util.List;

/**
 * Interfaz genérica que define los métodos CRUD básicos que todos los DAOs deben implementar.
 * Todos los DAOs del proyecto implementarán esta interfaz.
 */
public interface IDAO<T> {
    List<T> findAll();
    T findById(int id);
    boolean insert(T t);
    boolean update(T t);
    boolean delete(int id);
}
