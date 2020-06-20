package com.example.reproductormp3ivanmoreno;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class ListaCancionesEmergente extends AppCompatDialogFragment {

    private ListView lv;
    private ArrayList<String> prueba = new ArrayList<>();
    private ArrayAdapter<String> adaptadorPrueba;
    private String nombreLista;
    private ListasBD listasbd;
    private String titulo;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //NOMBRE DE LAS LISTAS POR DEFECTO QUE SE MOSTRARAN
        prueba.add("Lista 1");

        listasbd = new ListasBD(getContext(), null, null, 1);

        prueba = listasbd.devolverNomListas();

        //AÑADIR LISTAS DE LA BASE DE DATOS AL ARRAY

        adaptadorPrueba = new ArrayAdapter<String>(getContext(), R.layout.layoutcolortextrow, prueba);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.listasventanaemergente, null);

        builder.setView(view).setTitle("Lista de canciones").setNegativeButton("Crear lista", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                abrirMenuListas();

            }
        }).setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        lv = view.findViewById(R.id.listaCancionesEmergente);
        lv.setAdapter(adaptadorPrueba);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //AÑADIR CANCION A LA LISTA SELECCIONADA


                añadirCancion(adaptadorPrueba.getItem(position).toString());

                //TEST
                //Toast.makeText(getContext(), "touched", Toast.LENGTH_SHORT).show();


            }
        });


        return builder.create();
    }

    //AÑADIMOS LA CANCION A LA LISTA SELECCIONADA
    private void añadirCancion(String nombreLista) {

        MainActivity mActivity = (MainActivity) getActivity();

        //RECIBIMOS EL NOMBRE DE LA CANCION
        String cancion = mActivity.getCancion();
        titulo = mActivity.getTitleSong();
        int idLista = listasbd.devolverIdListas(nombreLista);
        int idCancion = mActivity.getIdCancion();

        //Toast.makeText(getContext(), cancion, Toast.LENGTH_SHORT).show();

        //String sqlCreacion="CREATE TABLE listas (id integer primary key, nomLista text not null);";
        //String sqlCreacion2="CREATE TABLE canciones (idCancion integer primary key, idLista integer not null,nomCancion text not null);";

        String mensaje = listasbd.agregarCancion(idLista, idCancion, titulo);


        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();



    }

    public void abrirMenuListas(){

        PantallaAañadirNombreLista listaEmergente = new PantallaAañadirNombreLista();

        listaEmergente.show(getFragmentManager(),null);

    }
}
