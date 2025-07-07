package net.vpg

import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main(args: Array<String>) {
    embeddedServer(Netty,
        port = args.firstOrNull()?.toIntOrNull() ?: 8080,
        module = {
            install(CORS) {
                anyHost()
                allowHeader("Content-Type")
                allowMethod(HttpMethod.Get)
                allowMethod(HttpMethod.Post)
            }

            configureRouting()
        }
    ).start(wait = true)
}
