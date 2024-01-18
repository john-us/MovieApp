package com.movie.data.mapper

import com.movie.common.baseresponse.Result
import com.movie.common.constant.CommonConstant
import com.movie.data.model.MovieDetailModel
import com.movie.data.model.MovieListModel
import com.movie.domain.model.MovieDetailDomainModel
import com.movie.domain.model.MovieListDomainModel
import javax.inject.Inject

class MovieMapper @Inject constructor() {
    fun mapToDomainModel(movieDetailModel: MovieDetailModel): Result<MovieDetailDomainModel> =
        movieDetailModel.let {
            Result.Success(
                MovieDetailDomainModel(
                    id = it.id,
                    backdropPath = CommonConstant.IMAGE_URL + it.backdropPath,
                    releaseDate = it.releaseDate,
                    title = it.title,
                    status = it.status,
                    originalTitle = it.originalTitle
                )
            )
        }


    fun mapToDomainModel(movieListModel: MovieListModel): Result<List<MovieListDomainModel>> =
        Result.Success(movieListModel.results?.map { movie ->
            movie.let {
                MovieListDomainModel(
                    id = it.id,
                    title = it.title,
                    backdropPath = CommonConstant.IMAGE_URL + it.backdropPath,
                    releaseDate = it.releaseDate
                )
            }
        } ?: listOf())
}