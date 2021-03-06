package com.example.ricardogarcia.portalderecuerdos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Home extends AppCompatActivity {

    private EditText patronBusqueda;
    public static String PATRON="com.example.ricardogarcia.portalderecuerdos.PATRON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        patronBusqueda= (EditText) findViewById(R.id.patronTexto);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
                Intent intent = new Intent(this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
        }
        else if (id == R.id.action_perfil) {
            Intent intent = new Intent(this, Perfil.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_creation) {
            Intent intent = new Intent(this, Creacion.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_amigos) {
            Intent intent = new Intent(this, VerAmigos.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_logout) {
            Aplicacion.editor.clear();
            Aplicacion.editor.commit();
            Intent intent= new Intent(this,InicioSesion.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buscarRecuerdos(View view) {
        Intent intent=new Intent(this,ListaRecuerdos.class);
        intent.putExtra(PATRON,patronBusqueda.getText().toString());
        startActivity(intent);
    }


}
