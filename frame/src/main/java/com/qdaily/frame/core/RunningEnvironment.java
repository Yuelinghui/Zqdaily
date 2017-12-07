package com.qdaily.frame.core;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yuelinghui on 17/8/8.
 */

public class RunningEnvironment {
    /**
     * 全局静态context
     */
    public static Context sAppContext = null;

    /**
     * 网络连接管理
     */
    public static ConnectivityManager sConnectManager = null;

    /**
     * 初始化方法
     *
     * @param app
     */
    public static void init(Application app) {
        if (sAppContext == null) {
            sAppContext = app.getApplicationContext();
        }
        if (sConnectManager == null) {
            sConnectManager = (ConnectivityManager) sAppContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

    /**
     * 检查网络情况
     *
     * @return true:畅通，false:不畅通
     */
    public static boolean checkNetwork(Context context) {
        if (context == null) {
            return false;
        }

        if (!isNetworkAvailable(context)) {
            return false;
        }

        return true;
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    return true;
                }
            }
        } catch (Exception e) {
        }

        return false;
    }
}
