package com.movie.network.repository

import com.movie.common.apiexception.ApiException
import com.movie.common.apiexception.DataException
import com.movie.common.apiexception.NetworkException
import com.movie.common.baseresponse.Result
import com.movie.common.constant.NetworkConstant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.Response

abstract class BaseRepository(private val dispatcher: CoroutineDispatcher) {
    protected suspend fun <T, R> fetchApiData(
        response: suspend () -> Response<T>,
        mapper: (T) -> Result<R>,
    ): Result<R> {
        return withContext(dispatcher) {
            try {
                val result = response()
                if (result.isSuccessful) {
                    mapper(result.body()!!)
                } else {
                    Result.Error(ApiException(result.code(), result.errorBody()?.toString()))
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> Result.Error(NetworkException(e.message))
                    else -> Result.Error(DataException(NetworkConstant.UNKNOWN_ERROR))
                }
            }
        }
    }
}

