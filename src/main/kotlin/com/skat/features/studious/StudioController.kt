package com.skat.features.studious

import com.skat.database.studious.Studious
import com.skat.database.studious.StudiousDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

object StudiousController {

    suspend fun fetchNamesStudio(call: ApplicationCall) {
        call.respond(listOf(
            "event",
            "tech",
            "dance",
            "music",
        ))
    }

    suspend fun addStudio(call: ApplicationCall) {
        val receiveModel = call.receive(StudiousDTO::class)
        Studious.insert(receiveModel)
        call.respond(HttpStatusCode.OK, "OK")
    }

    suspend fun updateStudio(
        call: ApplicationCall
    ) {
        val receiveModel = call.receive(StudioUpdateReceiveModel::class)
        Studious.updateStudio(receiveModel)
    }

    suspend fun fetchStudio(call: ApplicationCall) {
        val id = call.parameters["name"]

        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "there isn't parameter get")
        } else {
            val studio = Studious.fetchStudio(id)

            if (studio == null) {
                call.respond(HttpStatusCode.BadRequest, "not found studio")
            } else {
                call.respond(HttpStatusCode.OK, studio)
            }
        }
    }
}