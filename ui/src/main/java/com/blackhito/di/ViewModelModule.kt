package com.blackhito.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.blackhito.ui.screens.addnote.AddNewNoteViewModel
import com.blackhito.ui.screens.calendar.CalendarViewModel

val viewModelModule = module {
    viewModelOf(::CalendarViewModel)
    viewModelOf(::AddNewNoteViewModel)
}