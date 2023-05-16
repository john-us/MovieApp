package com.movie.domain

import com.movie.common.network.DataException
import com.movie.common.network.Result
import com.movie.data.model.MovieDetailModel
import com.movie.data.repository.MovieRepository
import com.movie.domain.displaymodel.MovieDetailDisplayModel
import com.movie.domain.mapper.MovieDetailsMapper
import com.movie.domain.usecase.MovieDetailsUseCase
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailUseCaseTest {
    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieDetailsMapper: MovieDetailsMapper
    private lateinit var movieDetailsUseCase: MovieDetailsUseCase

    @Before
    fun setup() {
        movieDetailsUseCase = MovieDetailsUseCase(movieRepository, movieDetailsMapper)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMovieDetail should return movie detail`() = runTest {
        val expectedJsonResponse = Result.Success(
            MovieDetailModel(
                backdropPath = "/3CxUndGhUcZdt1Zggjdb2HkLLQX.jpg",
                id = 640146,
                originalTitle = "Ant-Man and the Wasp: Quantumania",
                releaseDate = "2023-02-15",
                status = "",
                title = "Ant-Man and the Wasp: Quantumania"
            ),
        )
        Mockito.`when`(movieRepository.getMovieDetails(640146)).thenReturn(expectedJsonResponse)
        val expectedMovieDetail = Result.Success(
            MovieDetailDisplayModel(
                "/3CxUndGhUcZdt1Zggjdb2HkLLQX.jpg",
                640146,
                "Ant-Man and the Wasp: Quantumania",
                "2023-02-15",
                "",
                "Ant-Man and the Wasp: Quantumania"
            )

        )
        // When
        val result = movieDetailsUseCase.getMovieDetails(640146)

        // Then
        TestCase.assertEquals(expectedMovieDetail, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMovieDetail returns error on repository failure`() = runTest {
        // Mock the repository response
        val errorMessage = "Failed to fetch movie detail"
        Mockito.`when`(movieRepository.getMovieDetails(640146))
            .thenReturn(Result.Error(DataException(errorMessage)))
        // Call the use case method
        when (val result = movieDetailsUseCase.getMovieDetails(640146)) {
            is Result.Error -> {
                TestCase.assertEquals(errorMessage, result.exception.message)
            }

            else -> {}
        }
    }
}