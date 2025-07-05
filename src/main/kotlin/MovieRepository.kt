package net.vpg

import net.vpg.vjson.parser.JSONParser.toJSON

object MovieRepository {
    val movies: List<Movie> by lazy {
        MovieRepository.javaClass
            .getResource("/api.json")!!
            .toJSON()
            .toArray()
            .map { Movie(it.toObject()) }
    }

    val years: List<Int> by lazy {
        movies.map { it.year }.distinct().sorted()
    }

    val categories: List<String> by lazy {
        movies.map { it.category }.distinct()
    }

    val genre: List<String> by lazy {
        movies.mapNotNull { it.genre }.flatten().distinct().sorted()
    }
}