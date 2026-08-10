package net.vplaygames.movie

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import net.vplaygames.movie.plugins.configurePlugins
import net.vplaygames.movie.repository.InMemoryMovieRepository
import net.vplaygames.movie.repository.MovieRepository
import net.vplaygames.movie.routing.configureMovieRouting

fun main(args: Array<String>) {
    val port = args.firstOrNull()?.toIntOrNull() ?: System.getenv("PORT")?.toIntOrNull() ?: 8080
    val repository: MovieRepository = InMemoryMovieRepository.loadFromResourceOrEnv()

    embeddedServer(Netty, port = port) {
        configurePlugins()
        configureMovieRouting(repository)
    }.start(wait = true)
}
