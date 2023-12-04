package com.movie.network.interceptor

import android.content.Context
import com.movie.common.constant.NetworkConstant.Companion.CONTENT_TYPE
import com.movie.common.constant.NetworkConstant.Companion.DUMMY
import com.movie.common.constant.NetworkConstant.Companion.OK
import com.movie.network.mapper.UrlToFileNameMap


import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject

class LocalJsonInterceptor @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url.toString()

        if (shouldHandleRequest(requestUrl)) {
            val jsonFileName = mapUrlToFileName(requestUrl)
            val json = loadJsonFromAssets(jsonFileName)

            // Create a response from the JSON content
            val responseBody = json.toResponseBody(CONTENT_TYPE.toMediaTypeOrNull())
            return Response.Builder()
                .code(200)
                .message(OK)
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .body(responseBody)
                .build()
        } else {
            // If the URL is not found in the mapping, proceed with the actual network request.
            return chain.proceed(request)
        }
    }

    private fun shouldHandleRequest(requestUrl: String): Boolean {
        return requestUrl.contains(DUMMY)
    }

    private fun mapUrlToFileName(requestUrl: String): String {
        return UrlToFileNameMap.map[requestUrl] ?: ""
    }

    private fun loadJsonFromAssets(fileName: String): String {
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, Charset.defaultCharset())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }
}
