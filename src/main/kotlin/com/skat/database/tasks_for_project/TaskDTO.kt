package com.skat.database.tasks_for_project

@kotlinx.serialization.Serializable
class TaskDTO(
    val id: String,
    val title: String,
    val description: String,
    val isCheck: Boolean,
    val user: String,
    val color: String,
    val mark: String
)