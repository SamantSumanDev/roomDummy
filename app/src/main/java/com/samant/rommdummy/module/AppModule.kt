package com.samant.rommdummy.module

import android.content.Context
import androidx.room.Room
import com.samant.rommdummy.db.NoteDatabase
import com.samant.rommdummy.db.NotesDao
import com.samant.rommdummy.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_database_todo"
        ).build()
    }

    @Provides
    fun provideNoteDao(database: NoteDatabase): NotesDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NotesDao): NoteRepository {
        return NoteRepository(noteDao)
    }
}
