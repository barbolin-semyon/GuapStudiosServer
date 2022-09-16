package com.skat.features.authorization

import com.skat.features.authorization.login.LoginController
import com.skat.features.authorization.register.RegisterController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.AuthorizationRooting() {
    routing {
        post("/login") {
            LoginController.performLogin(call)
            return@post
        }

        post("/token") {
            LoginController.performToken(call)
            return@post
        }

        post("/register") {
            RegisterController.register(call)
            return@post
        }
    }
}