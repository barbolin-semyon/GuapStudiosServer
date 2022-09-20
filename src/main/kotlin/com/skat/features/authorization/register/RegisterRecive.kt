package com.skat.features.authorization.register

import com.skat.database.users.Users
import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val login: String,
    val password: String,
    val email: String,
    val username: String,
    val typeStudio: String,
    val isAdmin: Boolean
)