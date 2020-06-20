package com.example.reproductormp3ivanmoreno;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PantallaAañadirNombreLista extends AppCompatDialogFragment {

    private EditText nombreLista;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.anyadirlistanombre, null);

        nombreLista = view.findViewById(R.id.nombreListaAñadir);

        builder.setView(view).setTitle("Nombre de la lista").setNegativeButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //RECOGER EDIT TEXT Y CREA RLALISTA EN LA BD

                //Toast.makeText(getContext(), nombreLista.getText().toString(), Toast.LENGTH_SHORT).show();
                ListasBD bd = new ListasBD(getContext(),null,null,1);
                String nombre = nombreLista.getText().toString();
                String mensaje = bd.crearLista(nombre);
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();



                //TEST
                //Toast.makeText(getContext(), "LISTA CREADA", Toast.LENGTH_SHORT).show();

            }
        }).setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

}

