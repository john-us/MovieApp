package com.movie.presentation

import androidx.compose.material.MaterialTheme
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.movie.domain.model.MovieDetailDomainModel
import com.movie.presentation.model.MovieDetailDisplayModel
import com.movie.presentation.ui.screen.MovieDetailUI
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
class MovieDetailScreenShotTest {
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
    fun launchMovieDetailScreen() {
        paparazzi.snapshot {
            val displayModel = MovieDetailDisplayModel(
                backdropPath = "https://image.tmdb.org/t/p/original//4XM8DUTQb3lhLemJC51Jx4a2EuA.jpg",
                id = 385687,
                originalTitle = "Fast X",
                releaseDate = "2023-05-17",
                status = "Released",
                title = "Fast X"
            )
            MaterialTheme {
                MovieDetailUI(displayModel)
            }
        }
    }
}