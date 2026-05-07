package model;

public class Genero extends Entidad {
    private String nombre;

   public Genero(int id, String nombre){
       super(id); /** llamada al constructor de Entidad **/
       this.nombre = nombre;
   }

    /**
     * Constructor vacío, lo necesita JAXB para crear objetos desde XML.
     * Si no lo pongo, Java lo elimina cuando defino el constructor con parámetros.
     */
    public Genero() {}

    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return "Genero{" +
            "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                '}';
    }

}
