package com.movie.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.movie.domain.displaymodel.MovieListDisplayModel
import com.movie.presentation.R
import com.movie.presentation.databinding.ItemMovielistBinding
import com.movie.common.util.loadImage

class MovieListViewHolder(private val binding: ItemMovielistBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movieListDisplayModel: MovieListDisplayModel) {
        binding.dateTv.text = movieListDisplayModel.releaseDate
        binding.nameTv.text=movieListDisplayModel.title
        binding.image.loadImage(movieListDisplayModel.backdropPath, R.drawable.ic_launcher_background)
    }
}