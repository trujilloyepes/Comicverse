package model;

import java.time.LocalDate;

public class Suscripcion extends Entidad {

    private TipoSuscripcion tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Usuario usuario;

    public Suscripcion(int id, TipoSuscripcion tipo, LocalDate fechaInicio, LocalDate fechaFin, Usuario usuario) {
        super(id);
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuario = usuario;
    }

    /**
     * Constructor vacío, lo necesita JAXB para crear objetos desde XML.
     * Si no lo pongo, Java lo elimina cuando defino el constructor con parámetros.
     */
    public Suscripcion() {}

    public TipoSuscripcion getTipo() {
        return tipo;
    }

    public void setTipo(TipoSuscripcion tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Devuelve una cadena con la información de la suscripción.
     * Sobreescribe el método abstracto de Entidad.
     * @return String con los datos de la suscripción
     */
    @Override
    public String toString() {
        return "Suscripcion{" +
                "id=" + getId() +
                ", tipo=" + tipo +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", usuario=" + usuario +
                '}';
    }
}
