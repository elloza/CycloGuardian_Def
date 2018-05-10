package com.example.sergi.cycloguardian.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by sergi on 23/04/2018.
 */

@Entity(tableName = "photos")
public class PhotoEntity {
    @PrimaryKey(autoGenerate = true)
    int photoId;

    int idIncidence;
    int uuid;
    String namePhoto;
    String rutaPhoto;
    Boolean syncronized;


    public String getRutaPhoto() {
        return rutaPhoto;
    }

    public void setRutaPhoto(String rutaPhoto) {
        this.rutaPhoto = rutaPhoto;
    }

    public Boolean getSyncronized() {
        return syncronized;
    }

    public void setSyncronized(Boolean syncronized) {
        this.syncronized = syncronized;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getIdIncidence() {
        return idIncidence;
    }

    public void setIdIncidence(int idIncidence) {
        this.idIncidence = idIncidence;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getNamePhoto() {
        return namePhoto;
    }

    public void setNamePhoto(String namePhoto) {
        this.namePhoto = namePhoto;
    }


}
