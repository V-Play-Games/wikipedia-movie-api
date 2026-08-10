package net.vplaygames.movie.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val title: String,
    val year: Int,
    val category: String,
    val director: String? = null,
    val genre: List<String>? = null,
    val cast: String? = null
)
