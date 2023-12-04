package com.movie.network.mapper
import com.movie.network.BuildConfig


object UrlToFileNameMap {
    val map = mapOf(
        "${BuildConfig.BASE_URL}/login/dummy" to "login_response.json",
        "${BuildConfig.BASE_URL}/signup/dummy" to "signup_response.json",
    )
}