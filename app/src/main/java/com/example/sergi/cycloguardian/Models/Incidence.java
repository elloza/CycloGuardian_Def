package com.example.sergi.cycloguardian.Models;

import android.content.Intent;

import com.example.sergi.cycloguardian.Files.Photo;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sergi on 13/04/2018.
 */

public class Incidencia {
    LatLng posicion;
    Photo image;
    ArrayList<Double> datosSensor;
    Date timeIncidence;

    //Constructor of the class
    public Incidencia() {

    }

    //Getters and setters
    public LatLng getPosicion() {
        return posicion;
    }

    public void setPosicion(LatLng posicion) {
        this.posicion = posicion;
    }

    public Photo getImage() {
        return image;
    }

    public void setImage(Photo image) {
        this.image = image;
    }

    public ArrayList<Double> getDatosSensor() {
        return datosSensor;
    }

    public void setDatosSensor(ArrayList<Double> datosSensor) {
        this.datosSensor = datosSensor;
    }

    public Date getTimeIncidence() {
        return timeIncidence;
    }

    public void setTimeIncidence(Date timeIncidence) {
        this.timeIncidence = timeIncidence;
    }
}
