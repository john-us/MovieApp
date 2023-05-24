package com.movie.domain.usecase

import com.movie.common.network.Result
import com.movie.data.repository.MovieRepository
import com.movie.domain.displaymodel.MovieListDisplayModel
import com.movie.domain.mapper.MovieListMapper
import javax.inject.Inject

class MovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieListMapper: MovieListMapper
) {
    suspend operator fun invoke(): Result<List<MovieListDisplayModel>> {
        val movieListModel = movieRepository.getMovieList()
        return movieListMapper.mapToDisplayModel(movieListModel)
    }
}