package com.skat.database.studious

@kotlinx.serialization.Serializable
class StudiousDTO(
    val id: String,
    val users: Array<String>,
    val projects: Array<String>,
    val tech_task: Array<String>,
)