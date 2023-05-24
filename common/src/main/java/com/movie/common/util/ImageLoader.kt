package com.movie.common.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.movie.common.constant.CommonConstant


fun ImageView.loadImage(url: String, drawable: Int) {
    Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(drawable)
            .error(drawable)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    ).load(CommonConstant.IMAGE_URL + url).into(this)
}
