package com.example.agendacontactos;

public class Events {

    private String fecha;
    private String hora;
    private String texto;
    private String contacto_quedado;

    public Events(String fecha, String hora, String texto, String contacto_quedado) {
        this.fecha = fecha;
        this.hora = hora;
        this.texto = texto;
        this.contacto_quedado = contacto_quedado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getContacto_quedado() {
        return contacto_quedado;
    }

    public void setContacto_quedado(String contacto_quedado) {
        this.contacto_quedado = contacto_quedado;
    }

    @Override
    public String toString() {
        return  "fecha:" + fecha  +"\nhora:" + hora + "\nEvento:" + contacto_quedado +"\nHas quedado con:" + texto+"";
    }
}
