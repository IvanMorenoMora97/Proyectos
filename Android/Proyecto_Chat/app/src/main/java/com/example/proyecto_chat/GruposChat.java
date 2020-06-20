package com.example.proyecto_chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;


public class GruposChat extends AppCompatActivity {

    private EditText filtrarGrupo;
    private ListView lv;
    private Adaptador adaptador;
    private ArrayList<Entity> entidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos_chat);

        //QUITAR ACTION BAR / TOOLBAR
        getSupportActionBar().hide();


        filtrarGrupo = findViewById(R.id.filtrarGrupo);
        lv = findViewById(R.id.listaGrupos);
        entidades = getEntidades();
        adaptador = new Adaptador(this, entidades);

        lv.setAdapter(adaptador);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GruposChat.this, ChatGrupo.class);

                intent.putExtra("ObjetoGrupo", entidades.get(position));

                startActivity(intent);
            }
        });


    }

    private ArrayList<Entity> getEntidades(){

        ArrayList<Entity> items = new ArrayList<>();

        items.add(new Entity(R.drawable.facebook_1,"Grupo 1", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_2,"Grupo 2", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_3,"Grupo 3", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_5,"Grupo 4", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_5,"Grupo 5", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_1,"Grupo 6", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_2,"Grupo 7", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_3,"Grupo 8", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_5,"Grupo 9", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_5,"Grupo 10", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_1,"Grupo 11", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_2,"Grupo 12", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_3,"Grupo 13", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_5,"Grupo 14", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_5,"Grupo 15", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_1,"Grupo 16", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_2,"Grupo 17", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_3,"Grupo 18", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_5,"Grupo 19", "Numero departicipantes: 5"));
        items.add(new Entity(R.drawable.facebook_5,"Grupo 20", "Numero departicipantes: 5"));

        return items;
    }

}
