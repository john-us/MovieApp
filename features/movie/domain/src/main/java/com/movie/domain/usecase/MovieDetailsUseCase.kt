package com.movie.domain.usecase

import com.movie.common.network.Result
import com.movie.domain.reprositorycontract.IMovieRepository
import com.movie.domain.mapper.MovieDetailsMapper
import com.movie.domain.model.displaymodel.MovieDetailDisplayModel
import javax.inject.Inject


class MovieDetailsUseCase @Inject constructor(
    private val movieRepository: IMovieRepository,
    private val movieDetailsMapper: MovieDetailsMapper
) {
    suspend operator fun invoke(movieId: Long): Result<MovieDetailDisplayModel> {
        val movieDetailModel = movieRepository.getMovieDetails(movieId)
        return movieDetailsMapper.mapToDisplayModel(movieDetailModel)
    }
}
