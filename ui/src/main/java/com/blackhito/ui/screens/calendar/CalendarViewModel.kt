package com.blackhito.ui.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackhito.domain.repository.INoteRepository
import com.blackhito.models.NoteUI
import com.blackhito.models.mappers.NoteToNoteUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CalendarViewModel(private val noteRepository: INoteRepository) : ViewModel() {
    private val _dayNotesList = MutableStateFlow<List<NoteUI>>(emptyList())
    private val dayNotesList = _dayNotesList.asStateFlow()

    fun getDayNotesList() = dayNotesList

    fun loadNotesListByDay(dayStart: Long, dayFinish: Long) {
        viewModelScope.launch {
            noteRepository.getAllNotesInDay(startDay = dayStart, finishDay = dayFinish).collect { newList ->
                _dayNotesList.update {
                    newList.map {
                        NoteToNoteUiMapper.mapFrom(it)
                    }
                }
            }
        }
    }
}