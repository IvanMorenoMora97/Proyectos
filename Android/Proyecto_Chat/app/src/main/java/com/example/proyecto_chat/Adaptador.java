package com.example.proyecto_chat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter implements Filterable {

   private ArrayList<Entity> listaEntidades;
   private Context context;

    public Adaptador(Context context, ArrayList<Entity> listaEntidades) {
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
        ImageView imgFoto = convertView.findViewById(R.id.imgFotos);
        TextView texto1 = convertView.findViewById(R.id.idTitulo);
        TextView texto2 = convertView.findViewById(R.id.idSubTitulo);


        imgFoto.setImageResource(item.getFoto());
        texto1.setText(item.getTitulo());
        texto2.setText(item.getContenido());


       return convertView;
    }

    @Override
    public Filter getFilter() {


        return null;
    }
}
