package net.vpg

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.cors.routing.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = args.firstOrNull()?.toIntOrNull() ?: 8080, module = {
        install(CORS) {
            anyHost()
            allowHeader("Content-Type")
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
        }
        install(CachingHeaders) {
            options { _, outgoingContent ->
                outgoingContent.contentType?.withoutParameters()
                    ?.takeIf { it.contentType == "application" && it.contentSubtype == "json" }
                    ?.let { CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 3600)) }
            }
        }

        install(CallLogging)

        configureRouting()

        MovieRepository.movies // refer to make it load data at init time instead of at call time
    }).start(wait = true)
}
