package com.samant.rommdummy.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.model.DeletedNote

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NotesDao
}
