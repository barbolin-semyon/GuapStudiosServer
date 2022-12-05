package com.skat.features.studious

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.StudiousRouting() {
    routing {
        post("/studios/add") {
            StudiousController.addStudio(call)
            return@post
        }

        get("/studios/get") {
            StudiousController.fetchStudio(call)
            return@get
        }

        post("/studios/update") {
            StudiousController.updateStudio(call)
            return@post
        }

        get("/studious/names") {
            StudiousController.fetchNamesStudio(call)
            return@get
        }
    }
}