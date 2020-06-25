package com.alandvgarcia.kotlinmultiplataform.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Movie(
    @SerialName("adult")
    var adult: Boolean? = false,
    @SerialName("backdrop_path")
    var backdropPath: String? = "",
    @SerialName("genre_ids")
    var genreIds: List<Int?>? = listOf(),
    @SerialName("id")
    var id: Int? = 0,
    @SerialName("original_language")
    var originalLanguage: String? = "",
    @SerialName("original_title")
    var originalTitle: String? = "",
    @SerialName("overview")
    var overview: String? = "",
    @SerialName("popularity")
    var popularity: Double? = 0.0,
    @SerialName("poster_path")
    var posterPath: String? = "",
    @SerialName("release_date")
    var releaseDate: String? = "",
    @SerialName("title")
    var title: String? = "",
    @SerialName("video")
    var video: Boolean? = false,
    @SerialName("vote_average")
    var voteAverage: Double? = 0.0,
    @SerialName("vote_count")
    var voteCount: Int? = 0
)