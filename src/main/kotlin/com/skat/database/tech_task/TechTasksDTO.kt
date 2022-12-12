package com.skat.database.tech_task

data class TechTasksDTO(
    val costumer: String,
    val id: String,
    val studio: String,
    val title: String,
    val description: String,
    val countPeople: Int,
    val isTake: Boolean,
    val place: String,
    val date: String
)
