package model;

/**
 * Clase abstracta que sirve como base para todas las entidades del modelo.
 * Al ser abstracta, no se puede instanciar directamente, solo se puede heredar.
 * Contiene el atributo id que es común a todas las entidades.
 */
public abstract class Entidad {

    /** Identificador único de cada entidad */
    private int id;

    /**
     * Constructor con parámetros.
     * @param id identificador único de la entidad
     */
    public Entidad(int id) {
        this.id = id;
    }

    /**
     * Constructor vacío necesario para algunas operaciones.
     */
    public Entidad() {}

    /**
     * Getter que devuelve el id de la entidad.
     * @return id de la entidad
     */
    public int getId() { return id; }

    /**
     * Setter que establece el id de la entidad.
     * @param id nuevo id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Método abstracto que obliga a todas las clases hijas a implementar
     * su propia versión de toString para mostrar sus datos.
     * @return String con la información de la entidad
     */
    @Override
    public abstract String toString();
}
