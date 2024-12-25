package com.blackhito.ui.screens.notedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blackhito.models.NoteUI
import com.blackhito.ui.theme.CalendarNoteTheme
import com.blackhito.ui.utils.convertMillisToDateWithHour
import com.blackhito.ui.utils.convertMillisToHour
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NoteDetailsScreen(
    modifier: Modifier = Modifier,
    onNavigateToEditNote: (Int) -> Unit,
    viewModel: NoteDetailsViewModel = koinViewModel()
) {
    val state by viewModel.getNoteState().collectAsStateWithLifecycle()

    when (state) {
        is NoteDetailsState.Content -> NoteDetailsContentScreen(
            modifier = modifier,
            state = state as NoteDetailsState.Content,
            onNavigateToEditNote = onNavigateToEditNote
        )

        is NoteDetailsState.Loading -> {
            Box(modifier = modifier) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun NoteDetailsContentScreen(
    modifier: Modifier = Modifier,
    state: NoteDetailsState.Content,
    onNavigateToEditNote: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Note Card",
                textAlign = TextAlign.Center
            )
        }

        item {
            Text(text = "Name: ${state.note.name}")
        }

        item {
            Text(text = "Start: ${convertMillisToDateWithHour(state.note.dateStart)}")
        }

        item {
            Text(text = "Finish: ${convertMillisToDateWithHour(state.note.dateFinish)}")
        }

        item {
            Text(text = "Description: ${state.note.description}")
        }

        item {
            Button(onClick = { onNavigateToEditNote(state.note.id) }) {
                Text(text = "Edit note")
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun NoteDetailsScreenPreview() {
    CalendarNoteTheme {
        NoteDetailsContentScreen(
            state = NoteDetailsState.Content(
                note = NoteUI(
                    id = 0,
                    dateStart = 0,
                    dateFinish = 0,
                    name = "Pos1",
                    description = "Pos2",
                    color = Color.LightGray
                )
            ),
            onNavigateToEditNote = {}
        )
    }
}