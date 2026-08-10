package net.vpg

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import net.vplaygames.movie.model.Movie
import net.vplaygames.movie.plugins.configurePlugins
import net.vplaygames.movie.repository.InMemoryMovieRepository
import net.vplaygames.movie.routing.configureMovieRouting
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MovieApiTest {

    private val testRepository = InMemoryMovieRepository(
        listOf(
            Movie(title = "Movie 1", year = 1990, category = "american", genre = listOf("action")),
            Movie(title = "Movie 2", year = 2000, category = "british", genre = listOf("comedy")),
            Movie(title = "Movie 3", year = 2020, category = "american", genre = listOf("drama"))
        )
    )

    @Test
    fun testGetYears() = testApplication {
        application {
            configurePlugins()
            configureMovieRouting(testRepository)
        }
        client.get("/api/v1/years").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), contentType())
            assertEquals("[1990,2000,2020]", bodyAsText())
        }
    }

    @Test
    fun testGetCategories() = testApplication {
        application {
            configurePlugins()
            configureMovieRouting(testRepository)
        }
        client.get("/api/v1/categories").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("[\"american\",\"british\"]", bodyAsText())
        }
    }

    @Test
    fun testGetGenres() = testApplication {
        application {
            configurePlugins()
            configureMovieRouting(testRepository)
        }
        client.get("/api/v1/genres").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("[\"action\",\"comedy\",\"drama\"]", bodyAsText())
        }
    }

    @Test
    fun testGetRandomMovieSuccess() = testApplication {
        application {
            configurePlugins()
            configureMovieRouting(testRepository)
        }
        client.get("/api/v1/movies/random?category=american&year=1990").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Movie 1"))
        }
    }

    @Test
    fun testGetRandomMovieNotFound() = testApplication {
        application {
            configurePlugins()
            configureMovieRouting(testRepository)
        }
        client.get("/api/v1/movies/random?year=9999").apply {
            assertEquals(HttpStatusCode.NotFound, status)
            assertTrue(bodyAsText().contains("No movies found matching criteria"))
        }
    }

    @Test
    fun testDeprecatedAlphaRoute() = testApplication {
        application {
            configurePlugins()
            configureMovieRouting(testRepository)
        }
        client.get("/api/alpha/years").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Use /api/v1/years instead", headers["X-API-Deprecated"])
        }
    }
}
