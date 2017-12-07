package com.zdaily.ui.home.protocol

import com.zdaily.ui.home.entity.HomeDetailInfo
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by yuelinghui on 17/12/6.
 */
interface DetailService {
    @GET("{id}")
    abstract fun getDetail(@Path("id")id: Int) : Observable<HomeDetailInfo>
}