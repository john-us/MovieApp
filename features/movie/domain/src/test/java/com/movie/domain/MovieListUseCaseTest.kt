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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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

    @ExperimentalCoroutinesApi
    @Test
    fun `execute should return movie list`() = runTest {
        val gson = Gson()

        // Read the movie list display JSON from the local file
        val displayJsonFilePath = "src/test/resources/movielistdisplay.json"
        val displayJsonString = String(withContext(Dispatchers.IO) {
            Files.readAllBytes(Paths.get(displayJsonFilePath))
        })

        // Parse the JSON into a List<MovieListDisplayModel> using Gson
        val expectedMovies = Result.Success(
            gson.fromJson(
                displayJsonString,
                Array<MovieListDisplayModel>::class.java
            ).toList()
        )
        coEvery { movieRepository.getMovieList() } returns expectedMovies
        // When
        val result = movieListUseCase.invoke()

        // Then
        assertEquals(expectedMovies, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMovieList returns error on repository failure`() = runTest {
        // Mock the repository response
        val errorMessage = "Failed to fetch movies"
        coEvery { movieRepository.getMovieList() } returns Result.Error(DataException(errorMessage))

        // Call the use case method
        when (val result = movieListUseCase.invoke()) {
            is Result.Error -> {
                assertEquals(errorMessage, result.exception.message)
            }

            else -> {
            }
        }
    }
}
