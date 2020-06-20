package com.example.reproductormp3ivanmoreno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int permiso = 1;
    private FloatingActionButton floatingButton1;
    private FloatingActionButton floatingButton2;
    private FloatingActionButton floatingButton3;
    private boolean isFABOpen;
    ArrayList<Integer>  infoCanciones;
    ArrayList<String>  nombreCanciones;
    ListView lv;
    ArrayAdapter<Integer> adapter;
    private String cancion;
    private String titulo;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingButton1 = findViewById(R.id.floatingActionButton1);
        floatingButton2 = findViewById(R.id.floatingActionButton2);
        floatingButton3 = findViewById(R.id.floatingActionButton3);

        floatingButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        //ABRIR LAYOUT LISTA DE CANCIONES
        floatingButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (MainActivity.this, ListaCanciones.class);

                startActivity(intent);

            }
        });

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, permiso);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, permiso);

            }

        } else {

            doStuff();

        }


    }

    private void showFABMenu(){
        isFABOpen=true;
        floatingButton1.setImageResource(R.drawable.menos);
        floatingButton2.animate().translationY(-getResources().getDimension(R.dimen.standard_60));
        //floatingButton3.animate().translationY(-getResources().getDimension(R.dimen.standard_120));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        floatingButton1.setImageResource(R.drawable.plus);
        floatingButton2.animate().translationY(0);
        //floatingButton3.animate().translationY(0);
    }


    public void doStuff() {

        lv = findViewById(R.id.listaCanciones);
        infoCanciones = new ArrayList<Integer>();
        nombreCanciones = new ArrayList<String>();
        getMusica();
        //adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, infoCanciones);
        adapter = new ArrayAdapter<Integer>(this, R.layout.layoutcolortextrow, infoCanciones);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ABRIR REPRODUCTOR DE MUSICA PARA REPRODUCIR LA CANCION

                Toast.makeText(MainActivity.this, nombreCanciones.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (MainActivity.this, ReproducirCancion.class);
                intent.putExtra("nombreCancion", adapter.getItem(position).toString());
                intent.putExtra("posicionCancion", position);
                intent.putExtra("titulo", nombreCanciones.get(position));
                intent.putExtra("arrayTitulos", nombreCanciones);
                intent.putExtra("arrayCancionesID", infoCanciones);

                //TEST
                //Toast.makeText(MainActivity.this, "POSICION: "+position, Toast.LENGTH_SHORT).show();

                startActivity(intent);

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                // TODO Auto-generated method stub

                Toast.makeText(MainActivity.this, "PULSACION LARGA", Toast.LENGTH_SHORT).show();

                titulo = nombreCanciones.get(pos);
                cancion = nombreCanciones.get(pos);
                position = pos;

                abrirMenuListas();

                return true;
            }
        });

    }

    public String getTitleSong() {

        return titulo;
    }

    //RECOGER NOMBRE DE LA CANCION
    public String getCancion() {

        return cancion;
    }

    //RECOGER ID DE LA CANCION
    public int getIdCancion(){

        return infoCanciones.get(position);
    }


    public void abrirMenuListas(){

        ListaCancionesEmergente listaEmergente = new ListaCancionesEmergente();

        listaEmergente.show(getSupportFragmentManager(), "ejemplo menu");

    }


    public void getMusica() {

       int cancionesID[] = {R.raw.cancion1,R.raw.cancion2,R.raw.cancion3,R.raw.cancion4,R.raw.cancion5,R.raw.cancion6,R.raw.cancion7,R.raw.cancion8};

        String tituloCancion;

        //AÑADIR A UN ARRAY EL TITULO Y ID DE LA CANCION
        for (int i=0; i<cancionesID.length; i++){

            tituloCancion = getResources().getResourceEntryName(cancionesID[i]);

            nombreCanciones.add(tituloCancion);
            infoCanciones.add(cancionesID[i]);

        }








        /*ContentResolver content = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = content.query(uri, null, null, null, null);

        if(cursor!=null && cursor.moveToFirst()){

            int cancionTitulo = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int cancionArtista = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {

                String currentTitle = cursor.getString(cancionTitulo);
                String currentArtista = cursor.getString(cancionArtista);

                infoCanciones.add(currentTitle + "\n" + currentArtista);

            } while(cursor.moveToNext());

        }*/

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch(requestCode) {

            case permiso: {

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();

                        doStuff();

                    }

                } else {

                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                    finish();

                }
                return;
            }

        }
    }
}
