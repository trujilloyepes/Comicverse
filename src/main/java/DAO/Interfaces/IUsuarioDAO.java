package DAO.Interfaces;

import model.Usuario;
public interface IUsuarioDAO {
    Usuario findByEmail(String email);
}
