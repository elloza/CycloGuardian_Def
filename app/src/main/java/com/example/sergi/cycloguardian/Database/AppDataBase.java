package com.example.sergi.cycloguardian.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.sergi.cycloguardian.Models.Incidence;

/**
 * Created by sergi on 23/04/2018.
 */

@Database(entities = {SessionEntity.class, PhotoEntity.class, IncidenceEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract SessionDao sessionDao();
    public abstract IncidenceDao incidenceDao();
    public abstract PhotoDao photoDao();
}
