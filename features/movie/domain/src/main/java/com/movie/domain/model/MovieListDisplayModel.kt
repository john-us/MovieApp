package com.movie.domain.model

data class MovieListDisplayModel(
    val id: Long,
    val title: String,
    val backdropPath: String,
    val releaseDate: String
)