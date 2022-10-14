package com.skat.database.event_for_project

import java.util.Date

@kotlinx.serialization.Serializable
class EventDTO(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val observers: Array<String>
)