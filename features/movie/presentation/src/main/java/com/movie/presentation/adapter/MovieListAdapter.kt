package com.movie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movie.domain.displaymodel.MovieListDisplayModel
import com.movie.presentation.databinding.ItemMovielistBinding
import com.movie.presentation.viewholder.MovieListViewHolder

class MovieListAdapter : RecyclerView.Adapter<MovieListViewHolder>() {
    private var movieDataList = listOf<MovieListDisplayModel>()
    private var onItemClickListener: ((MovieListDisplayModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieListDisplayModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding =
            ItemMovielistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieDataList.size
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(movieDataList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(movieDataList[position]) }
        }
    }

    fun refreshData(data: List<MovieListDisplayModel>) {
        movieDataList = data
        notifyItemRangeInserted(0, data.size);
    }
}