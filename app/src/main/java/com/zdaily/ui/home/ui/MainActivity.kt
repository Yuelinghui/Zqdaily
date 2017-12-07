package com.zdaily.ui.home.ui

import com.qdaily.frame.core.UIData
import com.zdaily.ui.core.BaseActivity

/**
 * Created by yuelinghui on 17/12/6.
 */
class MainActivity : BaseActivity() {
    override fun initUIData(): UIData? {
        return null
    }

    override fun initData():Boolean {
        return true
    }

    override fun initView() {
        startFristFragment(MainFragment())
    }

}