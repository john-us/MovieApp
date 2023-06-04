package com.movie.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.movie.presentation.ui.screen.MovieListScreen
import com.movie.presentation.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val viewModel: MovieListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MovieListScreen(viewModel, onItemClick = { movieId ->
                    findNavController().navigate(
                        MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                            movieId.id
                        )
                    )
                }, context = context)
            }
        }

    }
}