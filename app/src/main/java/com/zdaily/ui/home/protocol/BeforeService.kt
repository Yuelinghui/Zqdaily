package com.zdaily.ui.home.protocol

import com.zdaily.ui.home.entity.ArticleInfo
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by yuelinghui on 17/12/6.
 */
interface BeforeService {
    @GET("before/{date}")
    fun getBefore(@Path("date")beforeDate: String) : Observable<ArticleInfo>
}