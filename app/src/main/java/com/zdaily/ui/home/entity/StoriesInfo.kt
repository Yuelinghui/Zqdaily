package com.zdaily.ui.home.entity

import java.io.Serializable

/**
 * Created by yuelinghui on 17/12/6.
 */
data class StoriesInfo(
        val images: List<String>,
        val type: Int,
        val id: Int,
        val ga_prefix: String,
        val title: String,
        val image: String,
        val multipic: Boolean
) : Serializable