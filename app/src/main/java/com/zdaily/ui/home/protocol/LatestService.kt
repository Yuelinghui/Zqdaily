package com.zdaily.ui.home.protocol

import com.zdaily.ui.home.entity.ArticleInfo
import retrofit2.http.GET
import rx.Observable

/**
 * Created by yuelinghui on 17/12/6.
 */
interface LatestService {
    @GET("latest")
    abstract fun getLatest() : Observable<ArticleInfo>
}