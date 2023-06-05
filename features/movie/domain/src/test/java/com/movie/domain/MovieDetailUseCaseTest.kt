package com.movie.domain

import com.google.gson.Gson
import com.movie.common.network.DataException
import com.movie.common.network.Result
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.domain.reprositorycontract.IMovieRepository
import com.movie.domain.usecase.MovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths

class MovieDetailUseCaseTest {
    private lateinit var movieRepository: IMovieRepository
    private lateinit var movieDetailsUseCase: MovieDetailsUseCase

    @Before
    fun setup() {
        movieRepository = mockk()
        movieDetailsUseCase = MovieDetailsUseCase(movieRepository)
    }

    @Test
    fun `given valid movie detail display response, when invoke is called, then return Success with MovieDetailDisplayModel`() =
        runBlocking {
            val jsonString = String(withContext(Dispatchers.IO) {
                Files.readAllBytes(Paths.get(movieDetailDisplayPath))
            })

            val gson = Gson()
            val expectedJsonResponse = Result.Success(
                gson.fromJson(jsonString, MovieDetailDisplayModel::class.java)
            )
            coEvery { movieRepository.getMovieDetails(movieId) } returns expectedJsonResponse

            // When
            val result = movieDetailsUseCase(movieId)
            // Then
            assertEquals(expectedJsonResponse, result)
        }

    @Test
    fun `given network error, when invoke is called, then return Error with message`() =
        runBlocking {
            // Mock the repository response

            coEvery { movieRepository.getMovieDetails(movieId) } returns Result.Error(
                DataException(
                    errorMessage
                )
            )

            // Call the use case method
            val result = movieDetailsUseCase(movieId)
            assertEquals(errorMessage, (result as Result.Error).exception.message)
        }
}
