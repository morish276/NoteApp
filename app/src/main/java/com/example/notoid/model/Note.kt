package com.example.notoid.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "notes")
@Parcelize
// This annotation makes the class Parcelable,
// allowing it to be passed b/w Android components
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteBody: String
): Parcelable
// Implements Parcelable so the Note object can be sent
// via Intents or Bundles