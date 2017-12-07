package com.qdaily.network;

import android.content.Context;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by yuelinghui on 17/8/9.
 */

public class NetModel {

    private static final String MEDIA_TYPE = "application/json;charset=utf-8";

    protected Context mContext;

    public NetModel(Context context) {
        mContext = context;
    }

    protected RequestBody createRequestBody(String json) {
        RequestBody body = RequestBody.create(MediaType.parse(MEDIA_TYPE), json);
        return body;
    }
}
