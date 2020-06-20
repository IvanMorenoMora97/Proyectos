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

public class ListaCanciones extends AppCompatActivity {

    private ListView lv;
    private ArrayList<String> prueba = new ArrayList<>();
    private ArrayAdapter<String> adaptadorPrueba;
   // private ListasBD bd = new ListasBD(getApplicationContext(),null,null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canciones);

        lv = findViewById(R.id.listaDeLasCanciones);

        final ListasBD listasbd = new ListasBD(getApplicationContext(), null, null, 1);

        prueba = listasbd.devolverNomListas();

        adaptadorPrueba = new ArrayAdapter<String>(getApplicationContext(), R.layout.layoutcolortextrow, prueba);

        lv.setAdapter(adaptadorPrueba);


        //ENVIAR A OTRO ACTIVITY CON LAS CANCIONES DE LA LISTA PERSONALIZADA

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ABRIR REPRODUCTOR DE MUSICA PARA REPRODUCIR LA CANCION

                //Toast.makeText(getApplicationContext(), adaptadorPrueba.getItem(position), Toast.LENGTH_SHORT).show();

                //pilla la id de la lista
                int idLista = listasbd.devolverIdListas(adaptadorPrueba.getItem(position).toString());

                Toast.makeText(ListaCanciones.this, "IDLISTA: " + idLista, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListaCanciones.this, CancionesDeLaLista.class);
                intent.putExtra("idLista",idLista);
                intent.putExtra("nombreLista",adaptadorPrueba.getItem(position));
                startActivity(intent);
                //TEST
                //Toast.makeText(MainActivity.this, "POSICION: "+position, Toast.LENGTH_SHORT).show();



            }
        });



    }
}
