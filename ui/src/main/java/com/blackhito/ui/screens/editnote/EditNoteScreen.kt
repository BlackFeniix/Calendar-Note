package com.blackhito.ui.screens.editnote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blackhito.models.NoteUI
import com.blackhito.ui.R
import com.blackhito.ui.theme.CalendarNoteTheme
import com.blackhito.ui.utils.convertMillisToDateTime
import com.blackhito.ui.utils.convertMillisToDateWithHour
import com.blackhito.ui.utils.convertTimeToMillis
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun EditNoteScreen(
    modifier: Modifier = Modifier,
    onNavigateToNoteDetails: (Int) -> Unit,
    viewModel: EditNoteViewModel = koinViewModel()
) {
    val note by viewModel.getNoteState().collectAsStateWithLifecycle()

    NoteDetailsEditContentScreen(
        modifier = modifier,
        note = note,
        onClickUpdateNote = viewModel::updateNote,
        onNavigateToNoteDetails = onNavigateToNoteDetails,
        setName = viewModel::setName,
        setDescription = viewModel::setDescription,
        setStartTime = viewModel::setDateStart,
        setFinishTime = viewModel::setDateFinish
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteDetailsEditContentScreen(
    modifier: Modifier = Modifier,
    note: NoteUI,
    onClickUpdateNote: () -> Unit,
    onNavigateToNoteDetails: (Int) -> Unit,
    setName: (String) -> Unit,
    setDescription: (String) -> Unit,
    setStartTime: (Long, Long) -> Unit,
    setFinishTime: (Long, Long) -> Unit
) {
    val startTime = convertMillisToDateTime(note.dateStart)
    val finishTime = convertMillisToDateTime(note.dateFinish)

    val startTimePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = startTime.hour,
        initialMinute = startTime.minute
    )
    val finishTimePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = finishTime.hour,
        initialMinute = finishTime.minute
    )
    val startDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = note.dateStart,
        initialDisplayMode = DisplayMode.Picker
    )
    val finishDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = note.dateFinish,
        initialDisplayMode = DisplayMode.Picker
    )

    val dateStart = startDatePickerState.selectedDateMillis?.let {
        setStartTime(
            it,
            convertTimeToMillis(startTimePickerState.hour, startTimePickerState.minute)
        )
        it + convertTimeToMillis(startTimePickerState.hour, startTimePickerState.minute)
    } ?: 0L

    val dateFinish = finishDatePickerState.selectedDateMillis?.let {
        setFinishTime(
            it,
            convertTimeToMillis(finishTimePickerState.hour, finishTimePickerState.minute)
        )
        it + convertTimeToMillis(finishTimePickerState.hour, finishTimePickerState.minute)
    } ?: 0L

    val openDateStartDialog = remember { mutableStateOf(false) }
    val openDateFinishDialog = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.note_title),
                textAlign = TextAlign.Center
            )
        }

        item {
            Text(text = stringResource(R.string.name))
            OutlinedTextField(
                modifier = Modifier.fillParentMaxWidth(),
                maxLines = 1,
                onValueChange = setName,
                value = note.name
            )
        }

        item {
            Text(text = stringResource(R.string.note_start_time, convertMillisToDateWithHour(note.dateStart)))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { openDateStartDialog.value = true }) {
                Text(text = stringResource(R.string.change_start_time))
            }
        }

        item {
            Text(text = stringResource(R.string.note_finish_time, convertMillisToDateWithHour(note.dateFinish)))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { openDateFinishDialog.value = true }) {
                Text(text = stringResource(R.string.change_finish_time))
            }
        }

        item {
            Text(text = stringResource(R.string.description))
            OutlinedTextField(
                modifier = Modifier.fillParentMaxWidth(),
                onValueChange = setDescription,
                value = note.description
            )
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onClickUpdateNote()
                    onNavigateToNoteDetails(note.id)
                }) {
                Text(text = stringResource(R.string.save_note))
            }
        }
    }

    if (openDateStartDialog.value) {
        DatePickerDialog(onDismissRequest = { openDateStartDialog.value = false }, confirmButton = {
            Button(onClick = {
                openDateStartDialog.value = false
            }
            ) { Text(text = stringResource(R.string.ok)) }
        }) {
            DatePicker(state = startDatePickerState, showModeToggle = true)
        }
    }

    if (openDateFinishDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openDateFinishDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    openDateFinishDialog.value = false
                }
                ) { Text(text = stringResource(R.string.ok)) }
            }) {
            DatePicker(state = finishDatePickerState, showModeToggle = true)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun EditNoteScreenPreview() {
    CalendarNoteTheme {
        NoteDetailsEditContentScreen(
            note = NoteUI(
                id = 0,
                dateStart = 0,
                dateFinish = 0,
                name = "Pos1",
                description = "Pos2",
            ),
            onClickUpdateNote = {},
            onNavigateToNoteDetails = {},
            setName = {},
            setDescription = {},
            setStartTime = { l, l1 -> },
            setFinishTime = { l, l1 -> },
        )
    }
}