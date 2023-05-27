package com.movie.domain.ireprository

import com.movie.common.network.Result
import com.movie.domain.model.apimodel.MovieDetailModel
import com.movie.domain.model.apimodel.MovieListModel


interface IMovieRepository {
    suspend fun getMovieList(): Result<MovieListModel>
    suspend fun getMovieDetails(movieId: Long): Result<MovieDetailModel>
}
