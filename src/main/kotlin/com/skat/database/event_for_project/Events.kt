package com.skat.database.event_for_project

import array
import com.skat.database.projects.Projects
import com.skat.features.projects.ProjectUpdateReceiveModel
import com.skat.features.projects.events.EventUpdateReceiveModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.beans.EventSetDescriptor

object Events : Table("events") {
    val id = Events.varchar("id", 50)
    val title = Events.varchar("title", 25)
    val description = Events.varchar("description", 70)
    val date = Events.varchar("date", 30)
    val observers = Events.array<String>("observers", VarCharColumnType())

    fun insert(events: EventDTO) {
        transaction {
            Events.insert {
                it[id] = events.id
                it[title] = events.title
                it[description] = events.description
                it[date] = events.date
                it[observers] = events.observers
            }
        }
    }

    fun updateEvent(
        eventUpdateReceiveModel: EventUpdateReceiveModel
    ) {
        transaction {
            Projects.update({ Events.id.eq(eventUpdateReceiveModel.id) }) {
                if (eventUpdateReceiveModel.date != null) {
                    it[date] = eventUpdateReceiveModel.date
                }

                if (eventUpdateReceiveModel.title != null) {
                    it[title] = eventUpdateReceiveModel.title
                }

                if (eventUpdateReceiveModel.description != null) {
                    it[description] = eventUpdateReceiveModel.description
                }

                if (eventUpdateReceiveModel.observers != null) {
                    it[observers] = eventUpdateReceiveModel.observers
                }
            }
        }
    }

    fun fetch(id: String): EventDTO? {
        return try {
            transaction {
                val model = Events.select { Events.id.eq(id) }.single()


                EventDTO(
                    id = model[Events.id],
                    title = model[title],
                    description = model[description],
                    observers = model[observers],
                    date =  model[date]

                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun delete(id: String) {
        transaction {
            Events.deleteWhere {
                Events.id.eq(id)
            }
        }
    }
}