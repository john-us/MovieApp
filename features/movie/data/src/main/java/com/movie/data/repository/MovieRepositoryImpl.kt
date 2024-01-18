package com.movie.data.repository

import com.movie.common.baseresponse.Result
import com.movie.data.mapper.MovieMapper
import com.movie.domain.model.MovieDetailDomainModel
import com.movie.domain.model.MovieListDomainModel
import com.movie.domain.reprositorycontract.IMovieRepository
import com.movie.network.repository.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieAPIService,
    dispatcher: CoroutineDispatcher,
    private val mapper: MovieMapper,
) : BaseRepository(dispatcher), IMovieRepository {

    override suspend fun getMovieList(): Result<List<MovieListDomainModel>> {
        return fetchApiData({ service.getMovieList() }, mapper::mapToDomainModel)
    }

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetailDomainModel> {
        return fetchApiData({ service.getMovieDetails(movieId) }, mapper::mapToDomainModel)
    }
}
