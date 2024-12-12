package com.blackhito.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "date_start")
    val dateStart: Long,
    @ColumnInfo(name = "date_finish")
    val dateFinish: Long,
    val name: String,
    val description: String
)