package com.qdaily.frame.util;

import android.content.Context;

/**
 * Created by yuelinghui on 17/4/20.
 */

public class QLibConfig {
    public static boolean sDebug = false;
    public static Context sAppContext;
    public static void init(boolean debug, Context applicationContext) {
        sDebug = debug;
        sAppContext = applicationContext;
    }
}
