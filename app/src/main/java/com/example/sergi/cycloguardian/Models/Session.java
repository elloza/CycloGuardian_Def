package com.example.sergi.cycloguardian.Models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by sergi on 13/04/2018.
 */

public class Session {
    ArrayList<Incidence> incidenceArryList;
    Date sessionStart;
    Date sessionEnd;
    long timeElapsedSession;
    Queue<Float> sensorDatesQueue;


    public Session() {
        this.incidenceArryList = new ArrayList<>();
        this.sensorDatesQueue = new LinkedList<>();
    }

    //Singleton of the class
    private static Session instance;
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }

        return instance;
    }

    //Getters and setters
    public ArrayList<Incidence> getIncidenceArryList() {
        return incidenceArryList;
    }

    public void setIncidenceArryList(ArrayList<Incidence> incidenceArryList) {
        this.incidenceArryList = incidenceArryList;
    }

    public Date getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(Date sessionStart) {
        this.sessionStart = sessionStart;
    }

    public Date getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(Date sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public long getTimeElapsedSession() {
        return timeElapsedSession;
    }

    public void setTimeElapsedSession(long timeElapsedSession) {
        this.timeElapsedSession = timeElapsedSession;
    }

    public Queue<Float> getSensorDatesQueue() {
        return sensorDatesQueue;
    }

    public void setSensorDatesQueue(Queue<Float> sensorDatesQueue) {
        this.sensorDatesQueue = sensorDatesQueue;
    }

}
