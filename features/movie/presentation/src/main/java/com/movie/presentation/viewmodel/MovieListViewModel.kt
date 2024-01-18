package com.movie.presentation.viewmodel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.common.baseresponse.Result
import com.movie.domain.usecase.MovieListUseCase
import com.movie.presentation.mapper.MovieDisplayMapper
import com.movie.presentation.model.MovieListDisplayModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
    private val movieDisplayMapper: MovieDisplayMapper,
) : ViewModel() {
    private val _moviesList = MutableStateFlow<Result<List<MovieListDisplayModel>>>(Result.Loading)
    val movieList: StateFlow<Result<List<MovieListDisplayModel>>> = _moviesList

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadMovieList() {
        viewModelScope.launch {
            _moviesList.value = movieDisplayMapper.mapToListDisplayModel(movieListUseCase())
        }
    }
}