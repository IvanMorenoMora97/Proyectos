package com.example.reproductormp3ivanmoreno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CancionesDeLaLista extends AppCompatActivity {

    private ListView lv;
    //private ArrayList<Integer> idCanciones;
    private ArrayAdapter<Integer> adapter;
    private ArrayList<String> nombreCanciones = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones_de_la_lista);

        final ListasBD listasbd = new ListasBD(getApplicationContext(), null, null, 1);

        //NOMBRE LISTA
        String nombreLista = getIntent().getExtras().getString("nombreLista");
        //Toast.makeText(this, "nombreLista: " + nombreLista, Toast.LENGTH_SHORT).show();


        //NOMBRE CANCION
        nombreCanciones.add("cancion1");
        nombreCanciones.add("cancion2");
        nombreCanciones.add("cancion3");
        nombreCanciones.add("cancion4");
        nombreCanciones.add("cancion5");
        nombreCanciones.add("cancion6");
        nombreCanciones.add("cancion7");
        nombreCanciones.add("cancion8");

        //ID LISTA
        int idLista = getIntent().getExtras().getInt("idLista");
        //Toast.makeText(this, "idLista: " + idLista, Toast.LENGTH_SHORT).show();


        //CANCIONES
        final ArrayList<Integer> idCanciones = listasbd.idCanciones(idLista);
        //Toast.makeText(this, "idCancion 1: " + idCanciones.get(1), Toast.LENGTH_SHORT).show();

        lv = findViewById(R.id.listaCanciones);

        adapter = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.layoutcolortextrow, idCanciones);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ABRIR REPRODUCTOR DE MUSICA PARA REPRODUCIR LA CANCION

                //Toast.makeText(this, nombreCanciones.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (getApplicationContext(), ReproducirCancion.class);
                intent.putExtra("nombreCancion", adapter.getItem(position).toString());
                intent.putExtra("posicionCancion", position);
                intent.putExtra("titulo", nombreCanciones.get(position));
                intent.putExtra("arrayTitulos", nombreCanciones);
                intent.putExtra("arrayCancionesID", idCanciones);

                //TEST
                //Toast.makeText(MainActivity.this, "POSICION: "+position, Toast.LENGTH_SHORT).show();

                startActivity(intent);

            }
        });

    }
}
