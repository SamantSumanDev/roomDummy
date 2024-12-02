package com.samant.rommdummy.repository

import androidx.lifecycle.LiveData
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.db.NotesDao
import com.samant.rommdummy.model.DeletedNote

class NoteRepository(private val dao: NotesDao) {

    val allNotes: LiveData<List<Note>> = dao.getAllNotes()
    val allDeletedNotes: LiveData<List<DeletedNote>> = dao.getAllDeletedNotes()

    suspend fun insert(note: Note) {
        dao.insert(note)
    }
    suspend fun updateNote(
        id: Int,
        noteTitle: String,
        noteDescription: String,
        noteVenue: String,
        noteMapLink: String,
        timeStamp: String
    ) {
        dao.updateNoteById(id, noteTitle, noteDescription, noteVenue, noteMapLink, timeStamp)
    }


    suspend fun deleteDeleted(deletedNote: DeletedNote) {
        dao.deleteDeleted(deletedNote)
    }


}