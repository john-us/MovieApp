package com.movie.domain


import com.movie.common.network.DataException
import com.movie.common.network.Result
import com.movie.data.model.MovieListModel
import com.movie.data.repository.MovieRepository
import com.movie.domain.displaymodel.MovieListDisplayModel
import com.movie.domain.mapper.MovieListMapper
import com.movie.domain.usecase.MovieListUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieListUseCaseTest {
    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieListMapper: MovieListMapper
    private lateinit var movieListUseCase: MovieListUseCase

    @Before
    fun setup() {
        movieListUseCase = MovieListUseCase(movieRepository, movieListMapper)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun `execute should return movie list`() = runTest {
        val expectedJsonResponse = Result.Success(
            MovieListModel(
                1, listOf(
                    MovieListModel.Movie(
                        1, "Wow", "/3CxUndGhUcZdt1Zggjdb2HkLLQX.jpg", "01-01-111"
                    ), MovieListModel.Movie(
                        2,
                        "Awesome",
                        "/hiHGRbyTcbZoLsYYkO4QiCLYe34.jpg",
                        "02-02-2222"
                    )
                ), 1, 2
            ),
        )
        `when`(movieRepository.getMovieList()).thenReturn(expectedJsonResponse)
        val expectedMovies = Result.Success(
            listOf(
                MovieListDisplayModel(1, "Wow", "/3CxUndGhUcZdt1Zggjdb2HkLLQX.jpg", "01-01-111"),
                MovieListDisplayModel(
                    2,
                    "Awesome",
                    "/hiHGRbyTcbZoLsYYkO4QiCLYe34.jpg",
                    "02-02-2222"
                )
            )
        )
        // When
        val result = movieListUseCase.getMovieList()

        // Then
        assertEquals(expectedMovies, result)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun `getMovieList returns error on repository failure`() = runTest {
        // Mock the repository response
        val errorMessage = "Failed to fetch movies"
        `when`(movieRepository.getMovieList()).thenReturn(Result.Error(DataException(errorMessage)))
        // Call the use case method
        when (val result = movieListUseCase.getMovieList()) {
            is Result.Error -> {
                assertEquals(errorMessage, result.exception.message)
            }

            else -> {}
        }
    }
}
