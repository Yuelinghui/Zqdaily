package com.qdaily.frame.util;

import java.util.HashSet;

/**
 * Created by yuelinghui on 17/8/2.
 */

public class CookiesUtil {

    private static HashSet<String> COOKIES = null;

    public static void saveCookies(HashSet cookies) {
        COOKIES = cookies;
    }

    public static HashSet<String> getCookies() {
        return COOKIES;
    }
}
