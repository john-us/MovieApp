package com.movie.presentation

import com.google.gson.Gson
import com.movie.common.apiexception.NetworkException
import com.movie.common.baseresponse.Result
import com.movie.common.constant.NetworkConstant
import com.movie.domain.model.MovieListDisplayModel
import com.movie.domain.usecase.MovieListUseCase
import com.movie.presentation.viewmodel.MovieListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths

@ExperimentalCoroutinesApi
class MovieListViewModelTest {
    private lateinit var movieListViewModel: MovieListViewModel
    //private val testDispatcher = StandardTestDispatcher()
    private lateinit var movieListUseCase: MovieListUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        movieListUseCase = mockk()
        movieListViewModel = MovieListViewModel(movieListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        //testDispatcher.cancel()
    }

    @Test
    fun `given movie data available, when loadMovieList is called, then movie data successfully displayed`() =
        runTest {
            // Read the movie list display JSON from the local file
            val displayJsonString = String(withContext(Dispatchers.IO) {
                Files.readAllBytes(Paths.get(movieListDisplayPath))
            })

            // Parse the JSON into a List<MovieListDisplayModel> using Gson
            val expectedData = Result.Success(
                Gson().fromJson(
                    displayJsonString,
                    Array<MovieListDisplayModel>::class.java
                ).toList()
            )
            coEvery { movieListUseCase() } returns expectedData

            movieListViewModel.loadMovieList()

            advanceTimeBy(delayTime)

            // Assert that the data is updated correctly
            assertEquals(expectedData, movieListViewModel.movieList.value)
        }

    @Test
    fun `given movie data unavailable, when loadMovieList is called, then error displayed`() =
        runTest {
            val expectedData = Result.Error(
                NetworkException(NetworkConstant.UNKNOWN_ERROR)
            )
            coEvery { movieListUseCase() } returns expectedData

            movieListViewModel.loadMovieList()

            advanceTimeBy(delayTime)

            // Assert that the data is updated correctly
            assertEquals(expectedData, movieListViewModel.movieList.value)
        }
}
