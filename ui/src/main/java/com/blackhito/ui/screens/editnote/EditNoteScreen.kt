package com.blackhito.ui.screens.editnote

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blackhito.models.NoteUI
import com.blackhito.ui.theme.CalendarNoteTheme
import com.blackhito.ui.utils.convertMillisToDateWithHour
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
        setName = viewModel::setName)
}

@Composable
private fun NoteDetailsEditContentScreen(
    modifier: Modifier = Modifier,
    note: NoteUI,
    onClickUpdateNote: () -> Unit,
    onNavigateToNoteDetails: (Int) -> Unit,
    setName: (String) -> Unit
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
            Text(text = "Name:")
            TextField(onValueChange = setName, value = note.name)
        }

        item {
            Text(text = "Start date:")
            TextField(onValueChange = {}, value = convertMillisToDateWithHour(note.dateStart))

        }

        item {
            Text(text = "Finish date:")
            TextField(
                onValueChange = {},
                value = convertMillisToDateWithHour(note.dateFinish)
            )

        }

        item {
            Text(text = "Description:")
            TextField(onValueChange = {}, value = note.description)

        }

        item {
            Button(onClick = {
                onClickUpdateNote()
                onNavigateToNoteDetails(note.id)
            }) {
                Text(text = "Save")
            }
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
            setName = {}
        )
    }
}