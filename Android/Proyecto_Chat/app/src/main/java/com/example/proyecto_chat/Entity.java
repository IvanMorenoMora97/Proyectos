package com.example.proyecto_chat;

import java.io.Serializable;

public class Entity implements Serializable {

    private int imgFoto;
    private String titulo;
    private String contenido;

    public Entity(int foto, String t, String c) {

        this.imgFoto = foto;
        this.titulo = t;
        this.contenido = c;

    }

    public int getFoto() { return imgFoto; }

    public String getTitulo() { return titulo; }

    public String getContenido() { return contenido; }
}
