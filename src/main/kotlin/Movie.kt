package net.vpg

import net.vpg.vjson.value.JSONObject
import net.vpg.vjson.value.SerializableObject

data class Movie(
    val title: String,
    val year: Int,
    val category: String,
    val director: String? = null,
    val genre: List<String>? = null,
    val cast: String? = null,
) : SerializableObject {
    constructor(obj: JSONObject) : this(
        title = obj.getString("title"),
        year = obj.getInt("year"),
        category = obj.getString("category"),
        director = obj.getString("director", null),
        genre = obj.optArray("genre").map { arr -> arr.toList().map { it.toString() } }.orElse(null),
        cast = obj.getString("cast", null)
    )

    override fun toObject(): JSONObject {
        val obj = JSONObject()
        obj.put("title", title)
        obj.put("year", year)
        obj.put("category", category)
        if (director != null) obj.put("director", director)
        if (genre != null) obj.put("genre", genre)
        if (cast != null) obj.put("cast", cast)
        return obj
    }
}
