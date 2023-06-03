package com.movie.presentation.ui.screen

import CustomImage
import android.content.Context
import android.content.res.Configuration
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.movie.common.network.Result
import com.movie.domain.model.displaymodel.MovieListDisplayModel
import com.movie.presentation.R
import com.movie.presentation.constant.DARK_MODE
import com.movie.presentation.constant.LIGHT_MODE
import com.movie.presentation.viewmodel.MovieListViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel,
    onItemClick: (MovieListDisplayModel) -> Unit,
    @ApplicationContext context: Context
) {
    val movieList by viewModel.movieList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadMovieList()
    }


    when (movieList) {
        is Result.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(dimensionResource(id = R.dimen.space_60)))
            }
        }

        is Result.Success -> {
            MovieList(
                movieList = (movieList as Result.Success<List<MovieListDisplayModel>>).data,
                onItemClick
            )
        }

        is Result.Error -> {
            Toast.makeText(
                context,
                context.getString(R.string.error_fetching_movie),
                Toast.LENGTH_LONG
            ).show()
        }
    }


}

@Composable
fun MovieList(
    movieList: List<MovieListDisplayModel>,
    onItemClick: (MovieListDisplayModel) -> Unit
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
fun MovieListItem(movie: MovieListDisplayModel, click: () -> Unit) {
    Row(modifier = Modifier.padding(all = dimensionResource(id = R.dimen.space_10))) {
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

        Column(modifier = Modifier
            .clickable {
                // callback for list item click
                click()
            }) {
            Text(
                text = movie.title,
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
            Text(
                text = movie.releaseDate,
                style = MaterialTheme.typography.bodyMedium
            )


        }
    }
}

@Preview(name = LIGHT_MODE)
@Preview(name = DARK_MODE, uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewComposableList() {

}