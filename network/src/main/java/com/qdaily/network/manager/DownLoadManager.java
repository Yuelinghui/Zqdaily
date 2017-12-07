package com.qdaily.network.manager;

import android.os.Environment;

import com.qdaily.frame.managercenter.MManagerCenter;
import com.qdaily.frame.util.FileCache;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yuelinghui on 17/8/1.
 */

public class DownLoadManager {

    private static DownLoadManager mDownLoadManager = null;
    private static OkHttpClient mOkHttpClient = null;

    private DownLoadManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public synchronized static DownLoadManager getInstance() {
        if (mDownLoadManager == null) {
            mDownLoadManager = new DownLoadManager();
        }
        return mDownLoadManager;
    }

    public void downLoad(final String url, Subscriber<File> subscriber) {
        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(final Subscriber<? super File> subscriber) {
                Request request = new Request.Builder().url(url).build();
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        MManagerCenter.getManager(FileCache.class).setFile(url,response.body().byteStream());
                        File file = MManagerCenter.getManager(FileCache.class).getFile(url);
                        if (file == null) {
                            subscriber.onError(new Throwable("文件不存在"));
                        }
                        subscriber.onNext(file);
                        subscriber.onCompleted();
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private String isExitDir(String saveDir) {
        File downloadFile = new File(Environment.getExternalStorageDirectory(),saveDir);
        if (!downloadFile.mkdirs()) {
            try {
                downloadFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                downloadFile = null;
            }
        }
        if (downloadFile == null) {
            return null;
        }
        return downloadFile.getAbsolutePath();

    }
}
