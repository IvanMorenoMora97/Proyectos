package com.example.agendacontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnviarCorreo extends AppCompatActivity {

    private EditText destinatario;
    private EditText asunto;
    private EditText mensaje;
    private Button enviarEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_correo);

        destinatario = findViewById(R.id.destinatario);
        asunto = findViewById(R.id.asuntoCorreo);
        mensaje = findViewById(R.id.textoCorreo);
        enviarEmail = findViewById(R.id.enviarCorreo);

        Bundle bundle = getIntent().getExtras();
        destinatario.setText(bundle.getString("remitente"));

        enviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(destinatario.getText().toString().trim().length()>0 && asunto.getText().toString().trim().length()>0){

                    String[] TO = {destinatario.getText().toString()};
                    String[] CC = {""};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto.getText().toString());
                    emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje.getText().toString());

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Email listo para enviar"));
                        finish();
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(EnviarCorreo.this,
                                "No hay clientes para seleccionar.", Toast.LENGTH_SHORT).show();
                    }



                }

                else {

                    String mensaje = "Faltan campos por rellenar";

                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, mensaje, duration);
                    toast.show();

                }


            }
        });


    }
}
