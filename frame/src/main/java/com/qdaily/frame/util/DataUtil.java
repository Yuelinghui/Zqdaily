package com.qdaily.frame.util;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;

import com.qdaily.frame.log.QLog;

import java.text.SimpleDateFormat;

/**
 * Created by yuelinghui on 17/8/18.
 */

public class DataUtil {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatTime(long time) {
        String result = formatter.format(time);
        return result;
    }

    public static String formatTime(String time) {
        if (TextUtils.isEmpty(time) || time.length() < 14) {
            return "";
        }
        String year = time.substring(0,4);
        String month = time.substring(4,6);
        String day = time.substring(6,8);
        String hour = time.substring(8,10);
        String min = time.substring(10,12);
        String sec = time.substring(12,14);

        return year+"-"+month+"-"+day+" "+hour+":"+min+":"+sec;
    }

    public static boolean withOver5Days(long createAt) {

        if (createAt == 0) return false;

        createAt = System.currentTimeMillis() - createAt;

        int day = (int) (createAt / (24 * 60 * 60 * 1000));

        return day >= 5;
    }

    public static boolean within24Hours(long createAt) {
        long curMillis = System.currentTimeMillis();
        createAt = convertMillis(createAt);
        long hour = (curMillis - createAt) / (1000 * 60 * 60);
        return hour < 24;
    }

    /**
     * Description： 处理微博过去时间显示效果
     *
     * @param createAt Data 类型
     * @return { String }
     * @throws
     */
    public static String getSocialDateDisplay(long createAt) {
        long curMillis = System.currentTimeMillis();
        createAt = convertMillis(createAt);
        long diffMinute = (curMillis - createAt) / (1000 * 60);

        if (diffMinute < 30) {
            return "刚刚";
        }

        Time time = new Time();
        time.set(createAt);

        Time curTime = new Time();
        curTime.setToNow();
        if (isToday(createAt)) {
            if (diffMinute < 60)
                return diffMinute + "分钟前";

            long hour = diffMinute / 60;
            return hour + "小时前";
        }

        if (curTime.year - time.year == 1) {
            return "去年";
        }

        if (curTime.year - time.year > 1) {
            String result = time.year + "年" + (time.month + 1) + "月" + time.monthDay + "日";
            QLog.w("QDUtil", createAt + "2年前的时间:" + result);
            return result;
        }

        if (curTime.yearDay - time.yearDay == 1) {
            return "昨天";
        }

        return (time.month + 1) + "月" + time.monthDay + "日";
    }

    public static boolean isToday(long milliseconds) {
        return DateUtils.isToday(convertMillis(milliseconds));
    }

    public static long convertMillis(long second) {
        String str = String.valueOf(second);
        if (str.length() == 10) {
            return second * 1000;
        }
        return second;
    }
}
