package com.movie.data.model

import com.google.gson.annotations.SerializedName

data class MovieListModel(
    val page: Int? = 0,
    val results: List<Movie>?,
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
) {
    data class Movie(
        val id: Long?,
        val title: String,
        @SerializedName("backdrop_path")
        val backdropPath: String,
        @SerializedName("release_date")
        val releaseDate: String
    ) {

    }
}