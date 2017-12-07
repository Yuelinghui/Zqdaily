package com.zdaily.ui.home.ui.detail

import android.widget.Toast
import com.qdaily.frame.core.NetListener
import com.qdaily.frame.core.UIData
import com.zdaily.ui.core.BaseActivity
import com.zdaily.ui.home.entity.HomeDetailInfo
import com.zdaily.ui.home.model.MainModel

/**
 * Created by yuelinghui on 17/12/7.
 */
const val ARTICLE_ID = "ARTICLE_ID"
class DetailActivity : BaseActivity() {
    override fun initUIData(): UIData? {
        return DetailData(null,null)
    }

    private var mId: Int = -1
    private var mMainModel:MainModel = MainModel(this)

    private var mDetailData:DetailData? = null

    override fun initData():Boolean {
        mDetailData = mUIData as DetailData?
        mId = intent.getIntExtra(ARTICLE_ID,-1)
        if (mId == -1) {
            Toast.makeText(this,"数据错误",Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        return true
    }

    override fun initView() {
        mMainModel.queryDetail(mId,object :NetListener<HomeDetailInfo>(){
            override fun onNetStart() {
            }

            override fun onSuccess(t: HomeDetailInfo?) {
                if (t == null) {
                    onFail("数据错误")
                    return
                }
                mDetailData?.detailInfo = t
                if (t.css?.size != 0) {
                    loadCss()
                } else{
                    startFristFragment(DetailFragment())
                }
            }

            override fun onFail(msg: String?) {
                Toast.makeText(this@DetailActivity,"数据错误",Toast.LENGTH_SHORT).show()
            }

            override fun onFinish() {
            }

        })

    }

    private fun loadCss() {
        mMainModel.queryCss(mDetailData?.detailInfo?.css?.get(0)?:"",object :NetListener<String>(){
            override fun onNetStart() {
            }

            override fun onSuccess(t: String?) {
                mDetailData?.webCss = t
            }

            override fun onFail(msg: String?) {
            }

            override fun onFinish() {
                startFristFragment(DetailFragment())
            }

        })
    }

}