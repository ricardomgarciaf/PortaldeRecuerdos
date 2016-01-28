package com.example.ricardogarcia.portalderecuerdos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class InicioSesion extends AppCompatActivity {

    private EditText usuario;
    private EditText password;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuario = (EditText) findViewById(R.id.usuarioTexto);
        password = (EditText) findViewById(R.id.passwordTexto);
    }


    public void irRegistro(View view) {
        startActivity(new Intent(this, Registro.class));
    }

    public void validarUsuario(View view) {
        if(validarInput()){
            String[] info_usuario=new String[]{usuario.getText().toString(),password.getText().toString()};
            new validarUsuarioWS().execute(info_usuario);
        }

    }

    private boolean validarInput() {
        boolean errorValidacion = false;
        StringBuilder validationErrorMessage = new StringBuilder(getResources().getString(R.string.error_intro) + "\n");

        if (usuario.getText().toString().equals("")) {
            errorValidacion = true;
            validationErrorMessage.append(getResources().getString(R.string.error_usuariovacio) + "\n");
        }
        if (password.getText().toString().equals("")) {
            errorValidacion = true;
            validationErrorMessage.append(getResources().getString(R.string.error_passwordvacio) + "\n");
        }


        if (errorValidacion) {
            Toast.makeText(getBaseContext(), validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private class validarUsuarioWS extends AsyncTask<String[], Void, String> {

        final ProgressDialog dlg = new ProgressDialog(InicioSesion.this);
        String exception_message="";
        @Override
        protected void onPreExecute() {
            dlg.setTitle(R.string.esperar);
            dlg.setMessage(getString(R.string.iniciandoSesion));
            dlg.show();
        }

        @Override
        protected String doInBackground(String[]... params) {

            SoapPrimitive resultString=null;
            String SOAP_ACTION = "http://www.example.org/SistemaRecuerdosGeneral/ingresarUsuario";
            String METHOD_NAME = "ingresarUsuario";
            String NAMESPACE = "http://www.example.org/SistemaRecuerdosGeneral";
            String URL = "http://192.168.0.20:8080/sistemarecuerdosgeneral?wsdl";
            String response="";

            try {

                String[] info_usuario=params[0];
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

                Request.addProperty("id", info_usuario[0]);
                Request.addProperty("password", info_usuario[1]);

                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.dotNet = false;
                soapEnvelope.setOutputSoapObject(Request);

                HttpTransportSE transport = new HttpTransportSE(URL);

                transport.call(SOAP_ACTION, soapEnvelope);
                resultString = (SoapPrimitive) soapEnvelope.getResponse();

            } catch (Exception ex) {
                exception_message=ex.getMessage();
            }

            if(resultString!=null){
                response=resultString.toString();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String result) {
            dlg.dismiss();
            if(result.equals("")){
                Toast.makeText(InicioSesion.this,exception_message, Toast.LENGTH_LONG).show();
            }else{

                Aplicacion.editor.putBoolean(Aplicacion.IS_LOGIN, true);
                Aplicacion.editor.putString(Aplicacion.KEY_ID, usuario.getText().toString());
                Aplicacion.editor.putString(Aplicacion.KEY_NOMBRE, result);
                Aplicacion.editor.commit();

                Intent intent = new Intent(InicioSesion.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }


}


