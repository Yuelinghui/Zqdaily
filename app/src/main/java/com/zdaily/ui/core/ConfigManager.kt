package com.zdaily.ui.core

import android.content.Context
import com.qdaily.frame.managercenter.MManager
import com.zdaily.ui.util.getShared
import com.zdaily.ui.util.setShared

/**
 * Created by yuelinghui on 17/12/6.
 */
private const val NIGHT_MODE ="NIGHT_MODE"
fun ConfigManager.setNightMode(isNight: Boolean) {
    this.context?.setShared(isNight, NIGHT_MODE)
}
fun ConfigManager.isNightMode():Boolean
 =  this.context?.getShared(Boolean::class.java, NIGHT_MODE)?:false

class ConfigManager:MManager() {
    var context: Context?  = null

    override fun onManagerInit(context: Context) {
        this.context = context
    }
}