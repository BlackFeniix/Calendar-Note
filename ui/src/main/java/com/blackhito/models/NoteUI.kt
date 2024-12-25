package com.blackhito.models

import androidx.compose.ui.graphics.Color

internal data class NoteUI(
    val id: Int = 0,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String,
    val color: Color = Color.LightGray
)