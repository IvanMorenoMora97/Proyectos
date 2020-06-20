package com.example.proyecto_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //QUITAR ACTION BAR / TOOLBAR
        getSupportActionBar().hide();

        final EditText Nom = findViewById(R.id.Nom);
        final EditText Cognom = findViewById(R.id.Cognom);
        final EditText Telefon = findViewById(R.id.Telefon);
        final EditText Usuari = findViewById(R.id.Usuari);
        final EditText Contrassenya = findViewById(R.id.Contrassenya);
        final Button btnRegistre = findViewById(R.id.btnRegistro);



        btnRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Nom.getText().toString().trim().length()>0 && Cognom.getText().toString().trim().length()>0
                        && Telefon.getText().toString().trim().length()>0 && Usuari.getText().toString().trim().length()>0
                        && Contrassenya.getText().toString().trim().length()>0){
                    Toast mensaje = Toast.makeText(getApplicationContext(),"Usuari creat", Toast.LENGTH_SHORT);
                    mensaje.show();

                    //SE AÑADEN LOS DATOS A LA BASE DE DATOS Y SE ABRE EL LAYOUT DE LOS GRUPOS
                    Intent login = new Intent(getApplicationContext(),GruposChat.class);
                    startActivity(login);

                }
                else {
                    Toast mensaje = Toast.makeText(getApplicationContext(),"Falten camps per omplir", Toast.LENGTH_SHORT);
                    mensaje.show();
                }


            }
        });

    }
}
