package com.skat.features.projects.tasks

import com.skat.database.tasks.TaskDTO

@kotlinx.serialization.Serializable
data class ListTasksReceiveModel(
    val tasks: List<String>
)

@kotlinx.serialization.Serializable
data class CreateTaskReceiveModel(
    val projectId: String,
    val task: TaskDTO
)

@kotlinx.serialization.Serializable
data class DeleteTaskReceiveModel(
    val projectId: String,
    val taskId: String,
)

@kotlinx.serialization.Serializable
data class ListTaskResponceModel(
    val tasks: List<TaskDTO>,
    val failTasksIndex: List<String>
)
