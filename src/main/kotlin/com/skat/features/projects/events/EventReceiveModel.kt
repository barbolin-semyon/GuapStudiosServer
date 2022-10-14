package com.skat.features.projects.events

import kotlinx.serialization.Serializable

@Serializable
data class EventReceiveModel(
    val adminId: String,
    val title: String,
    val description: String,
)

@Serializable
data class EventUpdateReceiveModel(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val observers: Array<String>? = null,
    val date: String? = null,
)