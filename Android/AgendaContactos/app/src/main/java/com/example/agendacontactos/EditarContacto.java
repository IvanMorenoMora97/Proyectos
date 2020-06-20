package com.example.agendacontactos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarContacto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contacto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText Nom = findViewById(R.id.editarNom);
        final EditText Cognom = findViewById(R.id.editarCognom);
        final EditText Telefon = findViewById(R.id.editarTelefon);
        final EditText Email = findViewById(R.id.editarEmail);
        final EditText CodiPostal = findViewById(R.id.editarCp);
        final EditText Ciutat = findViewById(R.id.editarCiutat);
        final EditText Pais = findViewById(R.id.editarPais);
        final EditText DataNaixement = findViewById(R.id.editarData);

        Bundle bundle = getIntent().getExtras();

        final String id = bundle.getString("IdEditar");


        FloatingActionButton insertarContacto = findViewById(R.id.añadirContactoEditado);

        insertarContacto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {

                if(Nom.getText().toString().trim().length()>0 && Cognom.getText().toString().trim().length()>0 &&
                        Telefon.getText().toString().trim().length()>0 && Email.getText().toString().trim().length()>0 && CodiPostal.getText().toString().trim().length()>0 && Ciutat.getText().toString().trim().length()>0
                        && Pais.getText().toString().trim().length()>0 && DataNaixement.getText().toString().trim().length()>0){


                    String nom = Nom.getText().toString();
                    String apellido = Cognom.getText().toString();
                    int telefono = Integer.parseInt(Telefon.getText().toString());
                    String correo = Email.getText().toString();
                    int cp = Integer.parseInt(CodiPostal.getText().toString());
                    String ciudad = Ciutat.getText().toString();
                    String pais = Pais.getText().toString();
                    String fecha=DataNaixement.getText().toString();



                    Contacto contactos = new Contacto(getApplicationContext(),null, null, 1);

                    contactos.editarContacto(id,nom, apellido, telefono, correo, cp, ciudad, pais, fecha);



                 }

                else {

                    Context context = getApplicationContext();
                    CharSequence text = "Falta algun camp per omplir";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

            }
        });



    }

    public void cargarImagen(View view){
        cargar();
    }

    private void cargar() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Selecciona una aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            // imagen.setImageURI(path);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){

        switch(menuItem.getItemId()){

            case android.R.id.home:

                finish();

                break;

        }

        return true;
    }

}
