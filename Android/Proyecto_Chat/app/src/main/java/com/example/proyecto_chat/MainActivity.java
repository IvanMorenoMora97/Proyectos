package com.example.proyecto_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //QUITAR ACTION BAR / TOOLBAR
        getSupportActionBar().hide();

        final TextView titulo = findViewById(R.id.tituloLayout);
        final TextView textoLink = findViewById(R.id.textoInfo1);
        final EditText Username = findViewById(R.id.usernameLogin);
        final EditText Password = findViewById(R.id.passwordLogin);
        Button btnLogin = findViewById(R.id.btnLogin);

        //CAMBIAR LA FUENTE DE LETRA
        Typeface face=Typeface.createFromAsset(getAssets(),"Imperfecta Regular.ttf");
        titulo.setTypeface(face);

        SpannableString link = new SpannableString(textoLink.getText());
        link.setSpan(new UnderlineSpan(), 0, link.length(), 0);
        textoLink.setText(link);

        //textoLink.setGravity();

        textoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistro = new Intent(getApplicationContext(), Login.class);
                startActivity(intentRegistro);
            }
        });

        //COMPROBACION TEMPORAL DEL BOTON
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //COMPROBAR QUE EL USUARIO ESTA EN LA BASE DE DATOS
                if (Username.getText().toString().trim().length() > 0 && Password.getText().toString().trim().length() > 0) {
                    Toast mensaje = Toast.makeText(getApplicationContext(), "Usuari correcte", Toast.LENGTH_SHORT);
                    mensaje.show();

                    Intent login = new Intent(getApplicationContext(), GruposChat.class);
                    startActivity(login);
                }

            }
        });


    }

}
