package com.example.sergi.cycloguardian.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by sergi on 23/04/2018.
 */

@Entity(tableName = "incidences")
public class IncidenceEntity {
    @PrimaryKey(autoGenerate = true)
    int incidenceId;

    int idSession;
    int idUser;
    double latitude;
    double longitude;
   // Date timeIncidence;
    int uuid;
    long distanceSensor;
    Boolean syncronized;

    public Boolean getSyncronized() {
        return syncronized;
    }

    public void setSyncronized(Boolean syncronized) {
        this.syncronized = syncronized;
    }

    public int getIncidenceId() {
        return incidenceId;
    }

    public void setIncidenceId(int incidenceId) {
        this.incidenceId = incidenceId;
    }

    public int getIdSession() {
        return idSession;
    }

    public void setIdSession(int idSession) {
        this.idSession = idSession;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

   /* public Date getTimeIncidence() {
        return timeIncidence;
    }

    public void setTimeIncidence(Date timeIncidence) {
        this.timeIncidence = timeIncidence;
    }*/

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public long getDistanceSensor() {
        return distanceSensor;
    }

    public void setDistanceSensor(long distanceSensor) {
        this.distanceSensor = distanceSensor;
    }


}
