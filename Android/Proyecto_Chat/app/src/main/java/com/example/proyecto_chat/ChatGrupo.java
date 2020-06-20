package com.example.proyecto_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.view.inputmethod.EditorInfo;

import java.util.ArrayList;

public class ChatGrupo extends AppCompatActivity {

    private Entity item;
    private TextView nParticipantes;
    private ImageView imgGrupo;
    private Toolbar toolbar;
    private EditText enviarMensaje;
    private ListView mostrarChat;
    private ArrayList<String> textos;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_grupo);

        textos = new ArrayList<String>();
        adapter   = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textos);

        item = (Entity) getIntent().getSerializableExtra("ObjetoGrupo");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(item.getTitulo());


        mostrarChat = findViewById(R.id.mostrarChat);
        enviarMensaje = findViewById(R.id.escribirTexto);

        enviarMensaje.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event)
            {
                boolean action = false;
                if (actionId == EditorInfo.IME_ACTION_SEND)
                {
                    // hide keyboard
                    Toast mensaje = Toast.makeText(getApplicationContext(), "Texto enviado", Toast.LENGTH_SHORT);
                    mensaje.show();
                    textos.add(enviarMensaje.getText().toString());
                    mostrarChat.setAdapter(adapter);
                    action = true;
                }
                return action;
            }
        });


        //getSupportActionBar().setIcon(item.getFoto());


    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}