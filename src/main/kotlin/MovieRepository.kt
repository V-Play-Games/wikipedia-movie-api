package net.vpg

import net.vpg.vjson.parser.JSONParser.toJSON

object MovieRepository {
    val movies = MovieRepository.javaClass
        .getResource("/api.json")!!
        .toJSON()
        .toArray()
        .map { Movie(it.toObject()) }

    val years = movies.map { it.year }.distinct().sorted()
    val categories = movies.map { it.category }.distinct().sorted()
    val genres = movies.mapNotNull { it.genre }.flatten().distinct().sorted()
}
