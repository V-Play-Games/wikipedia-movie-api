package net.vpg

import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(CORS) {
        anyHost()
        allowHeader("Content-Type")
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
    }

    configureRouting()
}
