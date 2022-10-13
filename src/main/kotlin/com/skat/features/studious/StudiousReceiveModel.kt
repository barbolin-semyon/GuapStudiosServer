package com.skat.features.studious

import kotlinx.serialization.Serializable

@Serializable
data class StudioUpdateReceiveModel(
    val name: String,
    val tech_task: Array<String>? = null,
    val project: Array<String>? = null,
    val users: Array<String>? = null,
)
