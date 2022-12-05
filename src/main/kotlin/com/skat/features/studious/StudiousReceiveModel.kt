package com.skat.features.studious

import kotlinx.serialization.Serializable

@Serializable
data class StudioUpdateReceiveModel(
    val id: String,
    val tech_task: Array<String>? = null,
    val projects: Array<String>? = null,
    val users: Array<String>? = null,
)
