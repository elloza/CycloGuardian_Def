package com.example.sergi.cycloguardian.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.sergi.cycloguardian.Models.Incidence;
import com.example.sergi.cycloguardian.Models.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

/**
 * Created by sergi on 23/04/2018.
 */

@Entity(tableName = "sessions")
public class SessionEntity extends Session {
    @PrimaryKey(autoGenerate = true)
    int sessionId;

    public SessionEntity() {
        super();
    }


    @Override
    public ArrayList<Incidence> getIncidenceArryList() {
        return super.getIncidenceArryList();
    }

    @Override
    public void setIncidenceArryList(ArrayList<Incidence> incidenceArryList) {
        super.setIncidenceArryList(incidenceArryList);
    }

    @Override
    public Date getSessionStart() {
        return super.getSessionStart();
    }

    @Override
    public void setSessionStart(Date sessionStart) {
        super.setSessionStart(sessionStart);
    }

    @Override
    public Date getSessionEnd() {
        return super.getSessionEnd();
    }

    @Override
    public void setSessionEnd(Date sessionEnd) {
        super.setSessionEnd(sessionEnd);
    }

    @Override
    public long getTimeElapsedSession() {
        return super.getTimeElapsedSession();
    }

    @Override
    public void setTimeElapsedSession(long timeElapsedSession) {
        super.setTimeElapsedSession(timeElapsedSession);
    }

    @Override
    public Queue<Float> getSensorDatesQueue() {
        return super.getSensorDatesQueue();
    }

    @Override
    public void setSensorDatesQueue(Queue<Float> sensorDatesQueue) {
        super.setSensorDatesQueue(sensorDatesQueue);
    }
}
