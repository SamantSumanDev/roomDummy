package com.gamdestroyerr.roomnote.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "notesTable")
@Parcelize // Add @Parcelize annotation to generate Parcelable implementation
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "title") val noteTitle: String,
    @ColumnInfo(name = "description") val noteDescription: String,
    @ColumnInfo(name = "venue") val noteVenue: String, // Corrected typo in venue
    @ColumnInfo(name = "mapLink") val noteMapLink: String,
    @ColumnInfo(name = "timestamp") val timeStamp: String
) : Parcelable