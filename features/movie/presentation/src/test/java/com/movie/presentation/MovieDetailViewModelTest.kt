package com.movie.presentation

import com.google.gson.Gson
import com.movie.common.constant.CommonConstant
import com.movie.common.network.NetworkException
import com.movie.common.network.Result
import com.movie.domain.model.displaymodel.MovieDetailDisplayModel
import com.movie.domain.usecase.MovieDetailsUseCase
import com.movie.presentation.viewmodel.MovieDetailViewModel
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
class MovieDetailViewModelTest {
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val movieId: Long = 640146
    private val delayTime: Long = 1000

    private lateinit var movieDetailsUseCase: MovieDetailsUseCase


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieDetailsUseCase = mockk()
        movieDetailViewModel = MovieDetailViewModel(movieDetailsUseCase)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }


    @Test
    fun `Movie Detail data success`() = runTest {
        val jsonFilePath = "src/test/resources/moviedetaildisplay.json"
        val jsonString = String(withContext(Dispatchers.IO) {
            Files.readAllBytes(Paths.get(jsonFilePath))
        })

        val expectedData = Result.Success(
            Gson().fromJson(jsonString, MovieDetailDisplayModel::class.java)
        )
        coEvery { movieDetailsUseCase.invoke(movieId) } returns expectedData

        movieDetailViewModel.loadMovieDetail(movieId)

        advanceTimeBy(delayTime)

        // Assert that the data is updated correctly
        assertEquals(expectedData, movieDetailViewModel.movieDetail.value)
    }


    @Test
    fun `Movie detail data error`() = runTest {
        val expectedData = Result.Error(
            NetworkException(CommonConstant.UnknownError)
        )
        coEvery { movieDetailsUseCase.invoke(movieId) } returns expectedData

        movieDetailViewModel.loadMovieDetail(movieId)

        advanceTimeBy(delayTime)

        // Assert that the data is updated correctly
        assertEquals(expectedData, movieDetailViewModel.movieDetail.value)
    }
}
