package com.skat.database.tech_task

import java.util.UUID

@kotlinx.serialization.Serializable
data class TechTasksDTO(
    val costumer: String,
    val id: String = UUID.randomUUID().toString(),
    val studio: String,
    val title: String,
    val description: String,
    val countPeople: Int,
    val isTake: Boolean,
    val place: String,
    val date: String
)
