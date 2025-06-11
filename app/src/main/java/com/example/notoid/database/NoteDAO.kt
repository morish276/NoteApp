package com.example.notoid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notoid.model.Note

@Dao // Data Access Object for Note entity
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)  // Inserts a new note or replaces it if the ID already exists
    suspend fun insertNote(note: Note) // suspend: does it on a background thread. Doesn’t block the UI

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM NOTES ORDER BY id DESC")
    fun getAllNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM NOTES WHERE noteTitle LIKE :query OR noteBody LIKE :query")
    fun searchNote(query: String?) : LiveData<List<Note>>
}