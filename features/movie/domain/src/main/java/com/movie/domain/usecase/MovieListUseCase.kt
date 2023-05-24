package com.movie.domain.usecase

import com.movie.common.network.Result
import com.movie.data.repository.MovieRepository
import com.movie.data.model.display.MovieListDisplayModel
import com.movie.data.mapper.MovieListMapper
import javax.inject.Inject

class MovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(): Result<List<MovieListDisplayModel>> {
        return movieRepository.getMovieList()
    }
}