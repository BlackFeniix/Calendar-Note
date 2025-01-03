package com.blackhito.domain.repository

import com.blackhito.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    fun getNote(id: Int): Flow<Note>

    fun getAllNotesInDay(startDay: Long, finishDay: Long): Flow<List<Note>>

    suspend fun addNewNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}