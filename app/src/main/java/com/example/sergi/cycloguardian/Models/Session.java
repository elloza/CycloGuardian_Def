package com.example.sergi.cycloguardian.Models;

import android.app.Application;

import java.util.ArrayList;


/**
 * Created by sergi on 13/04/2018.
 */

public class Sesion extends Application {
    ArrayList<Incidencia> incidenciaArryList;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
