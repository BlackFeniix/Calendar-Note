package com.blackhito.ui.screens.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blackhito.models.NoteUI
import com.blackhito.ui.components.TableNoteRaw
import com.blackhito.ui.theme.CalendarNoteTheme
import com.blackhito.ui.utils.convertMillisToDate
import com.blackhito.ui.utils.getEndOfDay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarScreen(
    modifier: Modifier = Modifier,
    onNavigateToAddNewNote: () -> Unit,
    onNavigateToNoteDetails: (Int) -> Unit,
    viewModel: CalendarViewModel = koinViewModel()
) {
    val chosenDayNoteList by viewModel.getDayNotesList().collectAsStateWithLifecycle()

    CalendarContentScreen(
        modifier = modifier,
        chosenDayNoteList = chosenDayNoteList,
        loadNotesListByDay = viewModel::loadNotesListByDay,
        onNavigateToAddNewNote = onNavigateToAddNewNote,
        onNavigateToNoteDetails = onNavigateToNoteDetails
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarContentScreen(
    modifier: Modifier = Modifier,
    chosenDayNoteList: List<NoteUI>,
    loadNotesListByDay: (Long, Long) -> Unit,
    onNavigateToAddNewNote: () -> Unit,
    onNavigateToNoteDetails: (Int) -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.also {
        loadNotesListByDay(it, getEndOfDay(it))
    } ?: 0L

    Box(modifier = modifier) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                DatePicker(state = datePickerState, title = {}, showModeToggle = false)
            }

            item {
                Text(text = convertMillisToDate(selectedDate))
            }

            items(count = 24) { hour ->
                val hourStart = selectedDate + hour * 60 * 60 * 1000
                val hourEnd = hourStart + 60 * 60 * 1000
                val tasksInHour = chosenDayNoteList.filter { note ->
                    note.dateStart < hourEnd && note.dateFinish > hourStart
                }

                TableNoteRaw(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    currentHour = hour,
                    currentDate = selectedDate,
                    notes = tasksInHour,
                    onNavigateToNoteDetails = onNavigateToNoteDetails
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(16.dp),
            onClick = onNavigateToAddNewNote
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CalendarScreenPreview() {
    CalendarNoteTheme {
        CalendarContentScreen(
            onNavigateToAddNewNote = {},
            onNavigateToNoteDetails = {},
            chosenDayNoteList = emptyList(),
            loadNotesListByDay = { l: Long, l1: Long -> },
        )
    }
}