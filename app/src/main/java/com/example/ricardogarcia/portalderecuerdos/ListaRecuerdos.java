package com.example.ricardogarcia.portalderecuerdos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ListaRecuerdos extends AppCompatActivity {

    private TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_recuerdos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titulo= (TextView) findViewById(R.id.resultRecuerdos);
        Intent intent= getIntent();
        Bundle b=intent.getExtras();

        new buscarRecuerdoPorPatronWS().execute(b.getString(Home.PATRON));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaRecuerdos.this,Home.class));
                finish();
            }
        });
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

    private class buscarRecuerdoPorPatronWS extends AsyncTask<String, Void, ArrayList<Recuerdo>> {

        private ProgressDialog progressDialog = new ProgressDialog(ListaRecuerdos.this);
        String exception_message="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle(R.string.cargando_recuerdos);
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        protected ArrayList<Recuerdo> doInBackground(String... params) {


            SoapPrimitive resultString=null;
            String SOAP_ACTION = "http://www.example.org/SistemaRecuerdosGeneral/buscarRecuerdoPorPatron";
            String METHOD_NAME = "buscarRecuerdoPorPatron";
            String NAMESPACE = "http://www.example.org/SistemaRecuerdosGeneral";
            String URL = "http://192.168.0.20:8080/sistemarecuerdosgeneral?wsdl";

            try {

                String patron=params[0];
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

                Request.addProperty("patron", patron);

                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.dotNet = false;
                soapEnvelope.setOutputSoapObject(Request);

                HttpTransportSE transport = new HttpTransportSE(URL);

                transport.call(SOAP_ACTION, soapEnvelope);
                resultString = (SoapPrimitive) soapEnvelope.getResponse();
                exception_message=resultString.toString();

                //List<Recuerdo> resultado_recuerdos= (List<Recuerdo>) resultString;




            } catch (Exception ex) {
               exception_message=ex.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Recuerdo> recuerdos) {
            super.onPostExecute(recuerdos);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            RecuerdoAdapter rAdapter= new RecuerdoAdapter(ListaRecuerdos.this,recuerdos);
            ListView list_recuerdos= (ListView) findViewById(R.id.listResults);
            list_recuerdos.setAdapter(rAdapter);
            list_recuerdos.setEmptyView(findViewById(R.id.emptyView));
        }
    }
}
