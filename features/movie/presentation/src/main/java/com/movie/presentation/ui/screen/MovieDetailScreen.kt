package com.movie.presentation.ui.screen

import CustomImage
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.movie.common.network.Result
import com.movie.domain.model.MovieDetailDisplayModel
import com.movie.presentation.R
import com.movie.presentation.constant.FontSize
import com.movie.presentation.ui.customcomposable.MovieProgressBar
import com.movie.presentation.ui.customcomposable.MovieText
import com.movie.presentation.viewmodel.MovieDetailViewModel

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
            is Result.Loading -> MovieProgressBar()

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
        MovieText(text = stringResource(id = R.string.title),
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
                ), fontSize = FontSize.fontSize_16)

        val space10 = dimensionResource(R.dimen.space_10)
        MovieText(text = movieDetail.title, modifier = Modifier
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
                bottom.linkTo(
                    titleLabel.bottom
                )
                width = Dimension.fillToConstraints

            })

        MovieText(
            text = stringResource(id = R.string.release_date),
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
                ),
            fontSize = FontSize.fontSize_16,
        )

        MovieText(
            text = movieDetail.releaseDate,
            modifier = Modifier
                .constrainAs(releaseDate) {
                    top.linkTo(
                        title.bottom,
                        margin = space10
                    )
                    start.linkTo(
                        releaseDateLabel.end
                    )
                    bottom.linkTo(
                        releaseDateLabel.bottom
                    )
                },
        )

    }
}