package net.vplaygames.movie.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.vplaygames.movie.plugins.ErrorResponse
import net.vplaygames.movie.repository.MovieRepository

fun Application.configureMovieRouting(repository: MovieRepository) {
    routing {
        route("/api/v1") {
            get("/movies/random") {
                val params = call.request.queryParameters
                val categories = params["category"]
                    ?.split(",")
                    ?.map { it.trim().lowercase() }
                    ?.filter { it.isNotEmpty() }
                    ?.distinct()
                val years = params["year"]
                    ?.split(",")
                    ?.mapNotNull { it.trim().toIntOrNull() }
                    ?.distinct()

                val randomMovie = repository.getRandomMovie(categories, years)
                if (randomMovie == null) {
                    call.respond(HttpStatusCode.NotFound, ErrorResponse("No movies found matching criteria"))
                } else {
                    call.respond(HttpStatusCode.OK, randomMovie)
                }
            }

            get("/years") {
                call.respond(HttpStatusCode.OK, repository.years)
            }

            get("/categories") {
                call.respond(HttpStatusCode.OK, repository.categories)
            }

            get("/genres") {
                call.respond(HttpStatusCode.OK, repository.genres)
            }
        }
    }
}
