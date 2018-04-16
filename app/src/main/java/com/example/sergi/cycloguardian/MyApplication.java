package com.example.sergi.cycloguardian;

import android.app.Application;

import com.example.sergi.cycloguardian.Models.Session;

/**
 * Created by sergi on 16/04/2018.
 */

public class MyApplication extends Application {
    public Session mySession = new Session();
}
