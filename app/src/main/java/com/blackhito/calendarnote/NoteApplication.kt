package com.blackhito.calendarnote

import android.app.Application
import com.blackhito.data.di.databaseModule
import com.blackhito.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NoteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NoteApplication)
            modules(viewModelModule, databaseModule)
        }
    }
}