package com.example.sergi.cycloguardian.Files;

import android.graphics.Bitmap;

/**
 * Created by sergi on 20/03/2018.
 */

public class Photo {
    String url = null;
    String namePhoto = null;
    String rutaInterna = null;

    public String getRutaInterna() {
        return rutaInterna;
    }

    public void setRutaInterna(String rutaInterna) {
        this.rutaInterna = rutaInterna;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNamePhoto() {
        return namePhoto;
    }

    public void setNamePhoto(String namePhoto) {
        this.namePhoto = namePhoto;
    }

    public Photo (String url, String namePhoto) {
        this.url = url;
        this.namePhoto = namePhoto;
    }
}
