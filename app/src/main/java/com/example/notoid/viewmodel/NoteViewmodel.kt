package com.example.notoid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notoid.model.Note
import com.example.notoid.repository.NoteRepository
import kotlinx.coroutines.launch

// This is our ViewModel. It's the bridge between the UI and the data (via Repository)
// Since we're dealing with Room and context here, we use AndroidViewModel instead of regular ViewModel
class NoteViewmodel(app: Application, private val noteRepository: NoteRepository) : AndroidViewModel(app) {
    // launching a coroutine — this keeps it off the main thread (so the UI doesn’t lag)
    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    // Just grabs all the notes from the repository
    // Since it's LiveData, it updates automatically if anything changes
    fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNote(query: String) = noteRepository.searchNote(query)
}