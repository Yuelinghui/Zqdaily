package com.qdaily.frame.log;

import android.text.TextUtils;
import android.util.Log;

import com.qdaily.frame.util.QLibConfig;

/**
 * Created by yuelinghui on 17/4/20.
 */

public class QLog {

    public static QLogCallBack callBack;

    public static void time(String tag) {
        i(tag, "current second time:" + System.currentTimeMillis());
    }

    public static void i(String tag, String msg) {
        if (QLibConfig.sDebug) Log.i(tag, msg);
        if (callBack != null) {
            callBack.info(tag, msg);
        }
    }

    public static void i(String tag, String msg, Object... args) {
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        i(tag, msg);
    }


    public static void d(String message) {
        if (QLibConfig.sDebug)
            Logger.d(message);
    }

    public static void d(String tag, String message) {
        if (QLibConfig.sDebug)
            Logger.d(tag, message);
    }

    public static void d(String message, int methodCount) {
        if (QLibConfig.sDebug)
            Logger.d(message, methodCount);
    }

    public static void d(String tag, String message, int methodCount) {
        if (QLibConfig.sDebug)
            Logger.d(tag, message, methodCount);
    }

    public static void json(String message) {
        if (QLibConfig.sDebug)
            Logger.json(message);
    }

    public static void json(String tag, String message) {
        if (QLibConfig.sDebug)
            Logger.json(tag, message);
    }

    public static void w(String tag, String msg) {
        if (QLibConfig.sDebug)
            Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Object... args) {
        if (QLibConfig.sDebug) {
            if (args.length > 0) {
                msg = String.format(msg, args);
            }
            w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable throwable) {
        if (QLibConfig.sDebug)
            Log.w(tag, msg, throwable);
    }


    public static void e(String tag, String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        Log.e(tag, msg);
        if (callBack != null) {
            callBack.error(tag, msg);
        }
    }

    public static void e(String tag, String msg, Object... args) {
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        Log.e(tag, msg, throwable);
        if (callBack != null) {
            callBack.error(tag, msg, throwable);
        }
    }

    //info和error会进行回调
    public interface QLogCallBack {
        void info(String tag, String msg);

        void error(String tag, String msg);

        void error(String tag, String msg, Throwable throwable);
    }
}
