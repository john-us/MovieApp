package com.movie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.common.network.NetworkException
import com.movie.common.network.Result
import com.movie.domain.displaymodel.MovieDetailDisplayModel
import com.movie.domain.usecase.MovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase
) : ViewModel() {
    private val _movieDetail = MutableStateFlow<Result<MovieDetailDisplayModel>>(Result.Loading)
    val movieDetail: StateFlow<Result<MovieDetailDisplayModel>> = _movieDetail

    fun loadMovieDetail(movieId: Long) {
        viewModelScope.launch {
            try {
                _movieDetail.value = Result.Loading
                val result = movieDetailsUseCase.invoke(movieId)
                _movieDetail.value = result
            } catch (e: IOException) {
                _movieDetail.value = Result.Error(NetworkException(e.message))
            }
        }
    }

}