package com.skat

import com.skat.features.authorization.AuthorizationRooting
import com.skat.features.projects.ProjectRouting
import com.skat.features.projects.events.EventRouting
import com.skat.features.studious.StudiousRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.skat.plugins.*
import org.jetbrains.exposed.sql.Database

fun main() {

    Database.connect("jdbc:postgresql://localhost:5432/guap_studios", driver = "org.postgresql.Driver",
        password = "res123", user = "postgres")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSerialization()
        AuthorizationRooting()
        ProjectRouting()
        StudiousRouting()
        EventRouting()
    }.start(wait = true)
}
