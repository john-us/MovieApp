package com.movie.domain.reprositorycontract

import com.movie.common.baseresponse.Result
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.domain.model.MovieListDisplayModel


interface IMovieRepository {
    suspend fun getMovieList(): Result<List<MovieListDisplayModel>>
    suspend fun getMovieDetails(movieId: Long): Result<MovieDetailDisplayModel>
}
