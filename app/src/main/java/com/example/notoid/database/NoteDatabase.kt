package com.example.notoid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notoid.model.Note

// Alright, this tells Room that we're creating a database with the Note entity.
// We're starting with version 1.
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    // This function will give us access to all the DAO methods (insert, delete, update, etc.)
    abstract fun getNoteDao(): NoteDAO

    companion object {
        // We mark this variable as @Volatile so that it’s always up-to-date and visible to all threads.
        // Think of it like a single source of truth.
        @Volatile
        private var instance: NoteDatabase? = null

        // Just a regular lock object to make sure only one thread initializes the DB at a time.
        private val LOCK = Any()

        // This function is how we’ll get the database instance.
        // If it already exists, we use it.
        // If not, we go ahead and create one.
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            // Double-check again inside synchronized block — just to be extra safe.
            instance ?: createDatabase(context).also {
                instance = it // Save the instance so we don’t have to build it again.
            }
        }

        // Here’s where we actually build the database.
        // We’re giving it the application context (to avoid memory leaks),
        // the class type, and the name "note_db" for the database file.
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }
}