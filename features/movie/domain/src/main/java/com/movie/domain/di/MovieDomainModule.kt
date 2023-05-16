package com.movie.domain.di

import com.movie.domain.mapper.MovieDetailsMapper
import com.movie.domain.mapper.MovieListMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MovieDomainModule {
    @Provides
    fun provideMovieListMapper(): MovieListMapper {
        return MovieListMapper()
    }

    @Provides
    fun provideMovieDetailsMapper(): MovieDetailsMapper {
        return MovieDetailsMapper()
    }
}
