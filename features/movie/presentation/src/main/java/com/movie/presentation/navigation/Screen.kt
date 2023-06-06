package com.movie.presentation.navigation

import com.movie.presentation.constant.movieDetail
import com.movie.presentation.constant.movieList

sealed class Screen(val route: String) {
    object MovieList : Screen(movieList)
    object MovieDetail : Screen(movieDetail)
}