package com.movie.data.repository

import com.movie.common.network.Result
import com.movie.data.model.api.MovieDetailModel
import com.movie.data.model.api.MovieListModel
import com.movie.data.model.display.MovieDetailDisplayModel
import com.movie.data.model.display.MovieListDisplayModel

interface MovieRepository {
    suspend fun getMovieList(): Result<List<MovieListDisplayModel>>
    suspend fun getMovieDetails(movieId: Long): Result<MovieDetailDisplayModel>
}