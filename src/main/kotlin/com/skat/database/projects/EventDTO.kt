package com.skat.database.projects

import java.util.Date

@kotlinx.serialization.Serializable
class EventDTO(
    val id: String,
    val title: String,
    val description: String,
    val date: String
)