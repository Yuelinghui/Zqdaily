package com.zdaily.ui.home.protocol;


import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by yuelinghui on 17/12/7.
 */

public interface WebTextService {
    @GET
    Observable<String> getWebCss(@Url String url);
}
