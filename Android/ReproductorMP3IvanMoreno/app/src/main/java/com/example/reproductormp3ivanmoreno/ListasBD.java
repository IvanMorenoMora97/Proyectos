package com.example.reproductormp3ivanmoreno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListasBD extends SQLiteOpenHelper {


    private final static int VERSION_BD=1;
    //String TABLA_LISTAS="CREATE TABLE listas (idLista integer primary key, nomLista text, idCancion integer, tituloCancion text);";
    String sqlCreacion="CREATE TABLE listas (id integer primary key, nomLista text not null);";
    String sqlCreacion2="CREATE TABLE canciones (idCancion integer primary key, idLista integer not null,nomCancion text not null);";

    SQLiteDatabase db;


    public ListasBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "listasbd", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreacion);
        db.execSQL(sqlCreacion2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS listas");
        db.execSQL(sqlCreacion);
        db.execSQL("DROP TABLE IF EXISTS canciones");
        db.execSQL(sqlCreacion2);
    }

    //AGREGAR CANCIONES DENTRO DE LA LISTA
    public String agregarCancion(int idLista, int idCancion, String nombreCancion){

        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put("idCancion",idCancion);
        contenedor.put("idLista",idLista);
        contenedor.put("nomCancion",nombreCancion);

        try{

            database.insertOrThrow("canciones",null,contenedor);
            mensaje="Cancion ingresada";
        }catch(SQLException e){
            mensaje="Fallo al ingresar";
        }


        return mensaje;



    }

    //AGREGAR CANCIONES DENTRO DE LA LISTA
    public String crearLista(String nombre){

        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put("nomLista",nombre);

        try{

            database.insertOrThrow("listas",null,contenedor);
            mensaje="Lista ingresada";
        }catch(SQLException e){
            mensaje="Fallo al ingresar";
        }


        return mensaje;
    }

    //DEVOLVER NOMBRE LISTAS DE REPRODUCCION
    public ArrayList devolverNomListas() {

        ArrayList<String> lista = new ArrayList<String>();
        SQLiteDatabase basedatos = this.getWritableDatabase();
        String consulta = "SELECT * FROM listas";
        Cursor contactos = basedatos.rawQuery(consulta,null);

        if(contactos.moveToFirst()){

            do{

               String nom = contactos.getString(1);
               lista.add(nom);

            }while(contactos.moveToNext());

        }

        return lista;

    }

    //DEVOLVER ID DE LAS LISTAS DE REPRODUCCION
    public int devolverIdListas(String nombreLista) {

       int id=0;
        SQLiteDatabase basedatos = this.getWritableDatabase();
        String consulta = "SELECT id FROM listas WHERE nomLista='"+nombreLista+"'";
        Cursor contactos = basedatos.rawQuery(consulta,null);

        if(contactos.moveToFirst()){

            do{

                id = contactos.getInt(0);

            }while(contactos.moveToNext());

        }

        return id;

    }

    //DEVOLVER CANCIONES DE LA LISTA DE REPRODUCCION
    public ArrayList idCanciones(int idLista){

        ArrayList<Integer> idCanciones = new ArrayList<Integer>();
        SQLiteDatabase basedatos = this.getWritableDatabase();
        String consulta = "SELECT * FROM canciones WHERE idLista="+idLista+";";
        Cursor contactos = basedatos.rawQuery(consulta,null);


        if(contactos.moveToFirst()){

            do{

                int cancionID = contactos.getInt(0);
                idCanciones.add(cancionID);

            }while(contactos.moveToNext());

        }

        return idCanciones;
    }

}
