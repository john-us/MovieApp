package com.movie.domain.usecase

import com.movie.common.network.Result
import com.movie.domain.reprositorycontract.IMovieRepository
import com.movie.domain.mapper.MovieListMapper
import com.movie.domain.model.displaymodel.MovieListDisplayModel
import javax.inject.Inject

class MovieListUseCase @Inject constructor(
    private val movieRepository: IMovieRepository,
    private val movieListMapper: MovieListMapper
) {
    suspend operator fun invoke(): Result<List<MovieListDisplayModel>> {
        val movieListModel = movieRepository.getMovieList()
        return movieListMapper.mapToDisplayModel(movieListModel)
    }
}