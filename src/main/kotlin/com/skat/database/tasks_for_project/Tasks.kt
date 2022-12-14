package com.skat.database.tasks_for_project

import com.skat.database.projects.Projects
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Tasks : Table("tasks") {
    val id = Tasks.varchar("id", 50)
    val title = Tasks.varchar("title", 25)
    val description = Tasks.varchar("description", 70)
    val isCheck = Tasks.bool("isCheck")
    val user = Tasks.varchar("user", 50)
    val color = Tasks.varchar("color", 25)
    val mark = Tasks.varchar("mark", 25)

    fun insert(tasks: TaskDTO) {
        transaction {
            Tasks.insert {
                it[Tasks.id] = tasks.id
                it[title] = tasks.title
                it[description] = tasks.description
                it[isCheck] = tasks.isCheck
                it[user] = tasks.user
                it[color] = tasks.color
                it[mark] = tasks.mark
            }
        }
    }

    fun updateIsCheck(id: String, newIsCheck: Boolean) {
        transaction {
            Tasks.update({Tasks.id.eq(id)}) {
                it[isCheck] = newIsCheck
            }
        }
    }
    fun fetch(id: String): TaskDTO? {
        return try {
            transaction {
                val model = Tasks.select { Tasks.id.eq(id) }.single()


                TaskDTO(
                    id = model[Tasks.id],
                    title = model[title],
                    description = model[description],
                    user = model[user],
                    isCheck = model[isCheck],
                    color = model[color],
                    mark = model[mark]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun delete(id: String) {
        transaction {
            Tasks.deleteWhere {
                Tasks.id.eq(id)
            }
        }
    }
}