package com.blackhito.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.blackhito.data.models.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("SELECT * FROM noteentity WHERE id=:id")
    fun getNote(id: Int): Flow<NoteEntity>

    @Query("SELECT * FROM noteentity")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM noteentity WHERE date_start<:finishDay and date_finish>:startDay")
    fun getAllNotesInDay(startDay: Long, finishDay: Long): Flow<List<NoteEntity>>
}