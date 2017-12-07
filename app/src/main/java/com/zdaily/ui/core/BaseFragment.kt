package com.zdaily.ui.core

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.qdaily.frame.core.UIData

/**
 * Created by yuelinghui on 17/12/6.
 */
open class BaseFragment : Fragment() {

    protected var mUIData:UIData? = null

    protected var mActivity:BaseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mActivity = activity as? BaseActivity
        this.mUIData = mActivity?.mUIData
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mActivity = context as? BaseActivity
        this.mUIData = mActivity?.mUIData
    }



    fun onBackPressed():Boolean = false
}