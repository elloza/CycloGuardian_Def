package com.example.sergi.cycloguardian.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.sergi.cycloguardian.Models.Session;

import java.util.List;

/**
 * Created by sergi on 23/04/2018.
 */

@Dao
public interface SessionDao {
    @Query("SELECT * FROM sessions")
    List<SessionEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSession(SessionEntity session);

    @Delete
    void deleteSession(SessionEntity session);
}
