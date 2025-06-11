package com.example.notoid.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notoid.repository.NoteRepository

// This is a custom ViewModelFactory — we use this when our ViewModel has parameters (like Application or Repository)
// The default ViewModelProvider can’t handle constructors with arguments, so we build our own!
class NoteViewmodelFactory(val app: Application, private val noteRepository: NoteRepository) : ViewModelProvider.Factory {

    // This method actually creates the ViewModel instance.
    // We check if the requested ViewModel class is NoteViewmodel, and if it is, we return it.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // We're forcing the cast here because we know we're creating a NoteViewmodel
        return NoteViewmodel(app, noteRepository) as T
    }
}
