package com.skat.features.register

import com.skat.database.users.Users
import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val login: String,
    val password: String,
    val username: String,
    val email: String,
    val typeStudio: String,
    val score: Int,
)