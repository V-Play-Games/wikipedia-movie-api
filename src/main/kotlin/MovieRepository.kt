package net.vpg

import net.vpg.vjson.parser.JSONParser.toJSON

object MovieRepository {
    val movies by lazy {
        MovieRepository.javaClass
            .getResource("/api.json")!!
            .toJSON()
            .toArray()
            .map { Movie(it.toObject()) }
    }

    val years by lazy {
        movies.map { it.year }.distinct().sorted()
    }

    val categories by lazy {
        movies.map { it.category }.distinct().sorted()
    }

    val genre by lazy {
        movies.mapNotNull { it.genre }.flatten().distinct().sorted()
    }
}
