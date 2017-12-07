package com.zdaily.ui.core

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.qdaily.frame.managercenter.MManagerCenter
import com.zdaily.ui.R

/**
 * Created by yuelinghui on 17/12/6.
 */
open class TransitionAnimationActivity : FragmentActivity() {
    private val SAVE_THEME = "SAVE_THEME"
    private var mTheme = 0

    override fun setTheme(resid: Int) {
        val themeId = getThemeStyle()
        super.setTheme(themeId)
        mTheme = themeId
    }

    private fun getThemeStyle(): Int {
        return if (MManagerCenter.getManager(ConfigManager::class.java).isNightMode()) R.style.NightTheme else R.style.DayTheme
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(SAVE_THEME,mTheme)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
       mTheme = savedInstanceState?.getInt(SAVE_THEME) ?: 0
    }

//    override fun finish() {
//        super.finish()
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//    }
}