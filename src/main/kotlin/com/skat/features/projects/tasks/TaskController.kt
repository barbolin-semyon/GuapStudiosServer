package com.skat.features.projects.tasks

import com.skat.database.projects.Projects
import com.skat.database.tasks_for_project.TaskDTO
import com.skat.database.tasks_for_project.Tasks
import com.skat.features.projects.ProjectUpdateReceiveModel
import com.skat.features.projects.StringResponceModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

object TaskController {

    private fun getTasksFromDB(indecies: List<String>): ListResponceModel<TaskDTO> {

        val responseTasks = mutableListOf<TaskDTO>()
        val failTasksIndex = mutableListOf<String>()

        for (taskIndex in indecies) {
            val tempTask = Tasks.fetch(taskIndex)

            if (tempTask != null) {
                responseTasks.add(tempTask)
            } else {
                failTasksIndex.add(taskIndex)
            }
        }

        return ListResponceModel(responseTasks, failTasksIndex)
    }

    suspend fun getTasks(call: ApplicationCall) {
        val receive = call.receive(ListStringReceiveModel::class)
        val result = getTasksFromDB(receive.ides)
        call.respond(HttpStatusCode.OK, result)
    }

    suspend fun addTask(call: ApplicationCall) {
        val receive = call.receive(CreateTaskReceiveModel::class)

        val project = Projects.fetchProject(receive.projectId)

        if (project == null) {
            call.respond(HttpStatusCode.BadRequest, StringResponceModel("Project not found"))
        } else {
            val id = UUID.randomUUID().toString()

            val tasks = project.tasks.toMutableList()
            tasks.add(id)

            Tasks.insert(
                TaskDTO(
                    id = id,
                    title = receive.title,
                    description = receive.description,
                    isCheck = false,
                    user = receive.user,
                    color = receive.color,
                    mark = receive.mark
                )
            )
            Projects.updateProject(
                ProjectUpdateReceiveModel(
                    id = receive.projectId,
                    tasks = tasks.toTypedArray()
                )
            )
            call.respond(HttpStatusCode.OK, StringResponceModel("OK"))
        }
    }

    suspend fun deleteTask(call: ApplicationCall) {
        val receive = call.receive(DeleteTaskReceiveModel::class)

        val project = Projects.fetchProject(receive.projectId)

        if (project == null) {
            call.respond(HttpStatusCode.BadRequest, StringResponceModel("Project not found"))
        } else {
            val tasks = project.tasks.toMutableList()
            tasks.remove(receive.taskId)

            Tasks.delete(receive.taskId)
            Projects.updateProject(
                ProjectUpdateReceiveModel(
                    id = receive.projectId,
                    tasks = tasks.toTypedArray()
                )
            )

            call.respond(HttpStatusCode.OK, StringResponceModel("OK"))
        }

    }

    suspend fun updateIsCheck(call: ApplicationCall) {
        val receive = call.receive(UpdateTaskReceiveModel::class)

        receive.apply {
            if (id != null && isCheck != null) {
                Tasks.updateIsCheck(id, isCheck)
                call.respond(HttpStatusCode.OK, StringResponceModel("OK"))
            } else {
                call.respond(HttpStatusCode.BadRequest, StringResponceModel("Not found id or isCheck"))
            }
        }

    }

}