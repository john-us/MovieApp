package com.movie.domain.mapper

import com.movie.common.constant.NetworkConstant
import com.movie.common.network.DataException
import com.movie.common.network.Result
import com.movie.data.model.MovieDetailModel
import com.movie.domain.displaymodel.MovieDetailDisplayModel
import javax.inject.Inject

open class MovieDetailsMapper @Inject constructor() {
    fun mapToDisplayModel(movieDetailModel: Result<MovieDetailModel>): Result<MovieDetailDisplayModel> {
        return when (movieDetailModel) {
            is Result.Success -> Result.Success(
                MovieDetailDisplayModel(
                    id = movieDetailModel.data.id,
                    backdropPath = movieDetailModel.data.backdropPath,
                    releaseDate = movieDetailModel.data.releaseDate,
                    title = movieDetailModel.data.title,
                    status = movieDetailModel.data.status,
                    originalTitle = movieDetailModel.data.originalTitle
                )
            )

            is Result.Error -> Result.Error(movieDetailModel.exception)
            else -> {
                Result.Error(DataException(NetworkConstant.UnknownError))
            }
        }
    }
}