package model;

/**
 * Clase que representa un cómic en la aplicación ComicVerse.
 * Hereda de Entidad, por lo que ya tiene el atributo id y sus getters/setters.
 */
public class Comic extends Entidad {

    /** Título del cómic */
    private String titulo;

    /** Editorial que publicó el cómic (Marvel, DC...) */
    private String editorial;

    /** Autor del cómic */
    private String autor;

    /**
     * Constructor con todos los parámetros.
     * Llama al constructor de la clase padre (Entidad) con el id.
     * @param id identificador único del cómic
     * @param titulo título del cómic
     * @param editorial editorial del cómic
     * @param autor autor del cómic
     */
    public Comic(int id, String titulo, String editorial, String autor) {
        super(id); // llamada al constructor de Entidad
        this.titulo = titulo;
        this.editorial = editorial;
        this.autor = autor;
    }

    /**
     * Constructor vacío necesario para algunas operaciones.
     */
    public Comic() {}

    /**
     * Getter que devuelve el título del cómic.
     * @return título del cómic
     */
    public String getTitulo() { return titulo; }

    /**
     * Setter que establece el título del cómic.
     * @param titulo nuevo título
     */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /**
     * Getter que devuelve la editorial del cómic.
     * @return editorial del cómic
     */
    public String getEditorial() { return editorial; }

    /**
     * Setter que establece la editorial del cómic.
     * @param editorial nueva editorial
     */
    public void setEditorial(String editorial) { this.editorial = editorial; }

    /**
     * Getter que devuelve el autor del cómic.
     * @return autor del cómic
     */
    public String getAutor() { return autor; }

    /**
     * Setter que establece el autor del cómic.
     * @param autor nuevo autor
     */
    public void setAutor(String autor) { this.autor = autor; }

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
