package com.example.agendacontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MostrarEventos extends AppCompatActivity {

    private ListView quedadas;
    private ArrayList<Events> eventos;
    private ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_eventos);

        quedadas = findViewById(R.id.quedadas);

        Contacto dbcontactos = new Contacto(getApplicationContext(),null,null,1);

        eventos = dbcontactos.devolverQuedada();

        System.out.println(eventos);

        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, eventos);

        quedadas.setAdapter(adaptador);

    }
}
