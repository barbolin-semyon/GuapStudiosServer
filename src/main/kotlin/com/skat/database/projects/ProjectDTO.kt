package com.skat.database.projects

import com.skat.database.users.UserDTO
import java.util.UUID

@kotlinx.serialization.Serializable
class ProjectDTO(
    val adminId: String,
    val id: String,
    val title: String,
    val description: String,
    val tasks: Array<String> = arrayOf(),
    val events: Array<String> = arrayOf(),
    val users: Array<String> = arrayOf(),
)