package com.movie.presentation.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.movie.domain.model.displaymodel.MovieListDisplayModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.lang.reflect.Type

class Helper {
    suspend fun loadJsonFromAssets(
        dispatcher: CoroutineDispatcher,
        context: Context
    ): List<MovieListDisplayModel> {
        return withContext(dispatcher) {
            val jsonString: String?
            val inputStream: InputStream = context.assets.open("datalist.json")
            jsonString = inputStream.bufferedReader().use { it.readText() }
            val itemType: Type = object : TypeToken<List<MovieListDisplayModel>>() {}.type
            Gson().fromJson(jsonString, itemType)
        }
    }
}