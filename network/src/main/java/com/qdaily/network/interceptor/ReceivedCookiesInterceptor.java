package com.qdaily.network.interceptor;

import android.text.TextUtils;

import com.qdaily.frame.util.CookiesUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by yuelinghui on 17/8/2.
 */

public class ReceivedCookiesInterceptor implements Interceptor {

    private static final String KEY_COOKIE = "Set-Cookie";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!TextUtils.isEmpty(originalResponse.header(KEY_COOKIE))) {
            HashSet<String> headerSet = new HashSet<>();
            for (String header : originalResponse.headers(KEY_COOKIE)) {
                headerSet.add(header);
            }
            CookiesUtil.saveCookies(headerSet);
        }
        return originalResponse;
    }
}
