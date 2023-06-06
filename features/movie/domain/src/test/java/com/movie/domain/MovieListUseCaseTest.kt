package com.movie.domain

import com.google.gson.Gson
import com.movie.common.network.DataException
import com.movie.common.network.Result
import com.movie.domain.model.MovieListDisplayModel
import com.movie.domain.reprositorycontract.IMovieRepository
import com.movie.domain.usecase.MovieListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths

class MovieListUseCaseTest {
    private lateinit var movieRepository: IMovieRepository
    private lateinit var movieListUseCase: MovieListUseCase

    @Before
    fun setup() {
        movieRepository = mockk()
        movieListUseCase = MovieListUseCase(movieRepository)
    }


    @Test
    fun `given valid movie list display response, when invoke is called, then return Success with MovieListDisplayModel`() =
        runBlocking {
            // Read the movie list display JSON from the local file
            val displayJsonString = String(withContext(Dispatchers.IO) {
                Files.readAllBytes(Paths.get(movieListDisplayPath))
            })

            // Parse the JSON into a List<MovieListDisplayModel> using Gson
            val expectedMovies = Result.Success(
                Gson().fromJson(
                    displayJsonString,
                    Array<MovieListDisplayModel>::class.java
                ).toList()
            )
            coEvery { movieRepository.getMovieList() } returns expectedMovies
            // When
            val result = movieListUseCase()

            // Then
            assertEquals(expectedMovies, result)
        }

    @Test
    fun `given network error, when invoke is called, then return Error with message`() =
        runBlocking {
            // Mock the repository response
            coEvery { movieRepository.getMovieList() } returns Result.Error(
                DataException(errorMessage)
            )

            // Call the use case method
            val result = movieListUseCase()
            assertTrue(result is Result.Error)
            assertEquals(errorMessage, (result as Result.Error).exception.message)
        }
}
