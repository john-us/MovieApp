package com.movie.data.repository

import com.movie.common.constant.CommonConstant
import com.movie.common.network.ApiException
import com.movie.common.network.DataException
import com.movie.common.network.NetworkException
import com.movie.common.network.Result
import com.movie.data.model.MovieDetailModel
import com.movie.data.model.MovieListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieAPIService
) : MovieRepository {
    override suspend fun getMovieList(): Result<MovieListModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getMovieList()
                if (response.isSuccessful) {
                    val movieListModel = response.body()
                    return@withContext movieListModel?.let { Result.Success(movieListModel) }
                        ?: Result.Error(
                            ApiException(response.code(), response.message())
                        )
                }
            } catch (e: IOException) {
                return@withContext Result.Error(NetworkException(e.message))
            }
            return@withContext Result.Error(DataException(CommonConstant.UnknownError))
        }

    }

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetailModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getMovieDetails(movieId)
                if (response.isSuccessful) {
                    val movieDetailModel = response.body()
                    return@withContext movieDetailModel?.let { Result.Success(movieDetailModel) }
                        ?: Result.Error(
                            ApiException(response.code(), response.message())
                        )
                }
            } catch (e: IOException) {
                return@withContext Result.Error(NetworkException(e.message))
            }
            return@withContext Result.Error(DataException(CommonConstant.UnknownError))
        }
    }

}