package com.movie.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.movie.presentation.constant.movieId
import com.movie.presentation.ui.screen.MovieDetailScreen
import com.movie.presentation.ui.screen.MovieListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.MovieList.route) {
        composable(Screen.MovieList.route) {
            MovieListScreen(navController = navController)
        }
        composable(
            "${Screen.MovieDetail.route}/{${movieId}}",
            arguments = listOf(navArgument(movieId) { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong(movieId)
            movieId?.let {
                MovieDetailScreen(navController = navController, movieId = it)
            }
        }
    }
}

