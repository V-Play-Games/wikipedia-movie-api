package net.vplaygames.movie.plugins

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ErrorResponse(val error: String)

fun Application.configurePlugins() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = false
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Get)
    }

    install(CachingHeaders) {
        options { _, outgoingContent ->
            outgoingContent.contentType?.withoutParameters()
                ?.takeIf { it.contentType == "application" && it.contentSubtype == "json" }
                ?.let { CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 3600)) }
        }
    }

    install(CallLogging)

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.application.log.error("Unhandled exception processing call", cause)
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(cause.localizedMessage ?: "Internal Server Error")
            )
        }
    }
}
