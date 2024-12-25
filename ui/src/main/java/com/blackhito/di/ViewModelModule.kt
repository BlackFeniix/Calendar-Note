package com.blackhito.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.blackhito.ui.screens.addnote.AddNewNoteViewModel
import com.blackhito.ui.screens.calendar.CalendarViewModel
import com.blackhito.ui.screens.notedetails.NoteDetailsViewModel
import com.blackhito.ui.screens.editnote.EditNoteViewModel

val viewModelModule = module {
    viewModelOf(::CalendarViewModel)
    viewModelOf(::AddNewNoteViewModel)
    viewModelOf(::NoteDetailsViewModel)
    viewModelOf(::EditNoteViewModel)
}