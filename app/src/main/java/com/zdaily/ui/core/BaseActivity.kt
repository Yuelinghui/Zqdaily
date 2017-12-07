package com.zdaily.ui.core

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.qdaily.frame.core.UIData
import com.qdaily.frame.managercenter.MManagerCenter
import com.zdaily.ui.R
import com.zdaily.ui.widget.TitleBar

/**
 * Created by yuelinghui on 17/12/6.
 */
abstract class BaseActivity : TransitionAnimationActivity() {

    var mUIData:UIData? = null
    var mTitleLayout: TitleBar? = null
    val FRAME_LAYOUT = R.layout.base_activity_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(FRAME_LAYOUT)
        mUIData = initUIData()
        mTitleLayout = findViewById(R.id.title_bar)
        mTitleLayout?.setOnLeftClickListener { this.finish() }
        if (initData()) {
            initView()
        }
    }

    override fun startActivity(intent: Intent?) {
        start(intent, -1)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        start(intent, requestCode)
    }

    private fun start(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }

    fun getTitleBar(): TitleBar? {
        return mTitleLayout
    }

    fun startFristFragment(fragment: Fragment) {
        if (isFinishing) {
            return
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.replace(R.id.frame_container, fragment)
        ft.commitAllowingStateLoss()
    }

    fun startFragment(fragment: Fragment) {
        if (isFinishing) {
            return
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out)
        ft.replace(R.id.frame_container, fragment, fragment.javaClass.name)
        ft.addToBackStack(fragment.javaClass.name)
        ft.commitAllowingStateLoss()
    }

    fun getConfigManager(): ConfigManager {
        return MManagerCenter.getManager(ConfigManager::class.java)
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        val list = supportFragmentManager.fragments
        var fragment: BaseFragment? = null
        if (list != null && list.size > 0) {
            if (list.size > count) {
                // 处理startFragment和startFirstFragment混合模式
                for (i in list.indices.reversed()) {
                    if (list[i] != null) {
                        fragment = list[i] as? BaseFragment

                        if (fragment == null || !fragment.isAdded || !fragment.isVisible) {
                            continue
                        }
                        break
                    }
                }
            } else {
                // 处理startFragment模式
                if (count > 0 && list[count - 1] != null
                        && list[count - 1] is BaseFragment) {
                    fragment = list[count - 1] as BaseFragment
                }
            }
        }
        if (fragment == null || !fragment!!.isAdded() || !fragment!!.isVisible()) {
            super.onBackPressed()
            return
        }
        if (!fragment!!.onBackPressed()) {
            if (count <= 0) {
                super.onBackPressed()
                return
            }
            supportFragmentManager.popBackStack()
            return
        }
    }

    abstract fun initData():Boolean

    abstract fun initView()

    abstract fun initUIData():UIData?

}