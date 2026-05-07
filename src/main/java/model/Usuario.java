package model;

public class Usuario extends Entidad {
    private String email;
    private String contrasena;
    /**
     * Indica si el usuario es administrador o no.
     * Si es true, puede gestionar todos los datos de la aplicación.
     */
    private boolean administrador;

    public Usuario(int id, String email, String contrasena, boolean administrador) {
        super(id);
        this.email = email;
        this.contrasena = contrasena;
        this.administrador = administrador;
    }

    /**
     * Constructor vacío, lo necesita JAXB para crear objetos desde XML.
     * Si no lo pongo, Java lo elimina cuando defino el constructor con parámetros.
     */
    public Usuario() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    /**
     * Devuelve una cadena con la información del usuario.
     * Sobreescribe el método abstracto de Entidad.
     * @return String con los datos del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", email='" + email + '\'' +
                ", administrador=" + administrador +
                '}';
    }
}
