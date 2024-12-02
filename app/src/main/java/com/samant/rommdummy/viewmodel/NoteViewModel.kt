package com.samant.rommdummy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.db.NoteDatabase
import com.samant.rommdummy.db.NotesDao
import com.samant.rommdummy.model.DeletedNote
import com.samant.rommdummy.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>
    val allDeletedNotes: LiveData<List<DeletedNote>>
    private val notesDao: NotesDao = NoteDatabase.getDatabase(application).getNotesDao()

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
        allDeletedNotes = repository.allDeletedNotes
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
    fun deleteNoteAndRecord(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesDao.insertDeleted(DeletedNote(
                noteTitle = note.noteTitle,
                noteDescription = note.noteDescription,
                noteVenue = note.noteVenue,
                noteMapLink = note.noteMapLink,
                timeStamp = note.timeStamp
            ))

            notesDao.delete(note.id)
        }
    }

  /*  fun deleteDeleted(deletedNote: DeletedNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDeleted(deletedNote)
        }
    }
*/

    fun updateNote(
        id: Int,
        noteTitle: String,
        noteDescription: String,
        noteVenue: String,
        noteMapLink: String,
        timeStamp: String
    ) {
        viewModelScope.launch {
            repository.updateNote(id, noteTitle, noteDescription, noteVenue, noteMapLink, timeStamp)
        }
    }

}