package com.qdaily.frame.core;

import rx.Subscriber;

/**
 * Created by yuelinghui on 17/8/1.
 */

public abstract class NetListener<T> extends Subscriber<T> {
    private static final String TAG = "NetListener";

    public NetListener() {
    }

    @Override
    public void onCompleted() {
       onFinish();
    }

    @Override
    public void onStart() {
        super.onStart();
        onNetStart();
    }

    @Override
    public void onError(Throwable e) {
        onFail(e.toString());
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    protected abstract void onNetStart();

    protected abstract void onSuccess(T t);

    protected abstract void onFail(String msg);

    protected abstract void onFinish();

}
