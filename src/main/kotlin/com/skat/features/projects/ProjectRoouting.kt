package com.skat.features.projects

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.ProjectRouting() {
    routing {
        post("/project/add") {
            ProjectController.addProject(call)
            return@post
        }

        post("/project/get") {
            ProjectController.getProjects(call)
            return@post
        }

        post("project/update") {
            ProjectController.updateProject(call)
            return@post
        }

        post("/project/delete") {
            ProjectController.deleteProject(call)
            return@post
        }
    }
}