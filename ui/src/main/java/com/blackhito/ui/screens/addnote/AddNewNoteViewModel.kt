package com.blackhito.ui.screens.addnote

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackhito.domain.repository.INoteRepository
import com.blackhito.models.NoteUI
import com.blackhito.models.mappers.NoteUiToNoteMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddNewNoteViewModel(private val noteRepository: INoteRepository) : ViewModel() {
    private val _name = MutableStateFlow("")
    private val name = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    private val description = _description.asStateFlow()

    private val _dateStart = MutableStateFlow(0L)
    private val dateStart = _dateStart.asStateFlow()

    private val _dateFinish = MutableStateFlow(0L)
    private val dateFinish = _dateFinish.asStateFlow()

    fun getName() = name
    fun setName(newName: String) {
        _name.value = newName
    }

    fun getDescription() = description
    fun setDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun getDateStart() = dateStart
    fun setDateStart(newDateStart: Long, newTimeStart: Long) {
        _dateStart.update { newDateStart + newTimeStart }
    }

    fun getDateFinish() = dateFinish
    fun setDateFinish(newDateFinish: Long, newTimeFinish: Long) {
        _dateFinish.update { newDateFinish + newTimeFinish }
    }

    fun createNote() {
        viewModelScope.launch {
            noteRepository.addNewNote(
                NoteUiToNoteMapper.mapFrom(
                    NoteUI(
                        name = name.value,
                        description = description.value,
                        dateStart = dateStart.value,
                        dateFinish = dateFinish.value,
                        color = Color.LightGray
                    )
                )
            )
        }
    }
}