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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Registro extends AppCompatActivity {

    private EditText nombre;
    private EditText usuario;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre= (EditText) findViewById(R.id.nombreTexto);
        usuario= (EditText) findViewById(R.id.usuarioTexto);
        password= (EditText) findViewById(R.id.passwordTexto);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarInput()){
                    String[] info=new String[]{usuario.getText().toString(),nombre.getText().toString(),password.getText().toString()};
                    new registrarUsuarioWS().execute(info);
                }
            }
        });
    }


    private boolean validarInput() {
        boolean errorValidacion = false;
        StringBuilder validationErrorMessage = new StringBuilder(getResources().getString(R.string.error_intro) + "\n");

            if (nombre.getText().toString().equals("")) {
                errorValidacion = true;
                validationErrorMessage.append(getResources().getString(R.string.error_nombrevacio) + "\n");
            }
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



    private class registrarUsuarioWS extends AsyncTask<String[], Void, Void> {

        final ProgressDialog dlg = new ProgressDialog(Registro.this);
        String exception_message="";

        @Override
        protected void onPreExecute() {
            dlg.setTitle(R.string.esperar);
            dlg.setMessage(getString(R.string.registrando));
            dlg.show();
        }

        @Override
        protected Void doInBackground(String[]... params) {


            SoapPrimitive resultString=null;
            boolean registrado=false;
            String SOAP_ACTION = "http://www.example.org/SistemaRecuerdosControl/registrarUsuario";
            String METHOD_NAME = "registrarUsuario";
            String NAMESPACE = "http://www.example.org/SistemaRecuerdosControl";
            String URL = "http://192.168.0.20:8081/sistemarecuerdoscontrol?wsdl";

            try {

                String[] info_usuario=params[0];
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

                Request.addProperty("id", info_usuario[0]);
                Request.addProperty("nombre", info_usuario[1]);
                Request.addProperty("password", info_usuario[2]);

                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.dotNet = false;
                soapEnvelope.setOutputSoapObject(Request);

                HttpTransportSE transport = new HttpTransportSE(URL);

                transport.call(SOAP_ACTION, soapEnvelope);
                resultString = (SoapPrimitive) soapEnvelope.getResponse();

                //Si llega a este punto, el servidor no ha enviado ninguna excepcion
                DatabaseHandler db=DatabaseHandler.getInstance(Registro.this);
                Usuario usuario= new Usuario();
                usuario.setId(info_usuario[0]);
                usuario.setNombre(info_usuario[1]);
                usuario.setPassword(info_usuario[2]);
                db.agregarUsuario(usuario);

            } catch (Exception ex) {
                exception_message=ex.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dlg.dismiss();
            if(!exception_message.equals("")){
                Toast.makeText(Registro.this,exception_message, Toast.LENGTH_LONG).show();
            }else{
                Aplicacion.editor.putBoolean(Aplicacion.IS_LOGIN, true);
                Aplicacion.editor.putString(Aplicacion.KEY_ID, usuario.getText().toString());
                Aplicacion.editor.putString(Aplicacion.KEY_NOMBRE, nombre.getText().toString());
                Aplicacion.editor.commit();

                Intent intent = new Intent(Registro.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

}
