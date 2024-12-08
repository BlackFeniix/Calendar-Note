package com.blackhito.ui.screens.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import com.blackhito.models.NoteUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalendarViewModel : ViewModel() {
    private val _dayNotesList = MutableStateFlow<List<NoteUI>>(emptyList())
    private val dayNotesList = _dayNotesList.asStateFlow()

    fun getDayNotesList() = dayNotesList

    fun loadNotesListByDay(dayStart: Long, dayFinish: Long) {
        Log.e("LOG", "$dayStart $dayFinish")
        if (dayStart == 1733356800000) {
            _dayNotesList.value = notesList
        } else {
            _dayNotesList.value = emptyList()
        }
    }
}

private val dayList = listOf(
    1733356800000,
    1733443200000,
    1733529600000,
    1733616000000
)

private val notesList = listOf(
    // 5 число
    NoteUI(
        id = 0,
        dateStart = 1733389236700,
        dateFinish = 1733389736700,
        name = "NOTE 1",
        description = "Description 1"
    ),
)