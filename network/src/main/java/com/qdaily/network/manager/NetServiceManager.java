package com.qdaily.network.manager;

import com.qdaily.network.UrlApi;
import com.qdaily.network.interceptor.AddCookiesInterceptor;
import com.qdaily.network.interceptor.ReceivedCookiesInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yuelinghui on 17/8/1.
 */

public class NetServiceManager {
    private static NetServiceManager mNetManager = null;
    private static Retrofit mRetrofit = null;
    private static OkHttpClient mOkHttpClient;

    private NetServiceManager() {
        mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor()).build();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .baseUrl(UrlApi.getCurrentUrl()).build();
    }

    public synchronized static NetServiceManager getInstance() {
        if (mNetManager == null) {
            mNetManager = new NetServiceManager();
        }
        return mNetManager;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

}
