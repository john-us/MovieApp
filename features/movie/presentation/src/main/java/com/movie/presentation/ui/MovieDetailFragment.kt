package com.movie.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.movie.presentation.R
import com.movie.presentation.ui.screen.MovieDetailScreen
import com.movie.presentation.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var movieId: Long? = null
    private lateinit var movieDetailViewModel: MovieDetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieDetailViewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        movieId = arguments?.getLong(getString(R.string.movie_id))
        return ComposeView(requireContext()).apply {
            setContent {
                movieId?.let {
                    MovieDetailScreen(
                        viewModel = movieDetailViewModel,
                        movieId = it,
                        context = context
                    )
                } ?: Toast.makeText(
                    context,
                    getString(R.string.movie_id_not_found),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}