package com.samant.rommdummy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.db.NoteDatabase
import com.samant.rommdummy.db.NotesDao
import com.samant.rommdummy.model.DeletedNote
import com.samant.rommdummy.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun deleteNoteAndRecord(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }
}


