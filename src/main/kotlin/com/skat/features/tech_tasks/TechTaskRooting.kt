package com.skat.features.tech_tasks

import com.skat.features.projects.ProjectController
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.TechTaskRooting() {
    routing {
        post("/tech_task/add") {
            TechTasksController.addTechStudio(call)
            return@post
        }

        post("/tech_task/get") {
            TechTasksController.getTechTasks(call)
            return@post
        }

        post("/tech_task/delete") {
            TechTasksController.deleteTechTasks(call)
            return@post
        }
    }
}