package com.movie.domain.usecase

import com.movie.common.baseresponse.Result
import com.movie.domain.model.MovieDetailDomainModel
import com.movie.domain.reprositorycontract.IMovieRepository
import javax.inject.Inject


class MovieDetailsUseCase @Inject constructor(
    private val movieRepository: IMovieRepository,
) {
    suspend operator fun invoke(movieId: Long): Result<MovieDetailDomainModel> =
        movieRepository.getMovieDetails(movieId)

}
