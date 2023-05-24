package com.movie.data.repository


import com.movie.data.BuildConfig
import com.movie.data.model.api.MovieDetailModel
import com.movie.data.model.api.MovieListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPIService {
    @GET("movie/popular")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Long = 1
    ): Response<MovieListModel>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): Response<MovieDetailModel>
}