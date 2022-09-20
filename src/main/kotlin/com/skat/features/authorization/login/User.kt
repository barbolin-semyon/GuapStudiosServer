package com.skat.features.authorization.login

@kotlinx.serialization.Serializable
data class UserModel(
    val login: String,
    val email: String,
    val username: String,
    val typeStudio: String,
    val isAdmin: Boolean,
    val score: Int
)
