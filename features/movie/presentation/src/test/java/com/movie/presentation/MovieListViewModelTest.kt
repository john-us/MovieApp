package com.movie.presentation

import com.google.gson.Gson
import com.movie.common.constant.CommonConstant
import com.movie.common.network.NetworkException
import com.movie.common.network.Result
import com.movie.domain.model.MovieListDisplayModel
import com.movie.domain.usecase.MovieListUseCase
import com.movie.presentation.viewmodel.MovieListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
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
    private val testDispatcher = StandardTestDispatcher()
    private val delayTime: Long = 1000

    private lateinit var movieListUseCase: MovieListUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieListUseCase = mockk()
        movieListViewModel = MovieListViewModel(movieListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `Movie data success`() = runTest {
        // Read the movie list display JSON from the local file
        val displayJsonFilePath = "src/test/resources/movielistdisplay.json"
        val displayJsonString = String(withContext(Dispatchers.IO) {
            Files.readAllBytes(Paths.get(displayJsonFilePath))
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
    fun `Movie data error`() = runTest {
        val expectedData = Result.Error(
            NetworkException(CommonConstant.UnknownError)
        )
        coEvery { movieListUseCase() } returns expectedData

        movieListViewModel.loadMovieList()

        advanceTimeBy(delayTime)

        // Assert that the data is updated correctly
        assertEquals(expectedData, movieListViewModel.movieList.value)
    }
}
