package com.movie.presentation.ui.screen

import CustomImage
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.movie.common.network.Result
import com.movie.domain.model.MovieListDisplayModel
import com.movie.presentation.R
import com.movie.presentation.constant.FontSize
import com.movie.presentation.navigation.Screen
import com.movie.presentation.ui.customcomposable.MovieProgressBar
import com.movie.presentation.ui.customcomposable.MovieText
import com.movie.presentation.viewmodel.MovieListViewModel

@Composable
fun MovieListScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val viewModel: MovieListViewModel = hiltViewModel()
    val movieList by viewModel.movieList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadMovieList()
    }


    when (movieList) {
        is Result.Loading -> MovieProgressBar()

        is Result.Success -> MovieList(
            movieList = (movieList as Result.Success<List<MovieListDisplayModel>>).data,
            onItemClick = { movie ->
                navController.navigate("${Screen.MovieDetail.route}/${movie.id}")
            }
        )

        is Result.Error -> Toast.makeText(
            context,
            context.getString(R.string.error_fetching_movie),
            Toast.LENGTH_LONG
        ).show()
    }


}

@Composable
fun MovieList(
    movieList: List<MovieListDisplayModel>,
    onItemClick: (MovieListDisplayModel) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(movieList) { movie ->
                MovieListItem(movie = movie) {
                    onItemClick(movie)
                }

            }
        }
    }
}

@Composable
fun MovieListItem(movie: MovieListDisplayModel, onItemClick: (MovieListDisplayModel) -> Unit) {
    Row(modifier = Modifier
        .padding(all = dimensionResource(id = R.dimen.space_10))
        .clickable {
            // callback for list item click
            onItemClick(movie)
        }) {
        CustomImage(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.space_5)))
                .size(dimensionResource(id = R.dimen.space_60)),

            url = movie.backdropPath,
            fallbackResId = R.drawable.ic_launcher_background,
            defaultResId = R.drawable.ic_launcher_background,
            contentDescription = stringResource(id = R.string.background_image)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))

        Column {
            MovieText(
                text = movie.title,
                fontSize = FontSize.fontSize_16
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
            MovieText(text = movie.releaseDate)


        }
    }
}