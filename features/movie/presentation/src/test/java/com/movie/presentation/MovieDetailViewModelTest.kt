package com.movie.presentation

import com.movie.common.network.NetworkException
import com.movie.common.network.Result
import com.movie.domain.displaymodel.MovieDetailDisplayModel
import com.movie.domain.usecase.MovieDetailsUseCase
import com.movie.presentation.viewmodel.MovieDetailViewModel
import junit.framework.TestCase
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var movieDetailsUseCase: MovieDetailsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieDetailViewModel = MovieDetailViewModel(movieDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Movie Detail data success`() = runBlockingTest {
        val expectedData = Result.Success(
            MovieDetailDisplayModel(
                "/3CxUndGhUcZdt1Zggjdb2HkLLQX.jpg",
                640146,
                "Ant-Man and the Wasp: Quantumania",
                "2023-02-15",
                "",
                "Ant-Man and the Wasp: Quantumania"
            )
        )
        Mockito.`when`(movieDetailsUseCase.getMovieDetails(640146)).thenReturn(expectedData)

        // Call the method under test
        movieDetailViewModel.loadMovieDetail(640146)

        // Advance the coroutine to execute the code inside viewModelScope.launch
        advanceTimeBy(1000)

        // Assert that the data is updated correctly
        TestCase.assertEquals(expectedData, movieDetailViewModel.movieDetail.value)
    }

    @Test
    fun `Movie detail data error`() = runBlockingTest {
        val expectedData = Result.Error(
            NetworkException("connection time out")
        )
        Mockito.`when`(movieDetailsUseCase.getMovieDetails(640146)).thenReturn(expectedData)

        // Call the method under test
        movieDetailViewModel.loadMovieDetail(640146)

        // Advance the coroutine to execute the code inside viewModelScope.launch
        advanceTimeBy(1000)

        // Assert that the data is updated correctly
        TestCase.assertEquals(expectedData, movieDetailViewModel.movieDetail.value)
    }
}