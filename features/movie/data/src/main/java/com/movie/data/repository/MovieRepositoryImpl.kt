package com.movie.data.repository

import com.movie.common.constant.NetworkConstant
import com.movie.common.network.ApiException
import com.movie.common.network.DataException
import com.movie.common.network.NetworkException
import com.movie.common.network.Result
import com.movie.data.model.MovieDetailModel
import com.movie.data.model.MovieListModel
import okio.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieAPIService
) : MovieRepository {
    override suspend fun getMovieList(): Result<MovieListModel> {
        try {
            val response = service.getMovieList()
            if (response.isSuccessful) {
                val movieListModel = response.body()
                return movieListModel?.let { Result.Success(movieListModel) } ?: Result.Error(
                    ApiException(response.code(), response.message())
                )
            }
        } catch (e: IOException) {
            return Result.Error(NetworkException(e.message))
        }
        return Result.Error(DataException(NetworkConstant.UnknownError))

    }

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetailModel> {
        try {
            val response = service.getMovieDetails(movieId)
            if (response.isSuccessful) {
                val movieDetailModel = response.body()
                return movieDetailModel?.let { Result.Success(movieDetailModel) } ?: Result.Error(
                    ApiException(response.code(), response.message())
                )
            }
        } catch (e: IOException) {
            return Result.Error(NetworkException(e.message))
        }
        return Result.Error(DataException(NetworkConstant.UnknownError))
    }

}