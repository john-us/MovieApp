package com.movie.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.movie.presentation.R
import com.movie.presentation.databinding.ActivityMoviedetailBinding
import com.movie.presentation.ui.MovieDetailFragment.Companion.MOVIE_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviedetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviedetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.movie_detail_fr,
                MovieDetailFragment.newInstance(intent.getLongExtra(MOVIE_ID, 1))
            ).commit()
    }

    companion object {
        fun createIntent(context: Context, movieId: Long): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }
}