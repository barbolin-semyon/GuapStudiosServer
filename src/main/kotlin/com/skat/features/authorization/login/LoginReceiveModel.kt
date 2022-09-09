package com.skat.features.authorization.login

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