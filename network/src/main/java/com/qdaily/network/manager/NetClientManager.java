package com.qdaily.network.manager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yuelinghui on 17/8/1.
 */

public class NetClientManager {

    private static NetClientManager mRxManager = null;

    private NetClientManager() {

    }

    public synchronized static NetClientManager getInstance() {
        if (mRxManager == null) {
            mRxManager = new NetClientManager();
        }
        return mRxManager;
    }

    /**
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */

    public <T> void doRequest(final Observable<T> observable, final Subscriber<T> subscriber) {
        Action1<T> onSuccessAction = new Action1<T>() {
            @Override
            public void call(T result) {
                subscriber.onNext(result);

                subscriber.onCompleted();

            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                subscriber.onError(throwable);
            }
        };

        subscriber.onStart();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccessAction, onErrorAction);
    }

}
