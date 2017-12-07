package com.zdaily.ui.home.entity

import java.io.Serializable

/**
 * Created by yuelinghui on 17/12/6.
 */
data class HomeDetailInfo(
        val body: String,
        val image_source: String,
        val title: String,
        val image: String,
        val share_url: String,
        val ga_prefix: Int,
        val images: List<String>?,
        val type: Int,
        val id: Int,
        val css: List<String>?
        ) : Serializable