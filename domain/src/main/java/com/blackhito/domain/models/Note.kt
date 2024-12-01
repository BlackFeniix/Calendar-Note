package com.blackhito.domain.models

import java.sql.Timestamp

data class Note(
    val id: Int,
    val dateStart: Timestamp,
    val dateFinish: Timestamp,
    val name: String,
    val description: String
)