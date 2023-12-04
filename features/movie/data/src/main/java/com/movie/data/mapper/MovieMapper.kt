package com.movie.data.mapper

import com.movie.common.constant.CommonConstant
import com.movie.common.baseresponse.Result
import com.movie.data.model.MovieDetailModel
import com.movie.data.model.MovieListModel
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.domain.model.MovieListDisplayModel
import javax.inject.Inject

class MovieMapper @Inject constructor() {
    fun mapToDisplayModel(movieDetailModel: MovieDetailModel): Result<MovieDetailDisplayModel> =
        movieDetailModel.let {
            Result.Success(
                MovieDetailDisplayModel(
                    id = it.id,
                    backdropPath = CommonConstant.IMAGE_URL + it.backdropPath,
                    releaseDate = it.releaseDate,
                    title = it.title,
                    status = it.status,
                    originalTitle = it.originalTitle
                )
            )
        }


    fun mapToDisplayModel(movieListModel: MovieListModel): Result<List<MovieListDisplayModel>> =
        Result.Success(movieListModel.results?.map { movie ->
            movie.let {
                MovieListDisplayModel(
                    id = it.id,
                    title = it.title,
                    backdropPath = CommonConstant.IMAGE_URL + it.backdropPath,
                    releaseDate = it.releaseDate
                )
            }
        } ?: listOf())
}