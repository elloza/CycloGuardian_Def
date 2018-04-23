package com.example.sergi.cycloguardian.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.sergi.cycloguardian.Models.Session;

/**
 * Created by sergi on 23/04/2018.
 */

@Database(entities = {Session.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract SessionDao sessionDao();
}
