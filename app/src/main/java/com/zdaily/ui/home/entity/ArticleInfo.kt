package com.zdaily.ui.home.entity

import java.io.Serializable

/**
 * Created by yuelinghui on 17/12/6.
 */
data class ArticleInfo(
        val date: String,
        val stories: List<StoriesInfo>,
        val top_stories:List<StoriesInfo>
):Serializable