package com.blackhito.models

data class NoteUI(
    val id: Int = 0,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String
)