package com.movie.data.di

import com.movie.data.repository.MovieAPIService
import com.movie.data.repository.MovieRepository
import com.movie.data.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MovieRepositoryModule {
    @Provides
    fun provideMovieAPIService(retrofit: Retrofit): MovieAPIService {
        return retrofit.create(MovieAPIService::class.java)
    }

    @Provides
    fun provideMovieRepository(apiService: MovieAPIService): MovieRepository {
        return MovieRepositoryImpl(apiService)
    }
}