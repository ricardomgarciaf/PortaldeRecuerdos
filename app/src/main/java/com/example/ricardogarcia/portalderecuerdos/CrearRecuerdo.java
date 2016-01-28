package com.example.ricardogarcia.portalderecuerdos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CrearRecuerdo extends AppCompatActivity {

    private EditText recuerdo;
    private DatePicker dia_recuerdo;
    private TimePicker hora_recuerdo;
    private CheckBox checkbox;
    private Boolean status_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_recuerdo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        status_check=false;

        recuerdo= (EditText) findViewById(R.id.recuerdoText);
        dia_recuerdo= (DatePicker) findViewById(R.id.diaRecuerdo);
        hora_recuerdo= (TimePicker) findViewById(R.id.horaRecuerdo);
        checkbox= (CheckBox) findViewById(R.id.checkBoxFecha);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dia_recuerdo.setEnabled(false);
                    hora_recuerdo.setEnabled(false);
                }else{
                    dia_recuerdo.setEnabled(true);
                    hora_recuerdo.setEnabled(true);
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarInput()){
                    String strdate = null;

                    if(!checkbox.isChecked()) {
                        int day = dia_recuerdo.getDayOfMonth();
                        int month = dia_recuerdo.getMonth();
                        int year = dia_recuerdo.getYear();
                        int hour = hora_recuerdo.getCurrentHour();
                        int minute = hora_recuerdo.getCurrentMinute();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day, hour, minute);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
                        if (calendar != null) {
                            strdate = sdf.format(calendar.getTime());
                        }
                    }

                    status_check=checkbox.isChecked();
                    String[] info_recuerdo=new String[]{recuerdo.getText().toString(),strdate,Aplicacion.pref.getString(Aplicacion.KEY_ID,null)};
                    new crearRecuerdoWS().execute(info_recuerdo);
                }
            }
        });
    }

    private boolean validarInput() {
        boolean errorValidacion = false;
        StringBuilder validationErrorMessage = new StringBuilder(getResources().getString(R.string.error_intro) + "\n");

        if (recuerdo.getText().toString().equals("")) {
            errorValidacion = true;
            validationErrorMessage.append(getResources().getString(R.string.recuerdo_vacio) + "\n");
        }
        if (errorValidacion) {
            Toast.makeText(getBaseContext(), validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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


    private class crearRecuerdoWS extends AsyncTask<String[], Void, Void> {

        final ProgressDialog dlg = new ProgressDialog(CrearRecuerdo.this);
        String exception_message="";

        @Override
        protected void onPreExecute() {
            dlg.setTitle(R.string.esperar);
            dlg.setMessage(getString(R.string.creando_recuerdo));
            dlg.show();
        }

        @Override
        protected Void doInBackground(String[]... params) {


            SoapPrimitive resultString=null;
            boolean registrado=false;
            String SOAP_ACTION = "http://www.example.org/SistemaRecuerdosControl/crearRecuerdo";
            String METHOD_NAME = "crearRecuerdo";
            String NAMESPACE = "http://www.example.org/SistemaRecuerdosControl";
            String URL = "http://192.168.0.20:8081/sistemarecuerdoscontrol?wsdl";

            try {

                String[] info_usuario=params[0];
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

                Request.addProperty("nombre", info_usuario[0]);
                Request.addProperty("horaRecuerdo", info_usuario[1]);
                Request.addProperty("idcreador", info_usuario[2]);

                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.dotNet = false;
                soapEnvelope.setOutputSoapObject(Request);

                HttpTransportSE transport = new HttpTransportSE(URL);

                transport.call(SOAP_ACTION, soapEnvelope);
                resultString = (SoapPrimitive) soapEnvelope.getResponse();


                //Si llega a este punto, el servidor no ha enviado ninguna excepcion
                DatabaseHandler db=DatabaseHandler.getInstance(CrearRecuerdo.this);
                Recuerdo recuerdo= new Recuerdo();
                recuerdo.setNombre(info_usuario[0]);
                if(!status_check) {
                    recuerdo.setHoraRecuerdo(info_usuario[1]);
                }
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
                String horaCreacion=null;
                if (calendar != null) {
                    horaCreacion = sdf.format(calendar.getTime());
                }
                recuerdo.setHoraCreacion(horaCreacion);
                recuerdo.setCreador(db.getUsuario(info_usuario[2]));
                db.agregarRecuerdo(recuerdo);

            } catch (Exception ex) {
                exception_message=ex.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dlg.dismiss();
            if(!exception_message.equals("")){
                Toast.makeText(CrearRecuerdo.this, exception_message, Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(CrearRecuerdo.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

}
