package com.example.note.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.note.Model.Notes;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = REPLACE)
    void insert(Notes notes);

    @Query("SELECT * FROM note ORDER BY id DESC")
    List<Notes> getall();

    @Query("UPDATE note SET title= :title,note= :notes WHERE id= :id")
    void update(int id, String title, String notes);

    @Delete
    void delete(Notes notes);

}
