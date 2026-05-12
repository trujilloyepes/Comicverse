package model;

import java.time.LocalDate;

public class Compra extends Entidad {

    private Perfil perfil;
    private Comic comic;
    private LocalDate fechaCompra;
    private double precio;

    public Compra(int id, Perfil perfil, Comic comic, LocalDate fechaCompra, double precio) {
        super(id);
        this.perfil = perfil;
        this.comic = comic;
        this.fechaCompra = fechaCompra;
        this.precio = precio;
    }

    /**
     * Constructor vacío, lo necesita JAXB para crear objetos desde XML.
     * Si no lo pongo, Java lo elimina cuando defino el constructor con parámetros.
     */
    public Compra() {}

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    public Comic getComic() { return comic; }
    public void setComic(Comic comic) { this.comic = comic; }

    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    /**
     * Devuelve una cadena con la información de la compra.
     * Sobreescribe el método abstracto de Entidad.
     * @return String con los datos de la compra
     */
    @Override
    public String toString() {
        return "Compra{" +
                "id=" + getId() +
                ", perfil=" + perfil +
                ", comic=" + comic +
                ", fechaCompra=" + fechaCompra +
                ", precio=" + precio +
                '}';
    }
}
