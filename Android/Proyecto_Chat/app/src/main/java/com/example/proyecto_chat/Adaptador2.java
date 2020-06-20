package com.example.proyecto_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador2 extends BaseAdapter {

    private ArrayList<Entity> listaEntidades;
    private Context context;

    public Adaptador2(Context context, ArrayList<Entity> listaEntidades) {
        this.listaEntidades = listaEntidades;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaEntidades.size();
    }

    @Override
    public Object getItem(int position) {
        return listaEntidades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Entity item = (Entity) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.itemgrupos, null);

        TextView usuario = convertView.findViewById(R.id.Usuario);
        TextView mensaje = convertView.findViewById(R.id.Mensaje);
        TextView hora = convertView.findViewById(R.id.Hora);


        //usuario.setText(item.getTitulo());
        //mensaje.setText(item.getContenido());
        //hora.setText(item.getContenido());
        usuario.setText("USUARIO");
        mensaje.setText("mensaje");
        hora.setText("12:30 pm");


        return convertView;
    }
}