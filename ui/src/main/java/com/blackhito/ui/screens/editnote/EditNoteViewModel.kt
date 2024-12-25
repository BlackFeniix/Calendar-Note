package com.blackhito.ui.screens.editnote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.blackhito.domain.repository.INoteRepository
import com.blackhito.models.NoteUI
import com.blackhito.models.mappers.NoteToNoteUiMapper
import com.blackhito.models.mappers.NoteUiToNoteMapper
import com.blackhito.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class EditNoteViewModel(
    savedStateHandle: SavedStateHandle,
    private val noteRepository: INoteRepository
) : ViewModel() {
    private val noteId: Int = savedStateHandle.toRoute<Screen.EditNote>().id

    private val _noteState = MutableStateFlow(NoteUI(0, 0, 0, "", ""))
    private val noteState = _noteState.asStateFlow()

    fun getNoteState() = noteState

    private fun loadNote() {
        viewModelScope.launch {
            noteRepository.getNote(id = noteId).collect { loadedNote ->
                _noteState.update {
                    NoteToNoteUiMapper.mapFrom(loadedNote)
                }
            }
        }
    }

    fun setName(newName: String) {
        _noteState.update {
            it.copy(name = newName)
        }
    }

    fun updateNote() {
        viewModelScope.launch {
            noteRepository.updateNote(note = NoteUiToNoteMapper.mapFrom(_noteState.value))
        }
    }

    init {
        loadNote()
    }
}