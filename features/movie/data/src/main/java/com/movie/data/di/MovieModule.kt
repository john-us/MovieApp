package com.movie.data.di

import com.movie.data.repository.MovieAPIService
import com.movie.data.repository.MovieRepository
import com.movie.data.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object MovieRepositoryModule {
    @Provides
    fun provideMovieAPIService(retrofit: Retrofit): MovieAPIService =
        retrofit.create(MovieAPIService::class.java)

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    fun provideMovieRepository(
        apiService: MovieAPIService,
        dispatcher: CoroutineDispatcher
    ): MovieRepository =
        MovieRepositoryImpl(apiService, dispatcher)

}