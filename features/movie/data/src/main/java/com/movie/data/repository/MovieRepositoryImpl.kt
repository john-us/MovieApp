package com.movie.data.repository

import com.movie.common.constant.CommonConstant
import com.movie.common.network.ApiException
import com.movie.common.network.DataException
import com.movie.common.network.NetworkException
import com.movie.common.network.Result
import com.movie.data.mapper.MovieMapper
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.domain.model.MovieListDisplayModel
import com.movie.domain.reprositorycontract.IMovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieAPIService,
    private val dispatcher: CoroutineDispatcher,
    private val mapper: MovieMapper,
) : IMovieRepository {

    override suspend fun getMovieList(): Result<List<MovieListDisplayModel>> {
        return withContext(dispatcher) {
            fetchApiData({service.getMovieList()}, mapper::mapToDisplayModel)
        }
    }

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetailDisplayModel> {
        return withContext(dispatcher) {
            fetchApiData({ service.getMovieDetails(movieId) }, mapper::mapToDisplayModel)
        }
    }

    private suspend fun <T, R> fetchApiData(
        response: suspend () -> Response<T>,
        mapper: (T) -> Result<R>
    ): Result<R> {
        return try {
            val result = response()
            if (result.isSuccessful) {
                mapper(result.body()!!)
            } else {
                Result.Error(ApiException(result.code(), result.errorBody()?.toString()))
            }
        } catch (e: Exception) {
            when (e) {
                is ApiException, is NetworkException -> Result.Error(e)
                else -> Result.Error(DataException(CommonConstant.UnknownError))
            }
        }
    }
}
