package com.skat.features.projects.tasks

import com.skat.database.projects.Projects
import com.skat.database.tasks_for_project.TaskDTO
import com.skat.database.tasks_for_project.Tasks
import com.skat.features.projects.ProjectUpdateReceiveModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

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
            call.respond(HttpStatusCode.BadRequest, "Project not found")
        } else {
            val tasks = project.tasks.toMutableList()
            tasks.add(receive.task.id)

            Tasks.insert(receive.task)
            Projects.updateProject(
                ProjectUpdateReceiveModel(
                    id = receive.projectId,
                    tasks = tasks.toTypedArray()
                )
            )
            call.respond(HttpStatusCode.OK, "OK")
        }
    }

    suspend fun deleteTask(call: ApplicationCall) {
        val receive = call.receive(DeleteTaskReceiveModel::class)

        val project = Projects.fetchProject(receive.projectId)

        if (project == null) {
            call.respond(HttpStatusCode.BadRequest, "Project not found")
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

            call.respond(HttpStatusCode.OK, "OK")
        }

    }

    suspend fun updateIsCheck(call: ApplicationCall) {
        val receive = call.receive(UpdateTaskReceiveModel::class)

        receive.apply {
            if (id != null && isCheck != null) {
                Tasks.updateIsCheck(id, isCheck)
                call.respond(HttpStatusCode.OK, "OK")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Not found id or isCheck")
            }
        }

    }

}