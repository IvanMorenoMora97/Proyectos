package com.example.agendacontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class Eventos extends AppCompatActivity implements View.OnClickListener {

    private Button btnFecha;
    private Button btnHora;
    private EditText fecha;
    private EditText hora;
    private int dia, mes, any, hora2,minutos;
    private Spinner contactos;
    private ArrayList<String> citas;
    private ArrayList<Persona> personas;
    private Contacto c;
    private FloatingActionButton insertarEvento;
    private String seleccionado;
    private EditText texto;
    private FloatingActionButton mostrarEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        c = new Contacto(getApplicationContext(),null,null,1);

        mostrarEventos = findViewById(R.id.mostrarEventos);
        insertarEvento = findViewById(R.id.insertarEvento);

        contactos = findViewById(R.id.spinner);

        texto = findViewById(R.id.infoEvento);
        btnFecha = findViewById(R.id.seleccionarFecha);
        btnHora = findViewById(R.id.seleccionarHora);
        fecha = findViewById(R.id.mostrarFecha);
        hora = findViewById(R.id.mostrarHora);


        btnFecha.setOnClickListener(this);
        btnHora.setOnClickListener(this);


        consultarContactos();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,android.R.layout.simple_spinner_item,citas);

        contactos.setAdapter(adaptador);

        contactos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        insertarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = fecha.getText().toString();
                String hora2 = hora.getText().toString();
                String text = texto.getText().toString();
                String sel = seleccionado;

                Contacto contactos = new Contacto(getApplicationContext(),null, null, 1);

                String mensaje = contactos.guardarEvento(data, hora2, sel,text);

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, mensaje, duration);
                toast.show();
            }
        });

        mostrarEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MostrarEventos.class);
                startActivity(intent);
            }
        });

    }

     private void consultarContactos() {

        SQLiteDatabase db = c.getReadableDatabase();

        Persona persona = null;

        personas = new ArrayList<Persona>();

        Cursor cursor = db.rawQuery("SELECT * FROM contactos",null);

        while(cursor.moveToNext()){

            persona = new Persona();

            persona.setId(cursor.getString(0));
            persona.setNom(cursor.getString(1));
            persona.setCognom(cursor.getString(2));
            persona.setTelefon(cursor.getString(3));
            persona.setCorreo(cursor.getString(4));
            persona.setCp(cursor.getString(5));
            persona.setCiudad(cursor.getString(6));
            persona.setPais(cursor.getString(7));
            persona.setFecha(cursor.getString(8));


            personas.add(persona);
        }
        
        obtenerlista();



    }

    private void obtenerlista() {

        citas = new ArrayList<String>();
        citas.add("Contactos");

        for(int i = 0; i<personas.size() ; i++){

            citas.add(personas.get(i).getId()+" - "+personas.get(i).getNom()+" "+personas.get(i).getCognom());

        }


    }

    @Override
    public void onClick(View v) {

        if(v==btnFecha){
            final Calendar calendario = Calendar.getInstance();
            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH);
            any = calendario.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    fecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                }
            }
            ,dia,mes,any);
            datePickerDialog.show();
        }

        if(v==btnHora){

            final Calendar calendario = Calendar.getInstance();
            hora2 = calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    hora.setText(hourOfDay+":"+minute);

                }
            },hora2,minutos,false);
            timePickerDialog.show();
        }

    }
}
