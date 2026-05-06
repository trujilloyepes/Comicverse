package model;

/**
 * Clase que representa la lectura de un cómic por parte de un perfil
 * Es la clase que gestiona la relación N:M entre Perfil y Cómic
 * añadiendo además la página actual de lectura
 * Hereda de Entidad, por lo que ya tiene el atributo id y sus getters y setters
 */
public class Lectura extends Entidad {
    /** Perfil que está leyendo el cómic **/
    private Perfil perfil;
    /**Cómic que está siendo leído **/
    private  Comic comic;
    /**Página por la que va el perfil en ese cómic **/
    private int paginaActual;

    /**
     * Constructor con todos los parámetros.
     * Llama al constructor de la clase padre (Entidad) con el id.
     * @param id
     * @param perfil
     * @param paginaActual
     * @param comic
     */
    public Lectura(int id, Perfil perfil, int paginaActual, Comic comic) {
        super(id);
        this.perfil = perfil;
        this.paginaActual = paginaActual;
        this.comic = comic;
    }

    /**
     * Constructor vacío necesario para algunas operaciones.
     */
    public Lectura() {}

    /**
     * Getter que devuelve el perfil de la lectura
     * @return
     */
    public Perfil getPerfil() {return perfil; }

    /**
     * Setter que establece el perfil de la lectura
     * @param perfil
     */
    public void setPerfil(Perfil perfil) {this.perfil = perfil; }

    /**
     * Getters que devuelve el cómic de la lectura
     * @return
     */
    public Comic getComic() {return comic;}

    /**
     * Setters que devuelve el cómic de la lectura
     * @param comic
     */
    public void setComic(Comic comic) {this.comic = comic; }

    /**
     * Getter que devuelve la página actual de lectura.
     * @return
     */
    public int getPaginaActual() { return paginaActual; }

    /**
     * Setters que establece la página actual de la lectura
     * @param PaginaActual
     */
    public void setPaginaActual(int PaginaActual) {this.paginaActual = paginaActual; }

    /**
     * Devuelve una cadena con la información de la lectura.
     * Sobreescribe el método abstracto de Entidad.
     * @return String con los datos de la lectura
     */
    @Override
    public String toString() {
        return "Lectura{" +
                "id=" + getId() +
                ", perfil=" + perfil +
                ", comic=" + comic +
                ", paginaActual=" + paginaActual +
                '}';
    }


}
