package com.skat.features.projects

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.ProjectRouting() {
    routing {
        post("/project/add") {
            ProjectController.addProject(call)
            return@post
        }

        get("/project/get") {
            ProjectController.fetchProject(call)
            return@get
        }
    }
}