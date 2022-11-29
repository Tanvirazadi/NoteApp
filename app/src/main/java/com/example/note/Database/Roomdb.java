package com.example.note.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.note.Model.Notes;

@Database(entities = Notes.class,version = 1,exportSchema = false)
public abstract  class Roomdb extends RoomDatabase {
    private static Roomdb database;
    private static String DATABSENAME="Noteapp";
    public synchronized static Roomdb getInstance(Context context){
        if (database==null){
            database= Room.databaseBuilder(context.getApplicationContext(),Roomdb.class,DATABSENAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
    public abstract NoteDao getNoteDao();

}
