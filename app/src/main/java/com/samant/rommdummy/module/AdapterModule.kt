package com.samant.rommdummy.module

import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.adapters.NoteRVAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {

    @Provides
    fun provideNoteClickInterface(): NoteRVAdapter.NoteClickInterface {
        return object : NoteRVAdapter.NoteClickInterface {
            override fun onNoteClick(note: Note) {
                // Default implementation
            }
        }
    }

    @Provides
    fun provideNoteShareClickInterface(): NoteRVAdapter.NoteClickShareInterface {
        return object : NoteRVAdapter.NoteClickShareInterface {
            override fun onShareIconClick(note: Note) {
                // Default implementation
            }
        }
    }
}
