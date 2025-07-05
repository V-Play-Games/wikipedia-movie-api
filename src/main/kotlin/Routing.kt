package net.vpg

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.vpg.vjson.value.JSONArray.Companion.toJSON

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText(
                MovieRepository.movies.random().toObject().toPrettyString()
            )
        }
        get("/years") {
            call.respondText(
                MovieRepository.years.toJSON().toPrettyString()
            )
        }
        get("/categories") {
            call.respondText(
                MovieRepository.categories.toJSON().toPrettyString()
            )
        }
        get("/genre") {
            call.respondText(
                MovieRepository.genre.toJSON().toPrettyString()
            )
        }
    }
}
