package com.movie.domain.usecase

import com.movie.common.network.Result
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.domain.reprositorycontract.IMovieRepository
import javax.inject.Inject


class MovieDetailsUseCase @Inject constructor(
    private val movieRepository: IMovieRepository,
) {
    suspend operator fun invoke(movieId: Long): Result<MovieDetailDisplayModel> {
        return movieRepository.getMovieDetails(movieId)
    }
}
