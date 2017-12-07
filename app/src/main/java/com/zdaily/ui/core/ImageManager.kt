package com.zdaily.ui.core

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.qdaily.frame.managercenter.MManager

/**
 * Created by yuelinghui on 17/12/6.
 */
class ImageManager:MManager() {

    fun displayImage(context: Context?, url: String, imageView: ImageView?, placeHolder: Int = -1, listener: RequestListener<String, GlideDrawable>? = null) {
        if (context == null || imageView == null || TextUtils.isEmpty(url))
            return

        var activity: Activity? = null

        if (context is Activity) {
            activity = context
        }

        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed)
            return

        val request = Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        if (placeHolder != -1) {
            request.placeholder(placeHolder)
        }
        if (listener != null)
            request.listener(listener)
        if (url.contains(".gif")) {
            request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
        }

        request.into(imageView)
    }

}