package com.samant.rommdummy.repository

import androidx.lifecycle.LiveData
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.db.NotesDao
import com.samant.rommdummy.model.DeletedNote
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NotesDao) {

    val allNotes: LiveData<List<Note>> = dao.getAllNotes()

    suspend fun insert(note: Note) {
        dao.insert(note)
    }
    suspend fun updateNote(
        note: Note
    ) {
        dao.updateNoteById(note)
    }


    suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }


}