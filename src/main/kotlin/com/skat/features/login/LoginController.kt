package com.skat.features.login

import com.skat.database.tokens.TokenDTO
import com.skat.database.tokens.Tokens
import com.skat.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

object LoginController {

    suspend fun performLogin(call: ApplicationCall) {
        val receive = call.receive(LoginReceiveModel::class)

        val userDTO = Users.fetch(login = receive.login)

        if (userDTO == null ){
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

    suspend fun performToken(call: ApplicationCall) {
        val receive = call.receive(TokenModel::class)

        val tokenDTO = Tokens.fetchLogin(receive.token)
        if (tokenDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "Not found token")
        } else {
            call.respond(HttpStatusCode.OK, "Token found")
        }
    }
}