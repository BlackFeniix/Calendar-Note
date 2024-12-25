package com.blackhito.data.repositoryImpl

import com.blackhito.data.database.NoteDao
import com.blackhito.data.models.mappers.NoteEntityToNoteMapper
import com.blackhito.data.models.mappers.NoteToNoteEntityMapper
import com.blackhito.domain.models.Note
import com.blackhito.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val noteDao: NoteDao) : INoteRepository {
    override fun getNote(id: Int): Flow<Note> {
        return noteDao.getNote(id = id).map { NoteEntityToNoteMapper.mapFrom(it) }
    }

    override fun getAllNotesInDay(startDay: Long, finishDay: Long): Flow<List<Note>> {
        return noteDao.getAllNotesInDay(startDay = startDay, finishDay = finishDay)
            .map { it.map { NoteEntityToNoteMapper.mapFrom(it) } }
    }

    override suspend fun addNewNote(note: Note) {
        noteDao.addNote(note = NoteToNoteEntityMapper.mapFrom(note))
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note = NoteToNoteEntityMapper.mapFrom(note))
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note = NoteToNoteEntityMapper.mapFrom(note))
    }
}