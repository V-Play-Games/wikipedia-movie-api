package net.vpg

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.vpg.vjson.value.JSONArray.Companion.toJSON

fun Application.configureRouting() {
    routing {
        get("/api/alpha/movies/random") {
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
                .filter { categories.isNullOrEmpty() || categories.contains(it.category.lowercase()) }
                .filter { years.isNullOrEmpty() || years.contains(it.year) }
            call.respondText(
                if (filtered.isEmpty())
                    "null"
                else
                    filtered.random().toObject().toString()
            )
        }
        get("/api/alpha/years") {
            call.respondText(
                MovieRepository.years.toJSON().toString()
            )
        }
        get("/api/alpha/categories") {
            call.respondText(
                MovieRepository.categories.toJSON().toString()
            )
        }
    }
}
