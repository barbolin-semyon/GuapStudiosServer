package com.skat.database.projects

import com.skat.database.users.UserDTO
import java.util.UUID

@kotlinx.serialization.Serializable
class ProjectDTO(
    val adminId: String? = null,
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val tasks: Array<String> = arrayOf(),
    val events: Array<String> = arrayOf(),
    val users: Array<String> = arrayOf(),
)