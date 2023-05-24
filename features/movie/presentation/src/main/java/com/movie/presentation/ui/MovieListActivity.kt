package com.movie.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.movie.presentation.R
import com.movie.presentation.databinding.ActivityMovielistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovielistBinding
    private lateinit var backPressedCallback: OnBackPressedCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovielistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleBackPress()
    }

    private fun handleBackPress() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val currentFragment = navHostFragment.childFragmentManager.fragments[0]

                if (currentFragment is MovieDetailFragment) {
                    // If the current fragment is the DetailFragment, navigate back to the ListingFragment
                    navHostFragment.navController.navigate(R.id.action_movieDetailFragment_to_movieListFragment)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, MovieListActivity::class.java).apply { }
        }
    }
}