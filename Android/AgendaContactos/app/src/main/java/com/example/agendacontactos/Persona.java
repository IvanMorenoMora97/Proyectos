package com.example.agendacontactos;

import java.io.Serializable;

public class Persona implements Serializable {

    private String id;
    private String nom;
    private String cognom;
    private String telefon;
    private String correo;
    private String cp;
    private String ciudad;
    private String pais;
    private String fecha;


    public Persona(){}

    public Persona(String id, String nom, String cognom, String telefon, String correo, String cp, String ciudad, String pais, String fecha) {
        this.id=id;
        this.nom = nom;
        this.cognom = cognom;
        this.telefon = telefon;
        this.correo = correo;
        this.cp = cp;
        this.ciudad = ciudad;
        this.pais = pais;
        this.fecha = fecha;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCognom() {
        return cognom;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCp() {
        return cp;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString(){return nom+" "+cognom;}
}
