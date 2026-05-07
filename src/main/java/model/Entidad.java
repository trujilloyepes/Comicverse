package model;

/**
 * Clase abstracta que sirve como base para todas las entidades del modelo.
 * Al ser abstracta, no se puede instanciar directamente, solo se puede heredar.
 * Contiene el atributo id que es común a todas las entidades.
 */
public abstract class Entidad {
    private int id;

    public Entidad(int id) {
        this.id = id;
    }

    /**
     * Constructor vacío, lo necesita JAXB para crear objetos desde XML.
     * Si no lo pongo, Java lo elimina cuando defino el constructor con parámetros.
     */
    public Entidad() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método abstracto que obliga a todas las clases hijas a implementar
     * su propia versión de toString para mostrar sus datos.
     * @return String con la información de la entidad
     */
    @Override
    public abstract String toString();
}
