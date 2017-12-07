package com.zdaily.ui.core

import android.app.Application
import com.qdaily.frame.core.RunningEnvironment
import com.qdaily.frame.managercenter.MManagerCenter

/**
 * Created by yuelinghui on 17/12/6.
 */
private val sNightModeLock = byteArrayOf()
private var sIsNightMode = false
fun initApp(app: Application) {
    RunningEnvironment.init(app)
    MManagerCenter.init(app)
    setNightMode(MManagerCenter.getManager(ConfigManager::class.java).isNightMode())
}

fun setNightMode(isNightMode: Boolean?) {
    synchronized(sNightModeLock) {
        if (sIsNightMode == isNightMode) {
            return
        }
        sIsNightMode = isNightMode ?: false
    }
    MManagerCenter.getManager(ConfigManager::class.java).setNightMode(sIsNightMode)
}

fun isNightMode(): Boolean {
    synchronized(sNightModeLock) {
        return sIsNightMode
    }
}
