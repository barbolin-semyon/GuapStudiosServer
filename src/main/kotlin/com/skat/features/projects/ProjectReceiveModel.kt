package com.skat.features.projects

import kotlinx.serialization.Serializable

@Serializable
data class ProjectReceiveModel(
    val studio: String,
    val adminId: String,
    val title: String,
    val description: String,
)

@Serializable
data class ProjectUpdateReceiveModel(
    val id: String,
    val adminId: String? = null,
    val title: String? = null,
    val tasks: Array<String>? = null,
    val description: String? = null,
    val events: Array<String>? = null,
    val users: Array<String>? = null,
)

@Serializable
data class ProjectDeleteReceiveModel(
    val id: String,
    val studioId: String
)