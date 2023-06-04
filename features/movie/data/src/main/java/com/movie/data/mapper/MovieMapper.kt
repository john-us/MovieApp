package com.movie.data.mapper

import com.movie.common.constant.CommonConstant
import com.movie.common.network.Result
import com.movie.data.model.MovieDetailModel
import com.movie.data.model.MovieListModel
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.domain.model.MovieListDisplayModel
import javax.inject.Inject

class MovieMapper @Inject constructor() {
    fun mapToDisplayModel(movieDetailModel: MovieDetailModel): Result<MovieDetailDisplayModel> {
        return Result.Success(
            with(movieDetailModel) {
                MovieDetailDisplayModel(
                    id = id,
                    backdropPath = CommonConstant.IMAGE_URL + backdropPath,
                    releaseDate = releaseDate,
                    title = title,
                    status = status,
                    originalTitle = originalTitle
                )
            })


    }

    fun mapToDisplayModel(movieListModel: MovieListModel): Result<List<MovieListDisplayModel>> {
        return Result.Success(movieListModel.results?.map { movie ->
            with(movie) {
                MovieListDisplayModel(
                    id = id,
                    title = title,
                    backdropPath = CommonConstant.IMAGE_URL + backdropPath,
                    releaseDate = releaseDate
                )
            }
        } ?: listOf())

    }
}