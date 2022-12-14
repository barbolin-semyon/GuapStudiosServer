package com.skat.features.projects.tasks

import com.skat.database.tasks_for_project.TaskDTO

@kotlinx.serialization.Serializable
data class ListStringReceiveModel(
    val ides: List<String>
)

@kotlinx.serialization.Serializable
data class CreateTaskReceiveModel(
    val projectId: String,
    val title: String,
    val description: String,
    val user: String,
    val color: String,
    val mark: String,
)

@kotlinx.serialization.Serializable
data class DeleteTaskReceiveModel(
    val projectId: String,
    val taskId: String,
)

@kotlinx.serialization.Serializable
data class UpdateTaskReceiveModel(
    val id: String?,
    val isCheck: Boolean?
)

@kotlinx.serialization.Serializable
data class ListResponceModel<T>(
    val tasks: List<T>,
    val failTasksIndex: List<String>
)
