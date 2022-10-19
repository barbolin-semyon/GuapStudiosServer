package com.skat.features.projects.events

import com.skat.database.event_for_project.EventDTO
import com.skat.database.event_for_project.Events
import com.skat.database.projects.Projects
import com.skat.features.projects.tasks.ListResponceModel
import com.skat.features.projects.tasks.ListStringReceiveModel
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

    private fun getEventFromDB(indecies: List<String>): ListResponceModel<EventDTO> {

        val responseEvent = mutableListOf<EventDTO>()
        val failElementIndex = mutableListOf<String>()

        for (index in indecies) {
            val tempEvent = Events.fetch(index)

            if (tempEvent != null) {
                responseEvent.add(tempEvent)
            } else {
                failElementIndex.add(index)
            }
        }

        return ListResponceModel(responseEvent, failElementIndex)
    }

    suspend fun getEvents(call: ApplicationCall) {
        val receive = call.receive(ListStringReceiveModel::class)
        val result = getEventFromDB(receive.ides)
        call.respond(HttpStatusCode.OK, result)
    }

}