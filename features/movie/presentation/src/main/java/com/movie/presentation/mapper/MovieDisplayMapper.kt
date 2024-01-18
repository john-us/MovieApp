package com.movie.presentation.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.movie.common.baseresponse.Result
import com.movie.common.constant.CommonConstant
import com.movie.domain.model.MovieDetailDomainModel
import com.movie.domain.model.MovieListDomainModel
import com.movie.presentation.model.MovieDetailDisplayModel
import com.movie.presentation.model.MovieListDisplayModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MovieDisplayMapper @Inject constructor() {
    fun mapToDetailDisplayModel(movieDetailDomainModel: Result<MovieDetailDomainModel>): Result<MovieDetailDisplayModel> =

        Result.Success((movieDetailDomainModel as Result.Success<MovieDetailDomainModel>).data.let {
            MovieDetailDisplayModel(
                id = it.id,
                backdropPath = CommonConstant.IMAGE_URL + it.backdropPath,
                releaseDate = it.releaseDate,
                title = it.title,
                status = it.status,
                originalTitle = it.originalTitle
            )
        })


    @RequiresApi(Build.VERSION_CODES.O)
    fun mapToListDisplayModel(movieListDomainModel: Result<List<MovieListDomainModel>>): Result<List<MovieListDisplayModel>> =
        Result.Success((movieListDomainModel as Result.Success<List<MovieListDomainModel>>).data.let { movieList ->
            movieList.map { movie ->
                movie.let {
                    MovieListDisplayModel(
                        id = it.id,
                        title = it.title,
                        backdropPath = CommonConstant.IMAGE_URL + it.backdropPath,
                        releaseDate = formatDate(it.releaseDate)
                    )
                }
            }
        })

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDate(originalDate: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
        val date = LocalDate.parse(originalDate, inputFormatter)
        return outputFormatter.format(date)
    }

}