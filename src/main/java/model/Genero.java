package model;

/**
 * Clase que representa un género de cómic en la app.
 * Por ejemplo: acción, aventura, superhéroes...
 * Hereda de Entidad, por lo que ya tiene el atributo id y sus getters/setters.
 */
public class Genero extends Entidad {

    /** nombre del género **/
    private String nombre;

    /**
     * Constructor con todos los parámetros.
     * Llama al constructor de la clase padre (Entidad) con el id.
     * @param id
     * @param nombre
     */
   public Genero(int id, String nombre){
       super(id); /** llamada al constructor de Entidad **/
       this.nombre = nombre;
   }

    /**
     * Constructor vacío necesario para algunas operaciones.
     */
    public Genero() {}

    /**
     * Getter que devuelve el nombre del género.
     * @return nombre del género
     */
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return "Genero{" +
            "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                '}';
    }

}
