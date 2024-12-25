package com.blackhito.ui.screens.notedetails

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

internal sealed class NoteDetailsState {
    data object Loading : NoteDetailsState()
    data class Content(val note: NoteUI) : NoteDetailsState()
}

internal class NoteDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val noteRepository: INoteRepository
) : ViewModel() {
    private val noteId: Int = savedStateHandle.toRoute<Screen.NoteDetails>().id

    private val _noteState = MutableStateFlow<NoteDetailsState>(NoteDetailsState.Loading)
    private val noteState = _noteState.asStateFlow()

    fun getNoteState() = noteState

    private fun loadNote() {
        viewModelScope.launch {
            noteRepository.getNote(id = noteId).collect { loadedNote ->
                _noteState.update {
                    NoteDetailsState.Content(
                        note = NoteToNoteUiMapper.mapFrom(loadedNote)
                    )
                }
            }
        }
    }

    init {
        loadNote()
    }
}