package com.example.ricardogarcia.portalderecuerdos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ricardogarcia on 1/28/16.
 */
public class Aplicacion extends Application {


    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static final String PREF_NAME = "SessionPreferences";
    public static int PRIVATE_MODE = 0;
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "ID";
    public static final String KEY_NOMBRE = "email";

    @Override
    public void onCreate() {
        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        super.onCreate();
    }


}
