package com.zdaily.ui.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.qdaily.frame.core.NetListener
import com.qdaily.frame.managercenter.MManagerCenter
import com.zdaily.ui.R
import com.zdaily.ui.core.BaseFragment
import com.zdaily.ui.core.ConfigManager
import com.zdaily.ui.core.isNightMode
import com.zdaily.ui.core.setNightMode
import com.zdaily.ui.home.entity.ArticleInfo
import com.zdaily.ui.home.model.MainModel
import com.zdaily.ui.home.ui.detail.ARTICLE_ID
import com.zdaily.ui.home.ui.detail.DetailActivity
import com.zdaily.ui.widget.RefreshRecyclerView

/**
 * Created by yuelinghui on 17/12/6.
 */
class MainFragment : BaseFragment() {

    var mRefreshView:RefreshRecyclerView? = null


    var mAdapter:MainAdapter? = null

    val mMainModel = MainModel(mActivity)

    var mArticleInfo:ArticleInfo? = null

    var mCurrentDate: String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.main_fragment_layout,container,false)
        mRefreshView = view?.findViewById(R.id.refresh_view)

        mAdapter = MainAdapter(mActivity!!, mutableListOf())

        mRefreshView?.setAdapter(mAdapter)

        mRefreshView?.setOnLoadListener { loadBefore(mCurrentDate) }

        mRefreshView?.setOnRefreshListener { loadLatest() }

        mAdapter?.setOnItemClickListener { startDetail(it) }

        var rightTxt = getString(R.string.main_menu_night_mode)
        if (MManagerCenter.getManager(ConfigManager::class.java).isNightMode()) {
            rightTxt = getString(R.string.main_menu_day_mode)
            mActivity?.setTheme(R.style.NightTheme)
        }

        mActivity?.getTitleBar()?.setTitle(getString(R.string.main_title))
        mActivity?.getTitleBar()?.setRightTxt(rightTxt)
        mActivity?.getTitleBar()?.setOnRightClickListener {
            val isNight = !MManagerCenter.getManager(ConfigManager::class.java).isNightMode()
            MManagerCenter.getManager(ConfigManager::class.java).setNightMode(isNight)
            if (isNight) {
                mActivity?.getTitleBar()?.setRightTxt(getString(R.string.main_menu_night_mode))
                mActivity?.setTheme(R.style.NightTheme)
                reStartSelf()
            } else{
                mActivity?.getTitleBar()?.setRightTxt(getString(R.string.main_menu_day_mode))
                mActivity?.setTheme(R.style.DayTheme)
                reStartSelf()
            }

        }

        loadLatest()
        return view
    }

    private fun reStartSelf() {
        val intent = Intent()
        intent.setClass(mActivity,MainActivity::class.java)
        mActivity?.startActivity(intent)
        mActivity?.finish()
    }

    private fun startDetail(id:Int) {
        val intent = Intent()
        intent.setClass(mActivity, DetailActivity::class.java)
        intent.putExtra(ARTICLE_ID,id)
        mActivity?.startActivity(intent)
    }

    private fun loadBefore(date:String?) {
        if (date == null) {
            return
        }
        mMainModel.queryBefore(date,object :NetListener<ArticleInfo>(){
            override fun onNetStart() {
            }

            override fun onSuccess(t: ArticleInfo?) {
                if (t == null) {
                    onFail("数据错误")
                    return
                }
                mArticleInfo = t
                mCurrentDate = t.date
                mAdapter?.addData(t.stories)
            }

            override fun onFail(msg: String?) {
                Toast.makeText(mActivity,msg,Toast.LENGTH_SHORT).show()
            }

            override fun onFinish() {
                mRefreshView?.compelete()
            }

        })
    }

    private fun loadLatest() {
        mMainModel.queryLatest(object :NetListener<ArticleInfo>(){
            override fun onSuccess(t: ArticleInfo?) {
                if (t == null) {
                    onFail("数据错误")
                    return
                }
                mArticleInfo = t
                mCurrentDate = t.date
                mAdapter?.setData(t.stories)
            }

            override fun onFail(msg: String?) {
                Toast.makeText(mActivity,msg,Toast.LENGTH_SHORT).show()
            }

            override fun onFinish() {
                mRefreshView?.compelete()
            }

            override fun onNetStart() {

            }

        })
    }
}