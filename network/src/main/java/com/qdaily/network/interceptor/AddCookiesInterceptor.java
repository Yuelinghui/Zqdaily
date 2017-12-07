package com.qdaily.network.interceptor;

import android.util.Log;

import com.qdaily.frame.util.CookiesUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yuelinghui on 17/8/2.
 */

public class AddCookiesInterceptor implements Interceptor {

    private static final String KEY_COOKIES = "Cookie";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (CookiesUtil.getCookies() != null) {
            for (String header : CookiesUtil.getCookies()) {
                builder.addHeader(KEY_COOKIES, header);
            }
        }
        String url = chain.request().url().toString();
        Log.e("网络请求", url);
        return chain.proceed(builder.build());
    }
}
