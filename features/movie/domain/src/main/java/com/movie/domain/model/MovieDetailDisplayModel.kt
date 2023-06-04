package com.movie.domain.model

data class MovieDetailDisplayModel(
    val backdropPath: String,
    val id: Int,
    val originalTitle: String,
    val releaseDate: String,
    val status: String,
    val title: String
)