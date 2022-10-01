package com.skat.database.projects

@kotlinx.serialization.Serializable
class TaskDTO(
    val id: String,
    val title: String,
    val description: String,
    val isCheck: Boolean
)