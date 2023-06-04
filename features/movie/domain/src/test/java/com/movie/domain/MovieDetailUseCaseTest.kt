package com.movie.domain

import com.google.gson.Gson
import com.movie.common.network.DataException
import com.movie.common.network.Result
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.domain.reprositorycontract.IMovieRepository
import com.movie.domain.usecase.MovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths

class MovieDetailUseCaseTest {
    private lateinit var movieRepository: IMovieRepository
    private val movieId: Long = 640146
    private lateinit var movieDetailsUseCase: MovieDetailsUseCase

    @Before
    fun setup() {
        movieRepository = mockk()
        movieDetailsUseCase = MovieDetailsUseCase(movieRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMovieDetail should return movie detail`() = runTest {

        val jsonFilePath = "src/test/resources/moviedetaildisplay.json"
        val jsonString = String(withContext(Dispatchers.IO) {
            Files.readAllBytes(Paths.get(jsonFilePath))
        })

        val gson = Gson()
        val expectedJsonResponse = Result.Success(
            gson.fromJson(jsonString, MovieDetailDisplayModel::class.java)
        )
        coEvery { movieRepository.getMovieDetails(movieId) } returns expectedJsonResponse

        // When
        val result = movieDetailsUseCase.invoke(movieId)
        // Then
        TestCase.assertEquals(expectedJsonResponse, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMovieDetail returns error on repository failure`() = runTest {
        // Mock the repository response
        val errorMessage = "Failed to fetch movie detail"
        coEvery { movieRepository.getMovieDetails(movieId) } returns Result.Error(
            DataException(
                errorMessage
            )
        )

        // Call the use case method
        when (val result = movieDetailsUseCase.invoke(movieId)) {
            is Result.Error -> {
                TestCase.assertEquals(errorMessage, result.exception.message)
            }

            else -> {
            }
        }
    }
}
