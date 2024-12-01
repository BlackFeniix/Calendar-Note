package com.blackhito.domain.repository

import com.blackhito.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNote(id: Int): Flow<Note>

    fun getAllNotesInDay(): Flow<List<Note>>

    suspend fun addNewNote(note: Note)

    suspend fun deleteNote(id: Int)
}