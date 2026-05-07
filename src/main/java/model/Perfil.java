package model;

public class Perfil extends Entidad{

    private String nombre;
    private Usuario usuario;

    public Perfil(int id, String nombre, Usuario usuario) {
        super(id);
        this.nombre = nombre;
        this.usuario = usuario;
    }

    /**
     * Constructor vacío que lo necesita JAXB para crear objetos desde XML.
     * Si no lo pongo, Java lo elimina cuando defino el constructor con parámetros.
     */
    public Perfil() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre= nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Devuelve una cadena con la información del perfil.
     * Sobreescribe el método abstracto de Entidad.
     * @return String con los datos del perfil
     */
    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
