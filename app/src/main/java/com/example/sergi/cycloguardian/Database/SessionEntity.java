package com.example.sergi.cycloguardian.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


/**
 * Created by sergi on 23/04/2018.
 */

@Entity(tableName = "sessions")
public class SessionEntity {
    @PrimaryKey(autoGenerate = true)
    int sessionId;
    int userId;
    int uuid;
    /*Date sessionStart;
    Date sessionEnd;*/
    long timeElapssed;
    Boolean syncronized;

    public Boolean getSyncronized() {
        return syncronized;
    }

    public void setSyncronized(Boolean syncronized) {
        this.syncronized = syncronized;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    /*public Date getSessionStart() {
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
    }*/

    public long getTimeElapssed() {
        return timeElapssed;
    }

    public void setTimeElapssed(long timeElapssed) {
        this.timeElapssed = timeElapssed;
    }


}
