package com.skat.database.projects

import array
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Projects : Table("project") {
    private val id = Projects.varchar("id", 50)
    private val adminId = Projects.varchar("adminId", 25)
    private val title = Projects.varchar("title", 25)
    private val description = Projects.varchar("description", 90)
    private val tasks = Projects.array<String>("tasks", VarCharColumnType())
    private val users = Projects.array<String>("users", VarCharColumnType())
    private val events = Projects.array<String>("events", VarCharColumnType())

    fun insert(projectDTO: ProjectDTO) {
        transaction {
            Projects.insert {
                it[id] = projectDTO.id
                it[adminId] = projectDTO.adminId!!
                it[title] = projectDTO.title!!
                it[description] = projectDTO.description!!
                it[tasks] = projectDTO.tasks
                it[users] = projectDTO.users
                it[events] = projectDTO.events
            }
        }
    }
    fun updateProject(projectDTO: ProjectDTO) {
        transaction {
            Projects.update {
                if (projectDTO.adminId != null) {
                    it[adminId] = projectDTO.adminId
                }

                if (projectDTO.title != null) {
                    it[title] = projectDTO.title
                }

                if (projectDTO.description != null) {
                    it[description] = projectDTO.description
                }

                if (projectDTO.tasks.isNotEmpty()) {
                    it[tasks] = projectDTO.tasks
                }

                if (projectDTO.users.isNotEmpty()) {
                    it[users] = projectDTO.users
                }

                if (projectDTO.events.isNotEmpty()) {
                    it[events] = projectDTO.events
                }
            }
        }
    }

    fun fetchProject(id: String): ProjectDTO? {
        return try {
            transaction {
                val model = Projects.select { Projects.id.eq(id) }.single()


                ProjectDTO(
                    id = model[Projects.id],
                    adminId = model[adminId],
                    title = model[title],
                    description = model[description],
                    tasks = model[tasks],
                    users = model[users],
                    events = model[events],
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}