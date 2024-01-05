package com.movie.presentation

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.movie.domain.model.MovieListDisplayModel
import com.movie.presentation.ui.screen.MovieListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieListScreenScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        renderingMode = SessionParams.RenderingMode.NORMAL,
        showSystemUi = false,
        maxPercentDifference = 1.0,
    )
    @Before
    fun setup() {
        // Set up the main dispatcher for coroutines
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun launchMovieListScreen() {
        paparazzi.snapshot {
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