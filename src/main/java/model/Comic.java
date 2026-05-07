package model;

public class Comic extends Entidad {
    private String titulo;
    private String editorial;
    private String autor;

    public Comic(int id, String titulo, String editorial, String autor) {
        super(id); // llamada al constructor de Entidad
        this.titulo = titulo;
        this.editorial = editorial;
        this.autor = autor;
    }

    /**
     * Constructor vacío, lo necesita JAXB para crear objetos desde XML.
     * Si no lo pongo, Java lo elimina cuando defino el constructor con parámetros.
     */
    public Comic() {}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Devuelve una cadena con la información del cómic.
     * Sobreescribe el método abstracto de Entidad.
     * @return String con los datos del cómic
     */
    @Override
    public String toString() {
        return "Comic{" +
                "id=" + getId() +
                ", titulo='" + titulo + '\'' +
                ", editorial='" + editorial + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }
}
