@file:JvmName("SharedPreferenceUtil")

package com.zdaily.ui.util

import android.content.Context
import com.qdaily.frame.core.RunningEnvironment
import com.qdaily.frame.util.JsonUtil
import java.lang.Exception

/**
 * Created by yuelinghui on 17/12/6.
 */
private const val OBJECT_INFO = "OBJECT_INFO"
private val mSharePreference = RunningEnvironment.sAppContext.getSharedPreferences(OBJECT_INFO, 0)


fun <T> Context.getShared(clazz: Class<T>, key: String): T? {
    try {
        val value = mSharePreference.getString(key, null)
        return JsonUtil.Json2Object(value, clazz)
    } catch (e: Exception) {

    }
    return null
}

fun <T> Context.setShared(obj: T,key: String) {
    val value = JsonUtil.toJSONString(obj);
    mSharePreference.edit().putString(key,value).commit()
}

