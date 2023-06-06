package com.movie.presentation.ui.screen

import CustomImage
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.movie.common.network.Result
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.presentation.R
import com.movie.presentation.viewmodel.MovieDetailViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun MovieDetailScreen(
    movieId: Long,
) {
    val context = LocalContext.current
    val viewModel: MovieDetailViewModel = hiltViewModel()
    val movieDetail by viewModel.movieDetail.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadMovieDetail(movieId = movieId)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (movieDetail) {
            is Result.Loading ->
                CircularProgressIndicator(modifier = Modifier.size(dimensionResource(id = R.dimen.space_50)))

            is Result.Success ->
                MovieDetailUI(movieDetail = (movieDetail as Result.Success<MovieDetailDisplayModel>).data)

            is Result.Error -> Toast.makeText(
                context,
                context.getString(R.string.error_fetching_movie_detail),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Composable
fun MovieDetailUI(movieDetail: MovieDetailDisplayModel) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (image, titleLabel, title, releaseDateLabel, releaseDate) = createRefs()

        CustomImage(
            url = movieDetail.backdropPath,
            fallbackResId = R.drawable.ic_launcher_background,
            defaultResId = R.drawable.ic_launcher_background,
            contentDescription = stringResource(
                id = R.string.background_image
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.space_200))
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        val space15 = dimensionResource(R.dimen.space_15)

        Text(text = stringResource(id = R.string.title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(titleLabel) {
                    top.linkTo(
                        image.bottom,
                        margin = space15
                    )
                }
                .padding(
                    start = dimensionResource(id = R.dimen.space_15),
                    end = dimensionResource(id = R.dimen.space_5)
                )

        )
        val space10 = dimensionResource(R.dimen.space_10)
        Text(text = movieDetail.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(
                        image.bottom,
                        margin = space15
                    )
                    start.linkTo(
                        titleLabel.end
                    )
                    end.linkTo(
                        parent.end,
                        margin = space10
                    )
                    width = Dimension.fillToConstraints

                }


        )

        Text(text = stringResource(id = R.string.release_date),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(releaseDateLabel) {
                    top.linkTo(
                        title.bottom,
                        margin = space10
                    )
                }
                .padding(
                    start = dimensionResource(id = R.dimen.space_15),
                    end = dimensionResource(id = R.dimen.space_5)
                )

        )
        Text(text = movieDetail.releaseDate,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .constrainAs(releaseDate) {
                    top.linkTo(
                        title.bottom,
                        margin = space10
                    )
                    start.linkTo(
                        releaseDateLabel.end
                    )
                }


        )

    }
}