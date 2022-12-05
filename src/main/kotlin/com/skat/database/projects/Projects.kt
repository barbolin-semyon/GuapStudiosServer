package com.skat.database.projects

import array
import com.skat.database.users.UserDTO
import com.skat.features.projects.ProjectUpdateReceiveModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Projects : Table("project") {
    private val id = Projects.varchar("id", 50)
    private val studio = Projects.varchar("id", 50)
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
                it[studio] = projectDTO.studio
                it[adminId] = projectDTO.adminId!!
                it[title] = projectDTO.title!!
                it[description] = projectDTO.description!!
                it[tasks] = projectDTO.tasks
                it[users] = projectDTO.users
                it[events] = projectDTO.events
            }
        }
    }
    fun updateProject(
        projectUpdateReceiveModel: ProjectUpdateReceiveModel
    ) {
        transaction {
            Projects.update({ Projects.id.eq(projectUpdateReceiveModel.id) }) {
                if (projectUpdateReceiveModel.adminId != null) {
                    it[adminId] = projectUpdateReceiveModel.adminId
                }

                if (projectUpdateReceiveModel.title != null) {
                    it[title] = projectUpdateReceiveModel.title
                }

                if (projectUpdateReceiveModel.description != null) {
                    it[description] = projectUpdateReceiveModel.description
                }

                if (projectUpdateReceiveModel.tasks != null) {
                    it[tasks] = projectUpdateReceiveModel.tasks
                }

                if (projectUpdateReceiveModel.users != null) {
                    it[users] = projectUpdateReceiveModel.users
                }

                if (projectUpdateReceiveModel.events != null) {
                    it[events] = projectUpdateReceiveModel.events
                }
            }
        }
    }

    fun deleteProject(id: String) {
        transaction {
            Projects.deleteWhere {
                Projects.id.eq(id)
            }
        }
    }
    fun fetchProject(id: String): ProjectDTO? {
        return try {

            transaction {
                val model = Projects.select { Projects.id.eq(id) }.single()


                ProjectDTO(
                    id = model[Projects.id],
                    studio = model[Projects.studio],
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