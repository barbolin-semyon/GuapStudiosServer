package com.skat.features.login

import com.skat.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive(LoginReceiveModel::class)

        val userDTO = Users.fetch(login = receive.login)

        if (userDTO == null ){
            call.respond(HttpStatusCode.BadRequest, "Not found user")
        } else {
            if (userDTO.password == receive.password) {
                //call.respond(Login)
            }
        }
    }

}