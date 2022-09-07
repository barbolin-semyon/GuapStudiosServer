package com.skat.features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceiveModel(
    val login: String,
    val password: String
)

@Serializable
data class TokenModel(
    val token: String
)