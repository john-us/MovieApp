package com.movie.domain.mapper

import com.movie.common.constant.NetworkConstant
import com.movie.common.network.DataException
import com.movie.common.network.Result
import com.movie.data.model.MovieListModel
import com.movie.domain.displaymodel.MovieListDisplayModel

open class MovieListMapper {
    fun mapToDisplayModel(movieListModel: Result<MovieListModel>): Result<List<MovieListDisplayModel>> {
        return when (movieListModel) {
            is Result.Success -> Result.Success(movieListModel.data.results?.map { movie ->
                MovieListDisplayModel(
                    id = movie.id,
                    title = movie.title,
                    backdropPath = movie.backdropPath,
                    releaseDate = movie.releaseDate
                )
            } ?: listOf())

            is Result.Error -> Result.Error(movieListModel.exception)
            else -> {
                Result.Error(DataException(NetworkConstant.UnknownError))
            }
        }
    }
}