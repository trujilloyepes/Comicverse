package model;

public class Lectura extends Entidad {
    private Perfil perfil;
    private  Comic comic;
    private int paginaActual;

    public Lectura(int id, Perfil perfil, int paginaActual, Comic comic) {
        super(id);
        this.perfil = perfil;
        this.paginaActual = paginaActual;
        this.comic = comic;
    }

    /**
     * Constructor vacío, lo necesita JAXB para crear objetos desde XML.
     * Si no lo pongo, Java lo elimina cuando defino el constructor con parámetros.
     */
    public Lectura() {}


    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int PaginaActual) {
        this.paginaActual = paginaActual;
    }

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
