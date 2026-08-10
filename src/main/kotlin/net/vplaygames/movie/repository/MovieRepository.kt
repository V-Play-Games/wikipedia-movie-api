package net.vplaygames.movie.repository

import kotlinx.serialization.json.Json
import net.vplaygames.movie.model.Movie
import java.io.File
import java.io.InputStream

interface MovieRepository {
    val movies: List<Movie>
    val years: List<Int>
    val categories: List<String>
    val genres: List<String>

    fun getRandomMovie(categories: List<String>?, years: List<Int>?): Movie?
}

class InMemoryMovieRepository(override val movies: List<Movie>) : MovieRepository {

    override val years = movies.map { it.year }.distinct().sorted()
    override val categories = movies.map { it.category }.distinct().sorted()
    override val genres = movies.mapNotNull { it.genre }.flatten().distinct().sorted()

    override fun getRandomMovie(categories: List<String>?, years: List<Int>?): Movie? {
        val filtered = movies.filter { movie ->
            (categories.isNullOrEmpty() || categories.contains(movie.category.lowercase())) &&
                (years.isNullOrEmpty() || years.contains(movie.year))
        }
        return if (filtered.isEmpty()) null else filtered.random()
    }

    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        fun loadFromResourceOrEnv(
            resourcePath: String = "/api.json",
            envVar: String = "MOVIES_DATA_PATH"
        ): InMemoryMovieRepository {
            val envPath = System.getenv(envVar)
            val inputStream: InputStream = if (!envPath.isNullOrBlank()) {
                val externalFile = File(envPath)
                if (externalFile.exists()) {
                    externalFile.inputStream()
                } else {
                    error("Specified data file at $envVar '$envPath' does not exist.")
                }
            } else {
                InMemoryMovieRepository::class.java.getResourceAsStream(resourcePath)
                    ?: error("Could not find default movie data file '$resourcePath' on classpath.")
            }

            val moviesList: List<Movie> = inputStream.use { stream ->
                json.decodeFromString(stream.bufferedReader().readText())
            }

            return InMemoryMovieRepository(moviesList)
        }
    }
}
