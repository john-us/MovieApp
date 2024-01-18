package com.movie.presentation

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.movie.presentation.model.MovieListDisplayModel
import com.movie.presentation.ui.screen.MovieListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieListScreenScreenshotTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        maxPercentDifference = 1.0,
    )

    @Before
    fun setup() {
        // Set up the main dispatcher for coroutines
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        //testDispatcher.cancel()
    }

    @Test
    fun launchMovieListScreen() {
        paparazzi.snapshot("movie_list_image") {
            val displayModel = MovieListDisplayModel(
                id = 872585,
                title = "Oppenheimer",
                backdropPath = "https://image.tmdb.org/t/p/original//fm6KqXpk3M2HVveHwCrBSSBaO0V.jpg",
                releaseDate = "2023-07-19"
            )
            MovieListItem(movie = displayModel, onItemClick = {})
        }
    }
}