package com.movie.data.repository

import com.movie.common.baseresponse.Result
import com.movie.data.mapper.MovieMapper
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.domain.model.MovieListDisplayModel
import com.movie.domain.reprositorycontract.IMovieRepository
import com.movie.network.repository.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieAPIService,
    dispatcher: CoroutineDispatcher,
    private val mapper: MovieMapper,
) : BaseRepository(dispatcher), IMovieRepository {

    override suspend fun getMovieList(): Result<List<MovieListDisplayModel>> {
        return fetchApiData({ service.getMovieList() }, mapper::mapToDisplayModel)
    }

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetailDisplayModel> {
        return fetchApiData({ service.getMovieDetails(movieId) }, mapper::mapToDisplayModel)
    }
}
