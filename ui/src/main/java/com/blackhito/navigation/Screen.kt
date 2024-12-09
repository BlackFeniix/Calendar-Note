package com.blackhito.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object NoteCalendar : Screen()

    @Serializable
    data object AddNote : Screen()

    @Serializable
    data object NoteDetails : Screen()
}