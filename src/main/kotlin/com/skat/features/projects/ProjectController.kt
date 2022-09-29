package com.skat.features.projects

import com.skat.database.projects.ProjectDTO
import com.skat.database.projects.Projects
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

object ProjectController {

    suspend fun addProject(call: ApplicationCall) {
        val receiveModel = call.receive(ProjectReceiveModel::class)

        Projects.insert(
            ProjectDTO(
                adminId = receiveModel.adminId,
                title = receiveModel.title,
                description = receiveModel.description,
            )
        )
        call.respond(HttpStatusCode.OK, "OK")


    }

    suspend fun fetchProject(call: ApplicationCall) {
        val id = call.parameters["id"]

        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "there isn't parametr get")
        } else {
            val project = Projects.fetchProject(id)

            if (project == null) {
                call.respond(HttpStatusCode.BadRequest, "not found project")
            } else {
                call.respond(HttpStatusCode.OK, project)
            }
        }
    }
}