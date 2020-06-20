package com.example.agendacontactos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class InfoContacto extends AppCompatActivity {

    //private int id;
    private TextView Nombre_Cognom;
    private TextView Telefono;
    private TextView Correo;
    private TextView CP;
    private TextView Ciudad;
    private TextView Pais;
    private TextView Fecha_Nacimiento;
    private ArrayList<String> informacionContacto;
    private FloatingActionButton llamar;
    private TextView mostrarid;
    private String idContacto;
    private FloatingActionButton crearEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contacto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        crearEvento = findViewById(R.id.eventosCalendario);
        llamar = findViewById(R.id.llamarContacto);
        mostrarid = findViewById(R.id.mostrarId);
        Nombre_Cognom = findViewById(R.id.textNom);
        Telefono = findViewById(R.id.textTelefono);
        Correo = findViewById(R.id.textCorreo);
        CP = findViewById(R.id.textCP);
        Ciudad = findViewById(R.id.textCiudad);
        Pais = findViewById(R.id.textPais);
        Fecha_Nacimiento = findViewById(R.id.textFecha);

        Bundle bundle = getIntent().getExtras();

        Nombre_Cognom.setText(bundle.getString("Nom")+" "+bundle.getString("Cognom"));
        Telefono.setText(bundle.getString("Telefon"));
        Correo.setText(bundle.getString("Correu"));
        CP.setText(bundle.getString("CP"));
        Ciudad.setText(bundle.getString("Ciutat"));
        Pais.setText(bundle.getString("Pais"));
        Fecha_Nacimiento.setText(bundle.getString("DataNaixement"));
        idContacto=bundle.getString("Id");


        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+Telefono.getText().toString() ));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Eventos.class);
                startActivity(intent);

            }

        });
        



    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){

        switch(menuItem.getItemId()){

            case R.id.eliminar:

                Contacto c = new Contacto(getApplicationContext(),null,null,1);

               String mensaje = c.eliminarContacto(idContacto);

                Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();

                break;

            case R.id.editar:

                Bundle bundle = new Bundle();

                bundle.putSerializable("IdEditar",idContacto);

                Intent intent = new Intent(this, EditarContacto.class);

                intent.putExtras(bundle);

                startActivity(intent);

                break;

            case android.R.id.home:

                    finish();

                break;

        }

        return true;
    }

    public void enviarMensaje(View view){

       Bundle bundle = new Bundle();
        bundle.putString("remitente",Correo.getText().toString());

        Intent intent = new Intent(this, EnviarCorreo.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void llamarContacto(View view){

        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+Telefono));
        if(ActivityCompat.checkSelfPermission(InfoContacto.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)return;
        startActivity(intent);

    }

}
