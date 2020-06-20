package com.example.agendacontactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Contacto extends SQLiteOpenHelper {

    String sqlCreacion="CREATE TABLE contactos (id integer primary key, nom text not null, cognom text not null, telefon integer not null, email text not null, codipostal integer not null, ciutat text not null, pais text not null, fecha text not null);";
    //String sqlCreacion2="CREATE TABLE eventos (idContactoQuedada integer primary key, idContacto integer ,fecha text, hora text, nota text,FOREIGN KEY(idContactoQuedada) REFERENCES contactos(id))";
    String sqlCreacion2="CREATE TABLE eventos (id integer primary key, fecha text not null, hora text not null, quedadaContacto text not null, notaTexto text not null);";

    SQLiteDatabase db;


     public Contacto(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Agenda", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreacion);
        db.execSQL(sqlCreacion2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contactos");
        db.execSQL("DROP TABLE IF EXISTS eventos");

        db.execSQL(sqlCreacion);
        db.execSQL(sqlCreacion2);
    }

    public String guardar(String nom, String apellido, int telefono, String correo, int cp, String ciudad, String pais, String fecha){

        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put("nom",nom);
        contenedor.put("cognom",apellido);
        contenedor.put("telefon",telefono);
        contenedor.put("email",correo);
        contenedor.put("codipostal",cp);
        contenedor.put("ciutat",ciudad);
        contenedor.put("pais",pais);
        contenedor.put("fecha",fecha);
        try{
            database.insertOrThrow("contactos",null,contenedor);
            mensaje="Ingresado correctamente";
        }catch(SQLException e){
            mensaje="Fallo al ingresar";
        }


        return mensaje;
    }

    public String guardarEvento(String fecha, String hora, String contacto, String texto){

        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put("fecha",fecha);
        contenedor.put("hora",hora);
        contenedor.put("quedadaContacto",contacto);
        contenedor.put("notaTexto",texto);


        try{

            database.insertOrThrow("eventos",null,contenedor);
            mensaje="Evento ingresado";
        }catch(SQLException e){
            mensaje="Fallo al ingresar";
        }


        return mensaje;
    }

    public ArrayList devolverContactos() {



        ArrayList<Persona> lista = new ArrayList<Persona>();
        SQLiteDatabase basedatos = this.getWritableDatabase();
        String consulta = "SELECT * FROM contactos";
        Cursor contactos = basedatos.rawQuery(consulta,null);

        if(contactos.moveToFirst()){

            do{

                int id = contactos.getInt(0);
                String idContacto = Integer.toString(id);
                String nom = contactos.getString(1);
                String cognom = contactos.getString(2);
                String telefon = contactos.getString(3);
                String correu = contactos.getString(4);
                String cp = contactos.getString(5);
                String ciutat = contactos.getString(6);
                String pais = contactos.getString(7);
                String datanaixement = contactos.getString(8);

                Persona persona = new Persona(idContacto,nom,cognom,telefon,correu,cp,ciutat,pais,datanaixement);


                lista.add(persona);

            }while(contactos.moveToNext());

        }

        return lista;

    }

    public ArrayList devolverQuedada(){

        ArrayList<Events> lista = new ArrayList<Events>();

        SQLiteDatabase basedatos = this.getWritableDatabase();
        String consulta = "SELECT * FROM eventos";

        Cursor events = basedatos.rawQuery(consulta,null);

        if(events.moveToFirst()){

            do{

                String fecha = events.getString(1);
                String hora = events.getString(2);
                        String texto = events.getString(3);
                                String contacto = events.getString(4);

                Events e = new Events(fecha,hora,texto,contacto);


                lista.add(e);

            }while(events.moveToNext());

        }




         return lista;
    }

    public void editarContacto(String id, String nom, String cognom, int telefon, String correu, int cp, String ciutat, String pais, String datanaixement){

        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put("nom",nom);
        contenedor.put("cognom",cognom);
        contenedor.put("telefon",telefon);
        contenedor.put("email",correu);
        contenedor.put("codipostal",cp);
        contenedor.put("ciutat",ciutat);
        contenedor.put("pais",pais);
        contenedor.put("fecha",datanaixement);

        database.update("contactos", contenedor, "id="+id+";", null);



    }

    public String eliminarContacto(String idContacto){

         String mensaje ="";
        SQLiteDatabase db = this.getWritableDatabase();



            int cantidad = db.delete("contactos","id='"+idContacto+"'",null);
           if(cantidad!=0){
               mensaje="Eliminado correctamente";
           }
           else {
               mensaje="Contacto no encontrado";
           }


        return mensaje;

    }
}
