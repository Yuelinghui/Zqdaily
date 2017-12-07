package com.qdaily.frame.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.qdaily.frame.log.QLog;

/**
 * Created by yuelinghui on 17/8/8.
 */

public class PackageUtil {

    private static PackageUtil instance;

    private String appChannel;
    private String qVersionName;
    private String versionName;
    private int versionCode;

    private PackageUtil(){}

    public static PackageUtil getInstance(Context context){
        if (instance == null) {
            instance = new PackageUtil();

            ApplicationInfo appInfo = null;
            try {

                appInfo = context.getApplicationContext().getPackageManager().getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);
                String qVersionName = appInfo.metaData.getString("TEST_VERSION_NAME");
                String qChannel = appInfo.metaData.getString("TEST_CHANNEL");

                instance.appChannel = TextUtils.isEmpty(qChannel)? "developer": qChannel;

                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);

                instance.versionName = pi.versionName;
                instance.versionCode = pi.versionCode;

                instance.qVersionName = TextUtils.isEmpty(qVersionName)? pi.versionName: qVersionName;

                QLog.w("PackageUtil", "QDaily app info:" + instance.toString());
            } catch (PackageManager.NameNotFoundException e) {
                QLog.e("PackageUtil", "getInstance", e);
            }
        }
        return instance;
    }

    public String getAppChannel() {
        return appChannel;
    }

    public String getqVersionName() {
        return qVersionName;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    @Override
    public String toString() {
        return "PackageUtil{" +
                "appChannel='" + appChannel + '\'' +
                ", qVersionName='" + qVersionName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                '}';
    }
}
