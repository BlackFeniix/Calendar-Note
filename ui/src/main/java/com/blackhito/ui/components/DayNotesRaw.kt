package com.blackhito.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.blackhito.models.NoteUI
import com.blackhito.ui.theme.CalendarNoteTheme
import com.blackhito.ui.utils.convertMillisToHour

@Composable
fun TableNoteRaw(
    modifier: Modifier = Modifier,
    currentHour: Int,
    currentDate: Long,
    noteUI: NoteUI?
) {
    val rangeStartTimestamp = currentDate + currentHour * 60 * 60 * 1000
    val rangeEndTimestamp = rangeStartTimestamp + 60 * 60 * 1000

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(3f),
            text = "${convertMillisToHour(rangeStartTimestamp)}-${
                convertMillisToHour(
                    rangeEndTimestamp
                )
            }"
        )
        TableNoteCell(modifier = Modifier.weight(7f), noteUI = noteUI)
    }
}

@Composable
fun TableNoteCell(modifier: Modifier = Modifier, noteUI: NoteUI?) {
    val boxText = when (noteUI == null) {
        true -> "No notes"
        false -> noteUI.name + " " + noteUI.description
    }
    Box(modifier = modifier) {
        Text(text = boxText)
    }
}

@Preview(showBackground = true)
@Composable
private fun TableNoteRawPreview() {
    CalendarNoteTheme {
        TableNoteRaw(
            currentHour = 0, currentDate = 0, noteUI = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TableNoteRawPreview2() {
    CalendarNoteTheme {
        TableNoteRaw(
            currentHour = 0, currentDate = 0,
            noteUI = NoteUI(
                id = 0,
                dateStart = 1733389236700,
                dateFinish = 1733389736700,
                name = "NOTE 1",
                description = "Описать описание задачи в задачнике для сотрудника по задачам в отделе задач. Напомнить про это на созвоне задач"
            ),
        )
    }
}