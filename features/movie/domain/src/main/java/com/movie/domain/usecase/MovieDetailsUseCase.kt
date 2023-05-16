package com.movie.domain.usecase

import com.movie.common.network.Result
import com.movie.data.repository.MovieRepository
import com.movie.domain.displaymodel.MovieDetailDisplayModel
import com.movie.domain.mapper.MovieDetailsMapper
import javax.inject.Inject


class MovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDetailsMapper: MovieDetailsMapper
) {
    suspend fun getMovieDetails(movieId: Long): Result<MovieDetailDisplayModel> {
        val movieDetailModel = movieRepository.getMovieDetails(movieId)
        return movieDetailsMapper.mapToDisplayModel(movieDetailModel)
    }
}
