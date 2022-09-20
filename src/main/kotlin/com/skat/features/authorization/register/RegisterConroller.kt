package com.skat.features.authorization.register

import com.skat.database.tokens.TokenDTO
import com.skat.database.tokens.Tokens
import com.skat.database.users.UserDTO
import com.skat.database.users.Users
import com.skat.features.authorization.login.TokenModel
import com.skat.utils.emailIsValid
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.*

object RegisterController {
    suspend fun register(call: ApplicationCall) {
        val receive = call.receive(RegisterReceiveRemote::class)

        if (emailIsValid(receive.email).not()) {
            call.respond(HttpStatusCode.BadRequest, "email is not valid")
        }

        val userDTO = Users.fetch(receive.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.BadRequest, "login busy")
        } else {
            val token = UUID.randomUUID().toString()

            try {
                Users.insert(
                    UserDTO(
                        login = receive.login,
                        password = receive.password,
                        username = receive.username,
                        email = receive.email,
                        isAdmin = receive.isAdmin,
                        typeStudio = receive.typeStudio,
                        score = 0
                    )
                )
            }catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }

            Tokens.insert(
                TokenDTO(
                    login = receive.login,
                    token = token
                )
            )

            call.respond(TokenModel(token = token))
        }
    }
}