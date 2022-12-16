package com.skat.database.users

import com.skat.database.projects.Projects
import com.skat.features.authorization.login.UpdateAdminModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object Users: Table("user") {
    private val login = Users.varchar("login", 25)
    private val password = Users.varchar("password", 25)
    private val username = Users.varchar("username", 25)
    private val email = Users.varchar("email", 25)
    private val typeStudio = Users.varchar("typeStudio", 25)
    private val score = Users.integer("score")
    private val isAdmin = Users.bool ("isAdmin")

    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[email] = userDTO.email
                it[typeStudio] = userDTO.typeStudio
                it[score] = userDTO.score
                it[isAdmin] = userDTO.isAdmin

            }
        }
    }

    fun update(updateAdminModel: UpdateAdminModel) {
        transaction {
            Users.update({ Users.login.eq(updateAdminModel.login) }) {
                it[isAdmin] = updateAdminModel.isAdmin
            }
        }
    }

    fun fetch(login: String) : UserDTO? {
        return try {
            transaction {
                val userModel = Users.select { Users.login.eq(login) }.single()

                UserDTO(
                    login = userModel[Users.login],
                    password = userModel[password],
                    email = userModel[email],
                    username = userModel[username],
                    isAdmin = userModel[isAdmin],
                    typeStudio = userModel[typeStudio],
                    score = userModel[score],
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}