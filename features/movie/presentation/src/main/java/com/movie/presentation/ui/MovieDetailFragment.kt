package com.movie.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.movie.common.network.Result
import com.movie.common.util.loadImage
import com.movie.domain.displaymodel.MovieDetailDisplayModel
import com.movie.presentation.R
import com.movie.presentation.databinding.FragmentMoviedetailBinding
import com.movie.presentation.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMoviedetailBinding
    private var movieId: Long? = null
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getLong(MOVIE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviedetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailViewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        initObservers()
        movieId?.let { movieDetailViewModel.loadMovieDetail(it) }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            movieDetailViewModel.movieDetail.collect { result ->
                when (result) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        bindData(result.data)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "Error fetching movie details", Toast.LENGTH_LONG)
                            .show()
                    }

                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    else -> {}
                }
            }
        }
    }

    private fun bindData(movie: MovieDetailDisplayModel) {
        with(binding) {
            name.text = movie.originalTitle
            releaseDate.text = movie.releaseDate
            status.text = movie.status
            title.text = movie.title
            image.loadImage(movie.backdropPath, R.drawable.ic_launcher_background)
        }
    }

    companion object {
        const val MOVIE_ID = "movie_id"

        @JvmStatic
        fun newInstance(movieId: Long) = MovieDetailFragment().apply {
            arguments = Bundle().apply { putLong(MOVIE_ID, movieId) }
        }
    }
}