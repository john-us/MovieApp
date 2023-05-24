package com.movie.domain.usecase

import com.movie.common.network.Result
import com.movie.data.repository.MovieRepository
import com.movie.data.model.display.MovieDetailDisplayModel
import com.movie.data.mapper.MovieDetailsMapper
import javax.inject.Inject


class MovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Long): Result<MovieDetailDisplayModel> {
        return movieRepository.getMovieDetails(movieId)
    }
}
