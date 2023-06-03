package com.movie.domain.mapper

import com.movie.common.constant.CommonConstant
import com.movie.common.network.DataException
import com.movie.common.network.Result
import com.movie.domain.model.apimodel.MovieListModel
import com.movie.domain.model.displaymodel.MovieListDisplayModel
import javax.inject.Inject

class MovieListMapper @Inject constructor() {
    fun mapToDisplayModel(movieListModel: Result<MovieListModel>): Result<List<MovieListDisplayModel>> {
        return when (movieListModel) {
            is Result.Success -> Result.Success(movieListModel.data.results?.map { movie ->
                with(movie) {
                    MovieListDisplayModel(
                        id = id,
                        title = title,
                        backdropPath = CommonConstant.IMAGE_URL + backdropPath,
                        releaseDate = releaseDate
                    )
                }
            } ?: listOf())

            is Result.Error -> Result.Error(movieListModel.exception)
            else -> {
                Result.Error(DataException(CommonConstant.UnknownError))
            }
        }
    }
}