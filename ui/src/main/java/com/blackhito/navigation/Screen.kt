package com.blackhito.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object NoteCalendar : Screen()

    @Serializable
    data object AddNote : Screen()

    @Serializable
    data class NoteDetails(val id: Int) : Screen()

    @Serializable
    data class EditNote(val id: Int) : Screen()
}