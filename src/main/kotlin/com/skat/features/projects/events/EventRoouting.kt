package com.skat.features.projects.events

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.EventRouting() {
    routing {
        post("/project/event/add") {
            EventController.addEvent(call)
            return@post
        }

        get("/project/event/get") {
            EventController.fetchEvent(call)
            return@get
        }

        post("project/event/update") {
            EventController.updateEvent(call)
            return@post
        }

        get("/project/event/delete") {
            EventController.deleteEvent(call)
            return@get
        }
    }
}