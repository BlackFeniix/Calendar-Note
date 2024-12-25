package com.blackhito.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.blackhito.ui.screens.addnote.AddNewNoteScreen
import com.blackhito.ui.screens.calendar.CalendarScreen
import com.blackhito.ui.screens.editnote.EditNoteScreen
import com.blackhito.ui.screens.notedetails.NoteDetailsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.NoteCalendar
    ) {
        composable<Screen.NoteCalendar> {
            CalendarScreen(
                onNavigateToAddNewNote = {
                    navController.navigate(Screen.AddNote)
                },
                onNavigateToNoteDetails = { id ->
                    navController.navigate(Screen.NoteDetails(id))
                }
            )
        }

        composable<Screen.AddNote> {
            AddNewNoteScreen(
                onNavigateToCalendar = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.NoteDetails> {
            NoteDetailsScreen(
                onNavigateToEditNote = { noteId ->
                    navController.navigate(Screen.EditNote(noteId))
                }
            )
        }

        composable<Screen.EditNote> {
            EditNoteScreen(onNavigateToNoteDetails = { noteId ->
                navController.navigate(Screen.NoteDetails(noteId)) {
                    popUpTo(Screen.NoteDetails(noteId)) {
                        inclusive = true
                    }
                }
            })
        }
    }
}