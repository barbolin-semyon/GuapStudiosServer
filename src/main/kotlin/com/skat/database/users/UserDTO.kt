package com.skat.database.users

@kotlinx.serialization.Serializable
class UserDTO(
    val login: String,
    val password: String,
    val email: String,
    val username: String,
    val typeStudio: String,
    val isAdmin: Boolean,
    val score: Int
)