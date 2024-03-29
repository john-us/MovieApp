package com.movie.domain.usecase

import com.movie.common.baseresponse.Result
import com.movie.domain.model.MovieListDisplayModel
import com.movie.domain.reprositorycontract.IMovieRepository
import javax.inject.Inject

class MovieListUseCase @Inject constructor(
    private val movieRepository: IMovieRepository,
) {
    suspend operator fun invoke(): Result<List<MovieListDisplayModel>> =
        movieRepository.getMovieList()

}