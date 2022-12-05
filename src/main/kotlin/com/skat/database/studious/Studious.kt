package com.skat.database.studious

import array
import com.skat.features.studious.StudioUpdateReceiveModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Studious : Table("studious") {
    private val name = Studious.varchar("name", 30)
    private val projects = Studious.array<String>("projects", VarCharColumnType())
    private val users = Studious.array<String>("users", VarCharColumnType())
    private val tech_task = Studious.array<String>("tech_task", VarCharColumnType())

    fun insert(studiousDTO: StudiousDTO) {
        transaction {
            Studious.insert {
                it[name] = studiousDTO.id
                it[users] = studiousDTO.users
                it[tech_task] = studiousDTO.tech_task
                it[projects] = studiousDTO.projects
            }
        }
    }

    fun updateStudio(
        updateModel: StudioUpdateReceiveModel,
    ) {
        transaction {
            Studious.update({ Studious.name.eq(updateModel.id) }) {
                if (updateModel.users != null) {
                    it[users] = updateModel.users
                }

                if (updateModel.projects != null) {
                    it[projects] = updateModel.projects
                }

                if (updateModel.tech_task != null) {
                    it[tech_task] = updateModel.tech_task
                }
            }
        }
    }

    fun fetchStudio(name: String): StudiousDTO? {
        return try {
            transaction {
                val model = Studious.select { Studious.name.eq(name) }.single()


                StudiousDTO(
                    id = model[Studious.name],
                    projects = model[projects],
                    users = model[users],
                    tech_task = model[tech_task],
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}