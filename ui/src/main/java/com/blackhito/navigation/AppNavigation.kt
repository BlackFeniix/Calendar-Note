package com.blackhito.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.blackhito.ui.screens.addnote.AddNewNoteScreen
import com.blackhito.ui.screens.calendar.CalendarScreen

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
            CalendarScreen(onAddNewNoteClick = {
                navController.navigate(Screen.AddNote)
            })
        }

        composable<Screen.AddNote> {
            AddNewNoteScreen()
        }
    }
}