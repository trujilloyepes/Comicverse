package DAO.Interfaces;

import model.Genero;
public interface IGeneroDAO {
    Genero findByNombre(String nombre);
}
