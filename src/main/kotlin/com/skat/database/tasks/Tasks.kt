package com.skat.database.tasks

import array
import com.skat.database.projects.ProjectDTO
import com.skat.database.projects.Projects
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Tasks : Table("tasks") {
    val id = Tasks.varchar("id", 50)
    val title = Tasks.varchar("title", 25)
    val description = Tasks.varchar("description", 70)
    val isCheck = Tasks.bool("isCheck")
    val user = Tasks.varchar("user", 50)
    val color = Tasks.varchar("id", 25)
    val mark = Tasks.varchar("id", 25)

    fun insert(tasks: TaskDTO) {
        transaction {
            Projects.insert {
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

    fun deleteProject(id: String) {
        transaction {
            Tasks.deleteWhere {
                Tasks.id.eq(id)
            }
        }
    }
}