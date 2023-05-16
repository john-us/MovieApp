package com.movie.data.repository

import com.movie.common.network.Result
import com.movie.data.model.MovieDetailModel
import com.movie.data.model.MovieListModel

interface MovieRepository {
    suspend fun getMovieList(): Result<MovieListModel>
    suspend fun getMovieDetails(movieId: Long): Result<MovieDetailModel>
}