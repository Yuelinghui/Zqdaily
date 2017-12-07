package com.zdaily.ui.core

import android.app.Application

/**
 * Created by yuelinghui on 17/12/6.
 */
class CustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initApp(this)
    }
}