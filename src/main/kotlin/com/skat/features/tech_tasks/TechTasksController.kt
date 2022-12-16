package com.skat.features.tech_tasks

import com.skat.database.projects.ProjectDTO
import com.skat.database.projects.Projects
import com.skat.database.studious.Studious
import com.skat.database.tech_task.TechTaskUpdateExecutor
import com.skat.database.tech_task.TechTaskUpdateIsTake
import com.skat.database.tech_task.TechTasks
import com.skat.database.tech_task.TechTasksDTO
import com.skat.features.projects.ProjectDeleteReceiveModel
import com.skat.features.projects.ProjectReceiveModel
import com.skat.features.projects.StringResponceModel
import com.skat.features.projects.tasks.ListResponceModel
import com.skat.features.projects.tasks.ListStringReceiveModel
import com.skat.features.studious.StudioUpdateReceiveModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

object TechTasksController {

    suspend fun addTechStudio(call: ApplicationCall) {
        val receiveModel = call.receive(TechTasksDTO::class)

        TechTasks.insert(receiveModel)

        val techTasks = Studious.fetchStudio(receiveModel.studio)!!.tech_task.toMutableList()
        techTasks.add(receiveModel.id)
        Studious.updateStudio(
            StudioUpdateReceiveModel(
                id = receiveModel.studio,
                tech_task = techTasks.toTypedArray()
            )
        )

        call.respond(StringResponceModel(receiveModel.id))
    }

    suspend fun updateCostumer(call: ApplicationCall) {
        val receiveModel = call.receive(TechTaskUpdateExecutor::class)
        TechTasks.updateExecutor(receiveModel)
    }

    suspend fun deleteTechTasks(call: ApplicationCall) {
        val receive = call.receive(ProjectDeleteReceiveModel::class)

        val techTask = TechTasks.fetch(receive.id)

        if (techTask == null) {
            call.respond(HttpStatusCode.BadRequest, "there isn't parameter id")
        } else {
            TechTasks.delete(receive.id)

            val listTechTasksInStudious = Studious.fetchStudio(receive.studioId)?.tech_task.orEmpty().toMutableList()
            listTechTasksInStudious.remove(receive.id)
            Studious.updateStudio(
                StudioUpdateReceiveModel(
                    id = receive.studioId,
                    tech_task = listTechTasksInStudious.toTypedArray()
                )
            )

            call.respond(HttpStatusCode.OK, "OK")
        }
    }

    private fun getTechTasksFromDB(indecies: List<String>): ListResponceModel<TechTasksDTO> {

        val response = mutableListOf<TechTasksDTO>()
        val failElementIndex = mutableListOf<String>()

        for (index in indecies) {
            val tempEvent = TechTasks.fetch(index)

            if (tempEvent != null) {
                response.add(tempEvent)
            } else {
                failElementIndex.add(index)
            }
        }

        return ListResponceModel(response, failElementIndex)
    }

    suspend fun getTechTasks(call: ApplicationCall) {
        val receive = call.receive(ListStringReceiveModel::class)
        val result = getTechTasksFromDB(receive.ides)
        call.respond(HttpStatusCode.OK, result)
    }
}