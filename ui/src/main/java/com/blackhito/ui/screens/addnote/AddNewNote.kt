package com.blackhito.ui.screens.addnote

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blackhito.ui.theme.CalendarNoteTheme
import com.blackhito.ui.utils.convertMillisToDate
import com.blackhito.ui.utils.convertMillisToDateWithHour

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewNoteScreen(modifier: Modifier = Modifier) {
    val viewModel = AddNewNoteViewModel()
    val name by viewModel.getName().collectAsStateWithLifecycle()
    val description by viewModel.getDescription().collectAsStateWithLifecycle()
    val dateStart by viewModel.getDateStart().collectAsStateWithLifecycle()
    val dateFinish by viewModel.getDateFinish().collectAsStateWithLifecycle()

    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE)
    )
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    val openDateStartDialog = remember { mutableStateOf(false) }
    val openDateFinishDialog = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        TextField(
            value = name,
            onValueChange = viewModel::setName,
            placeholder = { Text("Название") })
        TextField(
            value = description,
            onValueChange = viewModel::setDescription,
            placeholder = { Text("Описание") })

        Text(text = "Начало заметки: ${convertMillisToDateWithHour(dateStart)}")
        TimeInput(state = timePickerState)
        Button(onClick = {
            openDateStartDialog.value = true
        }) {
            Text(text = "Выбрать дату начала")
        }

        Text(text = "Конец заметки: ${convertMillisToDateWithHour(dateFinish)}")
        TimeInput(state = timePickerState)
        Button(onClick = {
            openDateFinishDialog.value = true
        }) {
            Text(text = "Выбрать дату конца")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = viewModel::createNote) {
            Text(text = "Создать заметку")
        }
    }

    if (openDateStartDialog.value) {
        DatePickerDialog(onDismissRequest = { openDateStartDialog.value = false }, confirmButton = {
            Button(onClick = {
                openDateStartDialog.value = false
                viewModel.setDateStart(datePickerState.selectedDateMillis ?: 0L)
            }
            ) { Text(text = "OK") }
        }) {
            DatePicker(state = datePickerState)
        }
    }

    if (openDateFinishDialog.value) {
        DatePickerDialog(onDismissRequest = { openDateFinishDialog.value = false }, confirmButton = {
            Button(onClick = {
                openDateFinishDialog.value = false
                viewModel.setDateFinish(datePickerState.selectedDateMillis ?: 0L)
            }
            ) { Text(text = "OK") }
        }) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerWithTimeDialog(
    modifier: Modifier = Modifier,
    onSelectedDate: (Long) -> Unit
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE)
    )
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    Column {
        DatePicker(state = datePickerState)
        TimeInput(state = timePickerState)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AddNewNoteScreenPreview() {
    CalendarNoteTheme {
        AddNewNoteScreen()
    }
}