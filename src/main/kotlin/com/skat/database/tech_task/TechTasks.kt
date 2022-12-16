package com.skat.database.tech_task

import com.skat.database.projects.ProjectDTO
import com.skat.database.projects.Projects
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.cos

object TechTasks : Table("tech_task") {
        private val costumer = TechTasks.varchar("costumer", 50)
    private val idm = TechTasks.varchar("id", 50)
    private val studio = TechTasks.varchar("studio", 25)
    private val title = TechTasks.varchar("title", 25)
    private val description = TechTasks.varchar("description", 200)
    private val countPeople = TechTasks.integer("countPeople")
    private val isTake = TechTasks.bool("isTake")
    private val place = TechTasks.varchar("place", 30)
    private val date = TechTasks.varchar("date", 30)
    private val executor = TechTasks.varchar("executor", 50)

    fun insert(techTasksDTO: TechTasksDTO) {
        transaction {
            TechTasks.insert {
                it[studio] = techTasksDTO.studio
                it[costumer] = techTasksDTO.costumer
                it[executor] = techTasksDTO.executor
                it[title] = techTasksDTO.title
                it[idm] = techTasksDTO.id
                it[description] = techTasksDTO.description
                it[countPeople] = techTasksDTO.countPeople
                it[isTake] = techTasksDTO.isTake
                it[place] = techTasksDTO.place
                it[date] = techTasksDTO.date
            }
        }
    }

    fun delete(id: String) {
        transaction {
            TechTasks.deleteWhere {
                TechTasks.idm.eq(id)
            }
        }
    }

    fun fetch(id: String): TechTasksDTO? {
        return try {

            transaction {
                val model = TechTasks.select { idm.eq(id) }.single()

                TechTasksDTO(
                    id = model[idm],
                    costumer = model[costumer],
                    executor = model[executor],
                    title = model[title],
                    studio = model[studio],
                    description = model[description],
                    countPeople = model[countPeople],
                    isTake = model[isTake],
                    place = model[place],
                    date = model[date]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

}