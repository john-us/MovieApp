package com.movie.data.repository

import com.movie.common.constant.CommonConstant
import com.movie.common.network.ApiException
import com.movie.common.network.DataException
import com.movie.common.network.NetworkException
import com.movie.common.network.Result
import com.movie.data.mapper.MovieDetailsMapper
import com.movie.data.mapper.MovieListMapper
import com.movie.data.model.display.MovieDetailDisplayModel
import com.movie.data.model.display.MovieListDisplayModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieAPIService,
    private val movieListMapper: MovieListMapper,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val dispatcher: CoroutineDispatcher
) : MovieRepository {
    override suspend fun getMovieList(): Result<List<MovieListDisplayModel>> {
        return withContext(dispatcher) {
            try {
                val response = service.getMovieList()
                if (response.isSuccessful) {
                    val movieListModel = response.body()
                    return@withContext movieListModel?.let {
                    movieListMapper.mapToDisplayModel(
                        Result.Success(
                            movieListModel
                        )
                    )
                }
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

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetailDisplayModel> {
        return withContext(dispatcher) {
            try {
                val response = service.getMovieDetails(movieId)
                if (response.isSuccessful) {
                    val movieDetailModel = response.body()
                    return@withContext movieDetailModel?.let {
                    movieDetailsMapper.mapToDisplayModel(
                        Result.Success(
                            movieDetailModel
                        )
                    )
                }
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