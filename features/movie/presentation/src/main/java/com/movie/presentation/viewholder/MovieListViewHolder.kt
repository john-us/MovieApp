package com.movie.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.movie.common.util.loadImage
import com.movie.data.model.display.MovieListDisplayModel
import com.movie.presentation.R
import com.movie.presentation.databinding.ItemMovielistBinding

class MovieListViewHolder(private val binding: ItemMovielistBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movieListDisplayModel: MovieListDisplayModel) {
        binding.apply {
            dateTv.text = movieListDisplayModel.releaseDate
            nameTv.text = movieListDisplayModel.title
            image.loadImage(
                movieListDisplayModel.backdropPath,
                R.drawable.ic_launcher_background
            )
        }

    }
}