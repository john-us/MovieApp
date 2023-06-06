package com.movie.data

import com.google.gson.Gson
import com.movie.common.constant.CommonConstant
import com.movie.common.network.ApiException
import com.movie.common.network.DataException
import com.movie.common.network.NetworkException
import com.movie.common.network.Result
import com.movie.common.network.Result.Error
import com.movie.data.mapper.MovieMapper
import com.movie.data.model.MovieDetailModel
import com.movie.data.model.MovieListModel
import com.movie.data.repository.MovieAPIService
import com.movie.data.repository.MovieRepositoryImpl
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.domain.model.MovieListDisplayModel
import com.movie.domain.reprositorycontract.IMovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.nio.file.Files
import java.nio.file.Paths


class MovieRepositoryImplTest {
    private lateinit var service: MovieAPIService
    private lateinit var dispatcher: CoroutineDispatcher
    private lateinit var mapper: MovieMapper
    private lateinit var repository: IMovieRepository

    @Before
    fun setup() {
        service = mockk()
        dispatcher = Dispatchers.Unconfined
        mapper = mockk()
        repository = MovieRepositoryImpl(service, dispatcher, mapper)
    }

    @Test
    fun `given valid movie list response, when getMovieList is called, then return Success with MovieListDisplayModel`() =
        runBlocking {
            //Mock Success Response

            val jsonString = String(withContext(Dispatchers.IO) {
                Files.readAllBytes(Paths.get(movieListPath))
            })
            val movieListModel =
                Gson().fromJson(jsonString, MovieListModel::class.java)
            coEvery { service.getMovieList() } returns Response.success(movieListModel)

            // Mock mapper
            val displayJsonString = String(withContext(Dispatchers.IO) {
                Files.readAllBytes(Paths.get(movieListDisplayPath))
            })
            val mappedList = Result.Success(
                Gson().fromJson(
                    displayJsonString,
                    Array<MovieListDisplayModel>::class.java
                ).toList()
            )
            coEvery { mapper.mapToDisplayModel(movieListModel) } returns mappedList

            // Call the method
            val result = repository.getMovieList()

            // Verify the result
            assertTrue(result is Result.Success)
            assertEquals(mappedList.data, (result as Result.Success).data)
        }

    @Test
    fun `given network error, when getMovieList is called, then return Error with NetworkException`() =
        runBlocking {
            // Mock API service response
            coEvery { service.getMovieList() } throws IOException(networkError)

            // Perform the test
            val result = repository.getMovieList()

            // Verify the result
            assertTrue(result is Error)
            assertTrue((result as Error).exception is NetworkException)
            assertEquals(networkError, result.exception.message)
        }

    @Test
    fun `given unknown error, when getMovieList is called, then return Error with DataException`() =
        runBlocking {
            // Mock API service response
            val errorBody = CommonConstant.UnknownError.toResponseBody(null)
            val response = Response.error<MovieListModel>(errorCode, errorBody)
            coEvery { service.getMovieList() } returns response

            // Perform the test
            val result = repository.getMovieList()

            // Verify the result
            assertTrue(result is Error)
            assertTrue((result as Error).exception is DataException)
            assertEquals(errorCode, response.code())
            assertEquals(CommonConstant.UnknownError, result.exception.message)
        }

    @Test
    fun `given API error response, when getMovieList is called, then return Error with ApiException`() =
        runBlocking {
            // Mock API service response
            val response: Response<MovieListModel> = Response.success(null)
            coEvery { service.getMovieList() } returns response

            // Perform the test
            val result = repository.getMovieList()

            // Verify the result
            assertTrue(result is Error)
            assertTrue((result as Error).exception is ApiException)
            assertEquals(response.errorBody()?.toString(), result.exception.message)
        }

    @Test
    fun `given valid movie detail response, when getMovieDetails is called, then return Success with MovieDetailDisplayModel`() =
        runBlocking {
            // Mock successful response

            val jsonString = String(withContext(Dispatchers.IO) {
                Files.readAllBytes(Paths.get(movieDetailPath))
            })
            val movieDetailModel = Gson().fromJson(jsonString, MovieDetailModel::class.java)
            val response = Response.success(movieDetailModel)
            coEvery { service.getMovieDetails(movieId) } returns response

            // Mock mapper


            val displayJsonString = String(withContext(Dispatchers.IO) {
                Files.readAllBytes(Paths.get(movieDetailDisplayPath))
            })

            val mappedDetail = Result.Success(
                Gson().fromJson(displayJsonString, MovieDetailDisplayModel::class.java)
            )
            coEvery { mapper.mapToDisplayModel(movieDetailModel) } returns mappedDetail

            // Call the method
            val result = repository.getMovieDetails(movieId)

            // Verify the result
            assertTrue(result is Result.Success)
            assertEquals(mappedDetail.data, (result as Result.Success).data)
        }

    @Test
    fun `given network error, when getMovieDetails is called, then return Error with NetworkException`() =
        runBlocking {
            // Mock API service response
            coEvery { service.getMovieDetails(movieId) } throws IOException(networkError)

            // Perform the test
            val result = repository.getMovieDetails(movieId)

            // Verify the result
            assertTrue(result is Error)
            assertTrue((result as Error).exception is NetworkException)
            assertEquals(networkError, result.exception.message)
        }

    @Test
    fun `given unknown error, when getMovieDetails is called, then return Error with DataException`() =
        runBlocking {
            // Mock API service response
            val errorBody = CommonConstant.UnknownError.toResponseBody(null)
            val response = Response.error<MovieDetailModel>(errorCode, errorBody)
            coEvery { service.getMovieDetails(movieId) } returns response

            // Perform the test
            val result = repository.getMovieDetails(movieId)

            // Verify the result
            assertTrue(result is Error)
            assertTrue((result as Error).exception is DataException)
            assertEquals(errorCode, response.code())
            assertEquals(CommonConstant.UnknownError, result.exception.message)
        }

    @Test
    fun `given API error response, when getMovieDetails is called, then return Error with ApiException`() =
        runBlocking {
            // Mock API service response
            val response: Response<MovieDetailModel> = Response.success(null)
            coEvery { service.getMovieDetails(movieId) } returns response

            // Perform the test
            val result = repository.getMovieDetails(movieId)

            // Verify the result
            assertTrue(result is Error)
            assertTrue((result as Error).exception is ApiException)
            assertEquals(response.errorBody()?.toString(), result.exception.message)
        }
}
