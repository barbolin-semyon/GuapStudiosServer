package com.skat.features.authorization.login

import com.skat.database.tokens.TokenDTO
import com.skat.database.tokens.Tokens
import com.skat.database.users.UserDTO
import com.skat.database.users.Users
import com.skat.features.projects.StringResponceModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*
import kotlin.math.log

object LoginController {
    suspend fun performLogin(call: ApplicationCall) {
        val receive = call.receive(LoginReceiveModel::class)

        val userDTO = Users.fetch(login = receive.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "Not found user")
        } else {
            if (userDTO.password == receive.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(tokenDTO = TokenDTO(receive.login, token))

                call.respond(TokenModel(token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid user")
            }
        }
    }

    suspend fun getUser(call: ApplicationCall) {
        val login = call.parameters["login"]

        if (login != null) {
            val user = Users.fetch(login)

            if (user != null) {
                call.respond(HttpStatusCode.OK, user.hidePassword())
            } else {
                call.respond(HttpStatusCode.BadRequest, StringResponceModel("Login is not valid"))
            }

        } else {
            call.respond(HttpStatusCode.BadRequest, StringResponceModel("There isn't parameter login"))
        }
    }

    suspend fun updateIsAdminUser(call: ApplicationCall) {
        val receive = call.receive(UpdateAdminModel::class)

        Users.update(receive)


    }

    suspend fun performToken(call: ApplicationCall) {
        val token = call.parameters["token"]

        if (token == null) {
            call.respond(HttpStatusCode.BadRequest, "There isn't parameter token")
        } else {

            val tokenDTO = Tokens.fetchLogin(token)
            if (tokenDTO == null) {
                call.respond(HttpStatusCode.BadRequest, "Not found token")
            } else {
                val userDTO = Users.fetch(tokenDTO.login)

                call.respond(HttpStatusCode.OK, userDTO!!.hidePassword())
            }
        }
    }

    private fun UserDTO.hidePassword(): UserModel {
        return UserModel(
            login = this.login,
            email = this.email,
            username = this.username,
            isAdmin = this.isAdmin,
            typeStudio = this.typeStudio,
            score = this.score,
        )
    }
}