package com.example.reproductormp3ivanmoreno;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class ReproducirCancion extends AppCompatActivity {

    private FloatingActionButton floatingButton1;
    private FloatingActionButton floatingButton2;
    private FloatingActionButton floatingButton3;
    private boolean isFABOpen;
    private boolean activarBucle=false;
    private boolean activarRandom=false;
    private ImageView playpause;
    private ImageView anterior;
    private ImageView siguiente;
    private ImageView bucle;
    private ImageView aleatorio;
    private ImageView imagenCancion;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable runnable;
    private TextView tituloCancion;
    private Uri uri;
    private int posicionCancion;
    private ArrayList<Integer>  infoCanciones;
    private ArrayList<String> nombreCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproducir_cancion);

        imagenCancion = findViewById(R.id.imagenCancion);
        imagenCancion.setImageResource(R.drawable.reproduciendo);

        floatingButton1 = findViewById(R.id.floating1);
        floatingButton2 = findViewById(R.id.floating2);
        floatingButton3 = findViewById(R.id.floating3);

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

        handler = new Handler();
        seekBar = findViewById(R.id.seekBar);

        nombreCanciones = getIntent().getStringArrayListExtra("arrayTitulos");
        //final int posicionCancion = getIntent().getExtras().getInt("posicionCancion");
        posicionCancion = getIntent().getExtras().getInt("posicionCancion");


        //ID DE LA CANCION PARA PODER REPRODUCIRLA
        final int idCancion = Integer.parseInt(getIntent().getExtras().getString("nombreCancion"));

        String titulo = getIntent().getExtras().getString("titulo");
        tituloCancion = findViewById(R.id.cancionNombre);
        tituloCancion.setText(titulo);
        tituloCancion.setGravity(Gravity.CENTER);

        infoCanciones = getIntent().getExtras().getIntegerArrayList("arrayCancionesID");;

        //INTRODUCIR CANCION A REPRODUCIR
        final Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        //POSICION DE LA CANCION
        //Toast.makeText(this, "posicion: " + posicionCancion, Toast.LENGTH_SHORT).show();

        mediaPlayer = MediaPlayer.create(getApplicationContext(), idCancion);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        playpause = findViewById(R.id.playpause);
        anterior = findViewById(R.id.anterior);
        siguiente = findViewById(R.id.siguiente);
        bucle = findViewById(R.id.bucle);
        aleatorio = findViewById(R.id.aleatorio);

        //PLAYPAUSE CANVIARA LA IMAGEN SEGUN SU ESTADO, SI ESTA PAUSADO SE CAMBIA A LA OTRA IMAGEN
        playpause.setImageResource(R.drawable.pause);
        playpause.setTag("play");

        anterior.setImageResource(R.drawable.anterior);
        anterior.setTag("anterior");
        //REVERTIR ESTA IMAGEN
        siguiente.setImageResource(R.drawable.anterior);
        siguiente.setTag("siguiente");

        bucle.setImageResource(R.drawable.bucle);
        bucle.setTag("bucle");

        aleatorio.setImageResource(R.drawable.aleatorio);
        aleatorio.setTag("aleatorio");

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                playCicle();
                mediaPlayer.start();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                    //TEST
                    //Toast.makeText(ReproducirCancion.this, "progress" + progress, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ReproducirCancion.this, "progress media " + mediaPlayer.getDuration(), Toast.LENGTH_SHORT).show();
                }

                if(progress==mediaPlayer.getDuration()){
                    //TEST
                    //Toast.makeText(ReproducirCancion.this, "100%", Toast.LENGTH_SHORT).show();
                    siguienteCancionAut();
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        playpause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

               if(playpause.getTag().equals("play")){

                   playpause.setImageResource(R.drawable.play);
                   playpause.setTag("pause");

                   //REANUDAR CANCION
                   onPause();
               }
               else {

                   playpause.setImageResource(R.drawable.pause);
                   playpause.setTag("play");

                   //PAUSAR CANCION
                   onResume();
               }

                return false;
            }
        });

        siguiente.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(posicionCancion!=7) {
                    if (posicionCancion + 1 <= infoCanciones.size()) {

                        mediaPlayer.stop();

                        int nuevaPosicion = posicionCancion + 1;
                        int idCancion = infoCanciones.get(nuevaPosicion);

                        mediaPlayer = MediaPlayer.create(getApplicationContext(), idCancion);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.start();

                        tituloCancion.setText(nombreCanciones.get(nuevaPosicion));
                        posicionCancion = posicionCancion + 1;

                        //TEST
                        //Toast.makeText(ReproducirCancion.this, "POSICION ACTUAL: " + posicionCancion, Toast.LENGTH_SHORT).show();

                    }
                }
                else {

                    mediaPlayer.stop();

                    int nuevaPosicion = 0;
                    int idCancion = infoCanciones.get(nuevaPosicion);

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), idCancion);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();

                    tituloCancion.setText(nombreCanciones.get(nuevaPosicion));

                    posicionCancion = 0;

                    //TEST
                    //Toast.makeText(ReproducirCancion.this, "POSICION ACTUAL: " + posicionCancion, Toast.LENGTH_SHORT).show();

                }

                return false;
            }
        });

        anterior.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //SI LA POSICION ES 0 LE PONEMOS LA ULTIMA CANCION DE LA LISTA
                if(posicionCancion-1>0){

                    if(posicionCancion!=0) {

                        mediaPlayer.stop();

                        int nuevaPosicion = posicionCancion - 1;
                        int idCancion = infoCanciones.get(nuevaPosicion);

                        //TEST
                       //Toast.makeText(ReproducirCancion.this, "posicion actualizada: " + nuevaPosicion, Toast.LENGTH_SHORT).show();

                        mediaPlayer = MediaPlayer.create(getApplicationContext(), idCancion);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.start();

                        tituloCancion.setText(nombreCanciones.get(nuevaPosicion));

                        posicionCancion = posicionCancion - 1;

                    }
                }

                else if(posicionCancion-1==0 || posicionCancion==0){

                    if(posicionCancion==0){
                        //TEST
                        //Toast.makeText(ReproducirCancion.this, "hola caracola", Toast.LENGTH_SHORT).show();

                        mediaPlayer.stop();

                        int nuevaPosicion = infoCanciones.size()-1;
                        int idCancion = infoCanciones.get(nuevaPosicion);

                        mediaPlayer = MediaPlayer.create(getApplicationContext(), idCancion);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.start();

                        tituloCancion.setText(nombreCanciones.get(nuevaPosicion));

                        posicionCancion = nuevaPosicion;

                    }

                    else {
                        mediaPlayer.stop();

                        int nuevaPosicion = 0;
                        int idCancion = infoCanciones.get(nuevaPosicion);

                        mediaPlayer = MediaPlayer.create(getApplicationContext(), idCancion);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.start();

                        tituloCancion.setText(nombreCanciones.get(nuevaPosicion));

                        posicionCancion = 0;
                    }

                }


                return false;
            }
        });


        bucle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(activarBucle){
                    activarBucle=false;
                    Toast.makeText(ReproducirCancion.this, "Bucle detenido", Toast.LENGTH_SHORT).show();
                    //PARAR BUCLE
                    mediaPlayer.setLooping(false);
                }
                else {
                    activarBucle=true;
                    Toast.makeText(ReproducirCancion.this, "Reproducción en bucle", Toast.LENGTH_SHORT).show();
                    mediaPlayer.setLooping(true);
                }


                return false;
            }
        });

        aleatorio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(activarRandom){
                    activarRandom=false;
                    Toast.makeText(ReproducirCancion.this, "aleatorio detenido", Toast.LENGTH_SHORT).show();
                    //PARAR REPRODUCCION ALEATORIA

                }
                else {
                    activarRandom=true;
                    //TEST
                    Toast.makeText(ReproducirCancion.this, "aleatorio activado", Toast.LENGTH_SHORT).show();
                    //DESORDENA LA LISTA DE CANCIONES
                    Collections.shuffle(infoCanciones);

                }



                return false;
            }
        });


    }

    private void showFABMenu(){
        isFABOpen=true;
        floatingButton1.setImageResource(R.drawable.menos);
        floatingButton2.animate().translationY(-getResources().getDimension(R.dimen.standard_60));
        floatingButton3.animate().translationY(-getResources().getDimension(R.dimen.standard_120));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        floatingButton1.setImageResource(R.drawable.plus);
        floatingButton2.animate().translationY(0);
        floatingButton3.animate().translationY(0);
    }


    public void playCicle() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCicle();
                }
            };
            handler.postDelayed(runnable, 1000);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
    }

    public void siguienteCancionAut() {

        if(posicionCancion!=7) {
            if (posicionCancion + 1 <= infoCanciones.size()) {

                mediaPlayer.stop();

                int nuevaPosicion = posicionCancion + 1;
                int idCancion = infoCanciones.get(nuevaPosicion);

                mediaPlayer = MediaPlayer.create(getApplicationContext(), idCancion);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.start();

                posicionCancion = posicionCancion + 1;

                //TEST
                //Toast.makeText(ReproducirCancion.this, "POSICION ACTUAL: " + posicionCancion, Toast.LENGTH_SHORT).show();

            }
        }
        else {

            mediaPlayer.stop();

            int nuevaPosicion = 0;
            int idCancion = infoCanciones.get(nuevaPosicion);

            mediaPlayer = MediaPlayer.create(getApplicationContext(), idCancion);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.start();

            posicionCancion = 0;

            //TEST
            //Toast.makeText(ReproducirCancion.this, "POSICION ACTUAL: " + posicionCancion, Toast.LENGTH_SHORT).show();

        }



    }

}
