package com.example.ricardogarcia.portalderecuerdos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ricardogarcia on 1/27/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler sInstance;
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PortalRecuerdos";

    private static final String tableUsuario = "USUARIO";
    private static final String tableRecuerdo = "RECUERDO";
    private static final String tableEstado = "ESTADO";
    private static final String tableLugar = "LUGAR";
    private static final String tableLugarxRecuerdo = "LUGARXRECUERDO";
    private static final String tableObjeto = "OBJETO";
    private static final String tableObjetoxRecuerdo = "OBJETOXRECUERDO";
    private static final String tableNota = "NOTA";
    private static final String tableNotaxRecuerdo = "NOTAXRECUERDO";
    private static final String tablePersonaxRecuerdo = "PERSONAXRECUERDO";
    private static final String tableAmigo = "AMIGO";
    private Context ctx;


    public static synchronized DatabaseHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREAR_TABLA_USUARIO = "CREATE TABLE " + tableUsuario + "("
                + "ID TEXT PRIMARY KEY, "
                + "NOMBRE TEXT NOT NULL, "
                + "PASSWORD TEXT NOT NULL "
                + ")";
        sqLiteDatabase.execSQL(CREAR_TABLA_USUARIO);

        String CREAR_TABLA_RECUERDO = "CREATE TABLE " + tableRecuerdo + "("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NOMBRE TEXT NOT NULL, "
                + "HORACREACION TEXT NOT NULL, "
                + "HORARECUERDO TEXT, "
                + "CREADOR TEXT NOT NULL, "
                + "FOREIGN KEY (CREADOR) REFERENCES " + tableUsuario + "(ID))";
        sqLiteDatabase.execSQL(CREAR_TABLA_RECUERDO);

        String CREAR_TABLA_ESTADO = "CREATE TABLE " + tableEstado + "("
                + "IDRECUERDO INTEGER, "
                + "IDPERSONA TEXT, "
                + "ESTADO TEXT DEFAULT 'SOSPECHA', "
                + "PRIMARY KEY (IDRECUERDO,IDPERSONA), "
                + "FOREIGN KEY (IDRECUERDO) REFERENCES "+tableRecuerdo+"(ID), "
                + "FOREIGN KEY (IDPERSONA) REFERENCES " + tableUsuario + "(ID))";
        sqLiteDatabase.execSQL(CREAR_TABLA_ESTADO);

        String CREAR_TABLA_LUGAR = "CREATE TABLE " + tableLugar + "("
                + "NOMBRE TEXT PRIMARY KEY, "
                + "CREADOR TEXT NOT NULL, "
                + "FOREIGN KEY (CREADOR) REFERENCES " + tableUsuario + "(ID))";
        sqLiteDatabase.execSQL(CREAR_TABLA_LUGAR);

        String CREAR_TABLA_LUGARXRECUERDO = "CREATE TABLE " + tableLugarxRecuerdo + "("
                + "IDRECUERDO INTEGER PRIMARY KEY, "
                + "NOMBRELUGAR TEXT NOT NULL, "
                + "ASOCIADOR TEXT NOT NULL, "
                + "FOREIGN KEY (IDRECUERDO) REFERENCES "+tableRecuerdo+"(ID), "
                + "FOREIGN KEY (NOMBRELUGAR) REFERENCES "+tableLugar+"(NOMBRE), "
                + "FOREIGN KEY (ASOCIADOR) REFERENCES " + tableUsuario + "(ID))";
        sqLiteDatabase.execSQL(CREAR_TABLA_LUGARXRECUERDO);

        String CREAR_TABLA_OBJETO = "CREATE TABLE " + tableObjeto + "("
                + "NOMBRE TEXT PRIMARY KEY, "
                + "CREADOR TEXT NOT NULL, "
                + "FOREIGN KEY (CREADOR) REFERENCES " + tableUsuario + "(ID))";
        sqLiteDatabase.execSQL(CREAR_TABLA_OBJETO);

        String CREAR_TABLA_OBJETOXRECUERDO = "CREATE TABLE " + tableObjetoxRecuerdo + "("
                + "IDRECUERDO INTEGER, "
                + "NOMBREOBJETO TEXT, "
                + "ASOCIADOR TEXT NOT NULL, "
                + "PRIMARY KEY(IDRECUERDO,NOMBREOBJETO), "
                + "FOREIGN KEY (IDRECUERDO) REFERENCES "+tableRecuerdo+"(ID), "
                + "FOREIGN KEY (NOMBREOBJETO) REFERENCES "+tableObjeto+"(NOMBRE), "
                + "FOREIGN KEY (ASOCIADOR) REFERENCES " + tableUsuario + "(ID))";
        sqLiteDatabase.execSQL(CREAR_TABLA_OBJETOXRECUERDO);

        String CREAR_TABLA_NOTA = "CREATE TABLE " + tableNota + "("
                + "NOMBRE TEXT PRIMARY KEY, "
                + "CREADOR TEXT NOT NULL, "
                + "FOREIGN KEY (CREADOR) REFERENCES " + tableUsuario + "(ID))";
        sqLiteDatabase.execSQL(CREAR_TABLA_NOTA);

        String CREAR_TABLA_NOTAXRECUERDO = "CREATE TABLE " + tableNotaxRecuerdo + "("
                + "IDRECUERDO INTEGER, "
                + "NOMBRENOTA TEXT, "
                + "ASOCIADOR TEXT NOT NULL, "
                + "PRIMARY KEY(IDRECUERDO,NOMBRENOTA), "
                + "FOREIGN KEY (IDRECUERDO) REFERENCES "+tableRecuerdo+"(ID), "
                + "FOREIGN KEY (NOMBRENOTA) REFERENCES "+tableNota+"(NOMBRE), "
                + "FOREIGN KEY (ASOCIADOR) REFERENCES " + tableUsuario + "(ID))";
        sqLiteDatabase.execSQL(CREAR_TABLA_NOTAXRECUERDO);

        String CREAR_TABLA_PERSONAXRECUERDO = "CREATE TABLE " + tablePersonaxRecuerdo + "("
                + "IDRECUERDO INTEGER, "
                + "IDPERSONA TEXT, "
                + "ASOCIADOR TEXT NOT NULL, "
                + "PRIMARY KEY(IDRECUERDO,IDPERSONA), "
                + "FOREIGN KEY (IDRECUERDO) REFERENCES "+tableRecuerdo+"(ID), "
                + "FOREIGN KEY (IDPERSONA) REFERENCES "+tableUsuario+"(ID), "
                + "FOREIGN KEY (ASOCIADOR) REFERENCES " + tableUsuario + "(ID))";
        sqLiteDatabase.execSQL(CREAR_TABLA_PERSONAXRECUERDO);

        String CREAR_TABLA_AMIGO = "CREATE TABLE " + tableAmigo + "("
                + "IDUSUARIO TEXT, "
                + "IDAMIGO TEXT, "
                + "PRIMARY KEY(IDUSUARIO,IDAMIGO), "
                + "FOREIGN KEY (IDUSUARIO) REFERENCES "+tableUsuario+"(ID), "
                + "FOREIGN KEY (IDAMIGO) REFERENCES " + tableUsuario + "(ID))";
        sqLiteDatabase.execSQL(CREAR_TABLA_AMIGO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableUsuario);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableRecuerdo);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableEstado);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableLugar);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableLugarxRecuerdo);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableObjeto);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableObjetoxRecuerdo);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableNota);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableNotaxRecuerdo);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tablePersonaxRecuerdo);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableAmigo);

        onCreate(sqLiteDatabase);
    }

    public void agregarUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", usuario.getId());
        values.put("NOMBRE", usuario.getNombre());
        values.put("PASSWORD", usuario.getPassword());

        db.insert(tableUsuario, null, values);
        db.close();
    }

    public void agregarRecuerdo(Recuerdo recuerdo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE", recuerdo.getNombre());
        values.put("HORACREACION", recuerdo.getHoraCreacion());
        values.put("HORARECUERDO", recuerdo.getHoraRecuerdo());
        values.put("CREADOR", recuerdo.getCreador().getId());

        db.insert(tableRecuerdo, null, values);
        db.close();
    }

    public void agregarLugar(Lugar lugar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE", lugar.getNombre());
        values.put("CREADOR", lugar.getCreador().getId());

        db.insert(tableLugar, null, values);
        db.close();
    }

    public void agregarObjeto(Objeto objeto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE", objeto.getNombre());
        values.put("CREADOR", objeto.getCreador().getId());

        db.insert(tableObjeto, null, values);
        db.close();
    }

    public void agregarNota(Nota nota){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE", nota.getNombre());
        values.put("CREADOR", nota.getCreador().getId());

        db.insert(tableNota, null, values);
        db.close();
    }

    public boolean usuarioRegistrado(String usuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableUsuario, null,"ID=?", new String[]{usuario}, null, null, null);

        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public Usuario getUsuario(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableUsuario, null, "ID=?", new String[]{id}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Usuario usuario = new Usuario(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        return usuario;
    }

}
