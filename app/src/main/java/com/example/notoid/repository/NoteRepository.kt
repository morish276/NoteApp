package com.example.notoid.repository

import com.example.notoid.database.NoteDatabase
import com.example.notoid.model.Note

// This class acts as a middleman between the ViewModel and the DAO (database)
// It helps keep your code clean by keeping all data-related operations here
class NoteRepository (private val db: NoteDatabase){
    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String) = db.getNoteDao().searchNote(query)
}