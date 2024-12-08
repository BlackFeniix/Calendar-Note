package com.blackhito.calendarnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.blackhito.ui.screens.calendar.CalendarScreen
import com.blackhito.ui.theme.CalendarNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalendarNoteTheme {
                CalendarScreen()
            }
        }
    }
}