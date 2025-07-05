package net.vpg

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.vpg.vjson.value.JSONArray.Companion.toJSON

fun Application.configureRouting() {
    routing {
        get("/") {
            val params = call.request.queryParameters
            val categories = params["category"]
                ?.split(",")
                ?.map { it.lowercase() }
                ?.distinct()
            val years = params["year"]
                ?.split(",")
                ?.mapNotNull { it.toIntOrNull() }
                ?.distinct()

            val filtered = MovieRepository.movies
                .filter { categories == null || categories.isEmpty() || categories.contains(it.category) }
                .filter { years == null || years.isEmpty() || years.contains(it.year) }
            call.respondText(
                if (filtered.isEmpty())
                    "null"
                else
                    filtered.random().toObject().toPrettyString()
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
