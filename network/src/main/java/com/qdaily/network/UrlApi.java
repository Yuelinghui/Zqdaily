package com.qdaily.network;

/**
 * Created by yuelinghui on 17/8/1.
 */

public class UrlApi {
    private static final String BASE_URL = "http://news-at.zhihu.com/api/4/news/";//"http://115.28.245.167:8080/appstore/servlet/";//"http://app3.qdaily.com/";
//    private static final String TEST1_URL = "http://test1.app.qdaily.com/";
//    private static final String TEST2_URL = "http://test2.app.qdaily.com/";

    private static String mCurrentUrl = BASE_URL;

    public static String getCurrentUrl() {
        return mCurrentUrl;
    }

    public static void setCurrentUrl(String currentUrl) {
        mCurrentUrl = currentUrl;
    }

    public static void initUrl() {
        mCurrentUrl = BASE_URL;
    }
}
