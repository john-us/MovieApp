package com.movie.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.common.network.Result
import com.movie.domain.model.displaymodel.MovieListDisplayModel
import com.movie.domain.usecase.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase
) : ViewModel() {
    private val _moviesList = MutableStateFlow<Result<List<MovieListDisplayModel>>>(Result.Loading)
    val movieList: StateFlow<Result<List<MovieListDisplayModel>>> = _moviesList

    fun loadMovieList() {
        viewModelScope.launch {
            _moviesList.value = Result.Loading
            val result = movieListUseCase.invoke()
            _moviesList.value = result
        }
    }
}