package com.skat.features.projects.tasks

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.TaskRooting() {
    routing {
        post("project/tasks") {
            TaskController.getTasks(call)
            return@post
        }

        post("project/tasks/add") {
            TaskController.addTask(call)
            return@post
        }

        post("project/tasks/delete") {
            TaskController.deleteTask(call)
            return@post
        }

        post ("/project/tasks/update" ) {
            TaskController.updateIsCheck(call)
            return@post
        }
    }
}