package com.blackhito.ui.screens.addnote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blackhito.ui.R
import com.blackhito.ui.theme.CalendarNoteTheme
import com.blackhito.ui.utils.convertMillisToDateWithHour
import com.blackhito.ui.utils.convertTimeToMillis
import com.blackhito.ui.utils.toMillis
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewNoteScreen(
    modifier: Modifier = Modifier,
    onNavigateToCalendar: () -> Unit,
    viewModel: AddNewNoteViewModel = koinViewModel()
) {
    val name by viewModel.getName().collectAsStateWithLifecycle()
    val description by viewModel.getDescription().collectAsStateWithLifecycle()

    val currentTime = LocalDateTime.now()

    val startTimePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = currentTime.hour,
        initialMinute = currentTime.minute
    )
    val finishTimePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = currentTime.hour,
        initialMinute = currentTime.minute
    )
    val startDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentTime.toMillis(),
        initialDisplayMode = DisplayMode.Input
    )
    val finishDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentTime.toMillis(),
        initialDisplayMode = DisplayMode.Input
    )

    val dateStart = startDatePickerState.selectedDateMillis?.let {
        viewModel.setDateStart(
            it,
            convertTimeToMillis(startTimePickerState.hour, startTimePickerState.minute)
        )
        it + convertTimeToMillis(startTimePickerState.hour, startTimePickerState.minute)
    } ?: 0L

    val dateFinish = finishDatePickerState.selectedDateMillis?.let {
        viewModel.setDateFinish(
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
                text = stringResource(R.string.create_note_title),
                textAlign = TextAlign.Center
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                value = name,
                onValueChange = viewModel::setName,
                placeholder = { Text(stringResource(R.string.name)) })
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = viewModel::setDescription,
                placeholder = { Text(stringResource(R.string.description)) })
        }

        item {
            Text(
                text = stringResource(
                    R.string.note_start_time, convertMillisToDateWithHour(
                        convertTimeToMillis(startTimePickerState.hour, startTimePickerState.minute)
                                + (startDatePickerState.selectedDateMillis ?: 0L)
                    )
                )
            )
            Text(text = stringResource(R.string.note_start_time, convertMillisToDateWithHour(dateStart)))
            TimeInput(state = startTimePickerState)
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    openDateStartDialog.value = true
                }) {
                Text(text = stringResource(R.string.choose_start_time))
            }
        }

        item {
            Text(
                text = stringResource(
                    R.string.note_finish_time, convertMillisToDateWithHour(
                        convertTimeToMillis(
                            finishTimePickerState.hour,
                            finishTimePickerState.minute
                        ) +
                                (finishDatePickerState.selectedDateMillis ?: 0L)
                    )
                )
            )
            Text(text = stringResource(R.string.note_finish_time, convertMillisToDateWithHour(dateFinish)))

            TimeInput(state = finishTimePickerState)
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    openDateFinishDialog.value = true
                }) {
                Text(text = stringResource(R.string.choose_finish_time))
            }
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                onClick = {
                    viewModel.createNote()
                    onNavigateToCalendar()
                }) {
                Text(text = stringResource(R.string.create_note))
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
private fun AddNewNoteScreenPreview() {
    CalendarNoteTheme {
        AddNewNoteScreen(onNavigateToCalendar = {})
    }
}