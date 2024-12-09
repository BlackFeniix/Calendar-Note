package com.blackhito.calendarnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.blackhito.navigation.AppNavigation
import com.blackhito.ui.theme.CalendarNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalendarNoteTheme {
                Scaffold { paddingValues ->
                    AppNavigation(
                        modifier = Modifier.padding(paddingValues),
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}