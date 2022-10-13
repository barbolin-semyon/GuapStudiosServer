package com.skat.database.studious

import java.util.Arrays

@kotlinx.serialization.Serializable
class StudiousDTO(
    val name: String,
    val users: Array<String>,
    val projects: Array<String>,
    val tech_task: Array<String>,


)