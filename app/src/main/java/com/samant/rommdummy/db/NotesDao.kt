package com.samant.rommdummy.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.model.DeletedNote

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM notesTable WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM notesTable ORDER BY timeStamp DESC")
    fun getAllNotes(): LiveData<List<Note>>

  /*  @Insert
    suspend fun insertDeleted(deletedNote: DeletedNote)
*/
  /*  @Query("SELECT * FROM deleted_notes ORDER BY id DESC")
    fun getAllDeletedNotes(): LiveData<List<DeletedNote>>
*/
    @Delete
    suspend fun deleteNote(note: Note)


   @Update
    suspend fun updateNoteById(
        note:Note
    )
}