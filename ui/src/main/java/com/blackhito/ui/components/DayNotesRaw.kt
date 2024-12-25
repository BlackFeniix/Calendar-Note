package com.blackhito.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blackhito.models.NoteUI
import com.blackhito.ui.R
import com.blackhito.ui.theme.CalendarNoteTheme
import com.blackhito.ui.utils.convertMillisToHour

@Composable
internal fun TableNoteRaw(
    modifier: Modifier = Modifier,
    onNavigateToNoteDetails: (Int) -> Unit,
    currentHour: Int,
    currentDate: Long,
    notes: List<NoteUI>
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
        TableNoteCell(
            modifier = Modifier.weight(7f),
            notes = notes,
            onNavigateToNoteDetails = onNavigateToNoteDetails,
            rangeStartTimestamp = rangeStartTimestamp,
            rangeEndTimestamp = rangeEndTimestamp
        )
    }
}

@Composable
internal fun TableNoteCell(
    modifier: Modifier = Modifier,
    notes: List<NoteUI>,
    onNavigateToNoteDetails: (Int) -> Unit,
    rangeStartTimestamp: Long,
    rangeEndTimestamp: Long
) {
    when (notes.isNotEmpty()) {
        true -> {
            LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items = notes, key = { it.id }) { note ->
                    Column(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(
                                shape = when {
                                    rangeStartTimestamp <= note.dateStart && rangeEndTimestamp <= note.dateFinish -> {
                                        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                    }

                                    rangeStartTimestamp >= note.dateStart && rangeEndTimestamp >= note.dateFinish -> {
                                        RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                                    }

                                    rangeStartTimestamp <= note.dateStart && rangeEndTimestamp >= note.dateFinish -> {
                                        RoundedCornerShape(16.dp)
                                    }

                                    else -> RoundedCornerShape(0.dp)
                                }
                            )
                            .background(color = note.color)
                            .clickable {
                                onNavigateToNoteDetails(note.id)
                            }
                            .padding(8.dp)
                    ) {
                        Text(text = note.name, overflow = TextOverflow.Ellipsis, maxLines = 2)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = convertMillisToHour(note.dateStart) + " - " + convertMillisToHour(
                                note.dateFinish
                            )
                        )
                    }
                }
            }
        }

        false -> TableNoteEmptyCell(modifier = modifier)
    }
}

@Composable
fun TableNoteEmptyCell(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(text = stringResource(R.string.no_notes))
    }
}


@Preview(showBackground = true)
@Composable
private fun TableNoteRawPreview() {
    CalendarNoteTheme {
        TableNoteRaw(
            currentHour = 0, currentDate = 0, notes = emptyList(), onNavigateToNoteDetails = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TableNoteRawPreview2() {
    CalendarNoteTheme {
        TableNoteRaw(
            currentHour = 0, currentDate = 1733389206700,
            onNavigateToNoteDetails = {},
            notes = listOf(
                NoteUI(
                    id = 0,
                    dateStart = 1733389236700,
                    dateFinish = 1733389736700,
                    name = "NOTE 1 sdsfsafasqfasf",
                    description = "Описать описание задачи в задачнике для сотрудника по задачам в отделе задач. Напомнить про это на созвоне задач",
                    color = Color.LightGray
                ),
                NoteUI(
                    id = 1,
                    dateStart = 1733389736700,
                    dateFinish = 1733399856700,
                    name = "NOTE 1",
                    description = "Описать описание задачи в задачнике для сотрудника по задачам в отделе задач. Напомнить про это на созвоне задач",
                    color = Color.Magenta
                ),
                NoteUI(
                    id = 1,
                    dateStart = 1733389736700,
                    dateFinish = 1733399856700,
                    name = "NOTE 1",
                    description = "Описать описание задачи в задачнике для сотрудника по задачам в отделе задач. Напомнить про это на созвоне задач",
                    color = Color.Cyan
                )
            ),
        )
    }
}