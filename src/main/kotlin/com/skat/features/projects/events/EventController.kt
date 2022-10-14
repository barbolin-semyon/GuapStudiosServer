package com.skat.features.projects.events

import com.skat.database.event_for_project.EventDTO
import com.skat.database.event_for_project.Events
import com.skat.database.projects.ProjectDTO
import com.skat.database.projects.Projects
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

object EventController {

    suspend fun addEvent(call: ApplicationCall) {
        val receiveModel = call.receive(EventReceiveModel::class)
        val id = UUID.randomUUID().toString()

        Events.insert(
            EventDTO(
                id = id,
                title = receiveModel.title,
                description = receiveModel.description,
                observers = receiveModel.observers,
                date = receiveModel.date
            )
        )

        val project = Projects.fetchProject(id)
        if (project != null) { call.respond(HttpStatusCode.OK, id)}
    }

    suspend fun updateEvent(
        call: ApplicationCall
    ) {
        val receiveModel = call.receive(EventUpdateReceiveModel::class)
        Events.updateEvent(receiveModel)
    }

    suspend fun deleteEvent(call: ApplicationCall) {
        val id = call.parameters["id"]

        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "there isn't parameter id")
        } else {
            Events.delete(id)
            call.respond(HttpStatusCode.OK, "OK")
        }
    }

    suspend fun fetchEvent(call: ApplicationCall) {
        val id = call.parameters["id"]

        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "there isn't parametr get")
        } else {
            val event = Events.fetch(id)

            if (event == null) {
                call.respond(HttpStatusCode.BadRequest, "not found project")
            } else {
                call.respond(HttpStatusCode.OK, event)
            }
        }
    }
}