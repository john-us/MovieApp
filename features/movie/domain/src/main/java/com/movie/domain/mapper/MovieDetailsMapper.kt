package com.movie.domain.mapper

import com.movie.common.constant.CommonConstant
import com.movie.common.network.DataException
import com.movie.common.network.Result
import com.movie.domain.model.apimodel.MovieDetailModel
import com.movie.domain.model.displaymodel.MovieDetailDisplayModel
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor() {
    fun mapToDisplayModel(movieDetailModel: Result<MovieDetailModel>): Result<MovieDetailDisplayModel> {
        return when (movieDetailModel) {
            is Result.Success -> Result.Success(
                with(movieDetailModel.data) {
                    MovieDetailDisplayModel(
                        id = id,
                        backdropPath = backdropPath,
                        releaseDate = releaseDate,
                        title = title,
                        status = status,
                        originalTitle = originalTitle
                    )
                }

            )

            is Result.Error -> Result.Error(movieDetailModel.exception)
            else -> {
                Result.Error(DataException(CommonConstant.UnknownError))
            }
        }
    }
}