package com.skat.features.projects.events

import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class EventReceiveModel(
    val title: String,
    val description: String,
    val date: String,
    val observers: Array<String>
)

@Serializable
data class EventUpdateReceiveModel(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val observers: Array<String>? = null,
    val date: String? = null,
)