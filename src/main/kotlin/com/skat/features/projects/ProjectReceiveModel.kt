package com.skat.features.projects

import kotlinx.serialization.Serializable

@Serializable
data class ProjectReceiveModel(
    val adminId: String,
    val title: String,
    val description: String,
)
