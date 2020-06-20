package com.example.agendacontactos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Persona> lista;
    private ArrayAdapter adaptador;
    private EditText buscarContacto;
    private Window window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button color1 = findViewById(R.id.color1);
        Button color2 = findViewById(R.id.color2);
        Button color3 = findViewById(R.id.color3);

        this.window = getWindow();

        buscarContacto = findViewById(R.id.buscadorContacto);
        buscarContacto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            adaptador.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //VEMOS LOS CONTACTOS
        lv = findViewById(R.id.listView);
        Contacto dbcontactos = new Contacto(getApplicationContext(),null,null,1);
        lista = dbcontactos.devolverContactos();


        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);


        lv.setAdapter(adaptador);


        //ABRIMOS INFORMACIÓN DEL CONTACTO
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Persona p = null;

                for(int i = 0; i<lista.size(); i++){

                    if(i==position){

                        p = lista.get(i);

                    }

                }

                Bundle bundle = new Bundle();

                bundle.putSerializable("Id",p.getId());
                bundle.putSerializable("Nom",p.getNom());
                bundle.putSerializable("Cognom",p.getCognom());
                bundle.putSerializable("Telefon",p.getTelefon());
                bundle.putSerializable("Correu",p.getCorreo());
                bundle.putSerializable("CP",p.getCp());
                bundle.putSerializable("Ciutat",p.getCiudad());
                bundle.putSerializable("Pais",p.getPais());
                bundle.putSerializable("DataNaixement",p.getFecha());

                Intent intent = new Intent(getApplicationContext(),InfoContacto.class);

                intent.putExtras(bundle);

               startActivity(intent);
            }
        });


       color1.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
           @Override
           public void onClick(View v) {

               String primaryDark="#ff00ff";
               String primary="#0009ff";
               String background="#e5be01";

               cambiarColor(primaryDark,primary,background);

           }
       });

        color2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                String primaryDark="#00701a";
                String primary="#43a047";
                String background="#494949";

                cambiarColor(primaryDark,primary,background);

            }
        });

        color3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                String primaryDark="#c7b446";
                String primary="#191919";
                String background="#b5b8b1";

                cambiarColor(primaryDark,primary,background);

            }
        });

    }

    public void añadirContacto(View view){
        Intent intent = new Intent(this, FormContactos.class);
        startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void cambiarColor(String primaryDark, String primary, String background){

        window.setStatusBarColor(Color.parseColor(primaryDark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
        window.setNavigationBarColor(Color.parseColor(primary));


    }

}
