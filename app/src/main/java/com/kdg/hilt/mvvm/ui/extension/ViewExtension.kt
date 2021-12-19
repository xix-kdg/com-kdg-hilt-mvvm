package com.kdg.hilt.mvvm.ui.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadImage(imageUrl: String, placeholder: Int) {
    Glide.with(this.context)
        .load(imageUrl)
        .placeholder(placeholder)
        .error(placeholder)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}

fun ImageView.loadImageCircular(imageUrl: String, placeholder: Int) {
    Glide.with(this.context)
        .load(imageUrl)
        .circleCrop()
        .placeholder(placeholder)
        .error(placeholder)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}
