package com.movie.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.movie.common.network.Result
import com.movie.presentation.R
import com.movie.presentation.adapter.MovieListAdapter
import com.movie.presentation.databinding.FragmentMovieListBinding
import com.movie.presentation.ui.MovieDetailFragment.Companion.MOVIE_ID
import com.movie.presentation.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var binding: FragmentMovieListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        // Initialize the adapter instance only if it hasn't been initialized before
        if (!::movieListAdapter.isInitialized) {
            movieListAdapter = MovieListAdapter()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieListAdapter
        }
        movieListViewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        initObserver()
        movieListViewModel.loadMovieList()
        initListener()
    }

    private fun initListener() {
        movieListAdapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.action_movieListFragment_to_movieDetailFragment,
                bundleOf(MOVIE_ID to it.id)
            )
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            movieListViewModel.movieList.collect { result ->
                when (result) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        movieListAdapter.refreshData(result.data)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            context,
                            getString(R.string.error_fetching_movie),
                            Toast.LENGTH_LONG
                        ).show()

                    }

                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}