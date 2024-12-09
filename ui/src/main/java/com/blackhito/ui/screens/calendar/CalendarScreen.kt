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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blackhito.ui.components.TableNoteRaw
import com.blackhito.ui.theme.CalendarNoteTheme
import com.blackhito.ui.utils.convertMillisToDate
import com.blackhito.ui.utils.getEndOfDay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(modifier: Modifier = Modifier, onAddNewNoteClick: () -> Unit) {
    val viewModel = CalendarViewModel()
    val chosenDayNoteList by viewModel.getDayNotesList().collectAsStateWithLifecycle()

    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.also {
        viewModel.loadNotesListByDay(it, getEndOfDay(it))
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
                val taskInHour = chosenDayNoteList.firstOrNull { note ->
                    note.dateStart < hourEnd && note.dateFinish > hourStart
                }

                TableNoteRaw(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    currentHour = hour,
                    currentDate = selectedDate,
                    noteUI = taskInHour
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(16.dp),
            onClick = onAddNewNoteClick) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CalendarScreenPreview() {
    CalendarNoteTheme {
        CalendarScreen(onAddNewNoteClick = {})
    }
}