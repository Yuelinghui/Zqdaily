package com.zdaily.ui.home.ui.detail

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qdaily.frame.managercenter.MManagerCenter
import com.zdaily.ui.R
import com.zdaily.ui.core.BaseFragment
import com.zdaily.ui.core.ConfigManager
import com.zdaily.ui.core.ImageManager
import com.zdaily.ui.core.isNightMode
import com.zdaily.ui.widget.ScrollWebView

/**
 * Created by yuelinghui on 17/12/7.
 */
class DetailFragment : BaseFragment() {

    private var mLogoImg: ImageView? = null
    private var mTitleTxt: TextView? = null
    private var mSourceTxt: TextView? = null
    private var mWebView: ScrollWebView? = null

    private var mDetailData: DetailData? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.detail_fragment_layout, container, false)

        mLogoImg = view?.findViewById(R.id.img_logo)
        mTitleTxt = view?.findViewById(R.id.txt_title)
        mSourceTxt = view?.findViewById(R.id.txt_source)
        mWebView = view?.findViewById(R.id.webview_detail)

        mDetailData = mUIData as DetailData?

        MManagerCenter.getManager(ImageManager::class.java).displayImage(mActivity, mDetailData?.detailInfo?.image ?: "", mLogoImg)
        mTitleTxt?.text = mDetailData?.detailInfo?.title
        mSourceTxt?.text = mDetailData?.detailInfo?.image_source

        var html = mDetailData?.detailInfo?.body
        if (!TextUtils.isEmpty(mDetailData?.webCss)) {
            val header = """<style type="text/css">""" + mDetailData?.webCss + """</style>"""
            html = """<html><header>""" + header + """</header>""" + mDetailData?.detailInfo?.body + """</body></html>"""
        }
        if (MManagerCenter.getManager(ConfigManager::class.java).isNightMode()) {
            html = html?.replace("<html","""<html class="night"""")
        }
        mWebView?.loadDataWithBaseURL(null,html,"text/html","utf-8",null)
        return view
    }
}