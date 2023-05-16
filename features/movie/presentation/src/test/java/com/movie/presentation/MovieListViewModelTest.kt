package com.movie.presentation

import com.movie.common.network.NetworkException
import com.movie.common.network.Result
import com.movie.domain.displaymodel.MovieListDisplayModel
import com.movie.domain.usecase.MovieListUseCase
import com.movie.presentation.viewmodel.MovieListViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {
    private lateinit var movieListViewModel: MovieListViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var movieUseCase: MovieListUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieListViewModel = MovieListViewModel(movieUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Movie data success`() = runBlockingTest {
        val expectedData = Result.Success(
            listOf(
                MovieListDisplayModel(
                    1,
                    "Wow",
                    "/3CxUndGhUcZdt1Zggjdb2HkLLQX.jpg",
                    "01-01-111"
                ),
                MovieListDisplayModel(
                    2,
                    "Awesome",
                    "/hiHGRbyTcbZoLsYYkO4QiCLYe34.jpg",
                    "02-02-2222"
                )
            )
        )
        `when`(movieUseCase.getMovieList()).thenReturn(expectedData)

        // Call the method under test
        movieListViewModel.loadMovieList()

        // Advance the coroutine to execute the code inside viewModelScope.launch
        advanceTimeBy(1000)

        // Assert that the data is updated correctly
        assertEquals(expectedData, movieListViewModel.movieList.value)
    }

    @Test
    fun `Movie data error`() = runBlockingTest {
        val expectedData = Result.Error(
            NetworkException("connection time out")
        )
        `when`(movieUseCase.getMovieList()).thenReturn(expectedData)

        // Call the method under test
        movieListViewModel.loadMovieList()

        // Advance the coroutine to execute the code inside viewModelScope.launch
        advanceTimeBy(1000)

        // Assert that the data is updated correctly
        assertEquals(expectedData, movieListViewModel.movieList.value)
    }


}

