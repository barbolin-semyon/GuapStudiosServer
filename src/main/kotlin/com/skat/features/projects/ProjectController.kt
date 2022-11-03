package com.skat.features.projects

import com.skat.database.event_for_project.EventDTO
import com.skat.database.event_for_project.Events
import com.skat.database.projects.ProjectDTO
import com.skat.database.projects.Projects
import com.skat.database.studious.Studious
import com.skat.features.projects.tasks.ListResponceModel
import com.skat.features.projects.tasks.ListStringReceiveModel
import com.skat.features.studious.StudioUpdateReceiveModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

object ProjectController {

    suspend fun addProject(call: ApplicationCall) {
        val receiveModel = call.receive(ProjectReceiveModel::class)
        val id = UUID.randomUUID().toString()

        Projects.insert(

            ProjectDTO(
                id = id,
                adminId = receiveModel.adminId,
                title = receiveModel.title,
                description = receiveModel.description,
            )
        )

        val projects = Studious.fetchStudio(receiveModel.studio)!!.projects.toMutableList()
        projects.add(id)
        Studious.updateStudio(StudioUpdateReceiveModel(
            name = receiveModel.studio,
            project = projects.toTypedArray()
        ))

        val project = Projects.fetchProject(id)
        if (project != null) { call.respond(HttpStatusCode.OK, id)}
    }

    suspend fun updateProject(
        call: ApplicationCall
    ) {
        val receiveModel = call.receive(ProjectUpdateReceiveModel::class)
        Projects.updateProject(receiveModel)
    }

    suspend fun deleteProject(call: ApplicationCall) {
        val id = call.parameters["id"]

        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "there isn't parameter id")
        } else {
            Projects.deleteProject(id)
            call.respond(HttpStatusCode.OK, "OK")
        }
    }

    private fun getProjectFromDB(indecies: List<String>): ListResponceModel<ProjectDTO> {

        val responseProject = mutableListOf<ProjectDTO>()
        val failElementIndex = mutableListOf<String>()

        for (index in indecies) {
            val tempEvent = Projects.fetchProject(index)

            if (tempEvent != null) {
                responseProject.add(tempEvent)
            } else {
                failElementIndex.add(index)
            }
        }

        return ListResponceModel(responseProject, failElementIndex)
    }

    suspend fun getProjects(call: ApplicationCall) {
        val receive = call.receive(ListStringReceiveModel::class)
        val result = getProjectFromDB(receive.ides)
        call.respond(HttpStatusCode.OK, result)
    }
}