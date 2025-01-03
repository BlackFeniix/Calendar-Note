package com.blackhito.data.di

import androidx.room.Room
import com.blackhito.data.database.NoteDao
import com.blackhito.data.database.NoteDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single<NoteDatabase>{
        Room.databaseBuilder(androidApplication(), NoteDatabase::class.java, "note_database").build()
    }

    single<NoteDao> {
        get<NoteDatabase>().noteDao()
    }
}