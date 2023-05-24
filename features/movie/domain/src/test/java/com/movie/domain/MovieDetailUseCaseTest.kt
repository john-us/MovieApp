package com.movie.domain

import com.google.gson.Gson
import com.movie.common.network.DataException
import com.movie.common.network.Result
import com.movie.data.mapper.MovieDetailsMapper
import com.movie.data.model.display.MovieDetailDisplayModel
import com.movie.data.repository.MovieRepository
import com.movie.domain.usecase.MovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
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
    private lateinit var movieRepository: MovieRepository
    private val movieId: Long = 640146
    private lateinit var movieDetailsMapper: MovieDetailsMapper
    private lateinit var movieDetailsUseCase: MovieDetailsUseCase

    @Before
    fun setup() {
        movieRepository = mockk()
        movieDetailsMapper = spyk(MovieDetailsMapper()) // Mocking the MovieDetailsMapper
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

        val displayJsonFilePath = "src/test/resources/moviedetaildisplay.json"
        val displayJsonString = String(withContext(Dispatchers.IO) {
            Files.readAllBytes(Paths.get(displayJsonFilePath))
        })

        val expectedMovieDetail = Result.Success(
            gson.fromJson(displayJsonString, MovieDetailDisplayModel::class.java)
        )
        // When
        val result = movieDetailsUseCase.invoke(movieId)
        // Then
        TestCase.assertEquals(expectedMovieDetail, result)
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
