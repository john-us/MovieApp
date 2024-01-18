package com.movie.domain.reprositorycontract

import com.movie.common.baseresponse.Result
import com.movie.domain.model.MovieDetailDomainModel
import com.movie.domain.model.MovieListDomainModel


interface IMovieRepository {
    suspend fun getMovieList(): Result<List<MovieListDomainModel>>
    suspend fun getMovieDetails(movieId: Long): Result<MovieDetailDomainModel>
}
