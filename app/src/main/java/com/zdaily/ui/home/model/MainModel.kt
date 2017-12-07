package com.zdaily.ui.home.model

import android.content.Context
import com.qdaily.frame.core.NetListener
import com.qdaily.network.NetModel
import com.qdaily.network.manager.NetClientManager
import com.qdaily.network.manager.NetServiceManager
import com.zdaily.ui.home.entity.ArticleInfo
import com.zdaily.ui.home.entity.HomeDetailInfo
import com.zdaily.ui.home.protocol.BeforeService
import com.zdaily.ui.home.protocol.DetailService
import com.zdaily.ui.home.protocol.LatestService
import com.zdaily.ui.home.protocol.WebTextService

/**
 * Created by yuelinghui on 17/12/6.
 */
class MainModel(context: Context?) : NetModel(context) {

    fun queryLatest(listener: NetListener<ArticleInfo>) {
        val latestService = NetServiceManager.getInstance().create(LatestService::class.java)
        NetClientManager.getInstance().doRequest(latestService.getLatest(), listener)
    }

    fun queryDetail(id: Int, listener: NetListener<HomeDetailInfo>) {
        val detailService = NetServiceManager.getInstance().create(DetailService::class.java)
        NetClientManager.getInstance().doRequest(detailService.getDetail(id), listener)
    }

    fun queryBefore(beforeDate: String, listener: NetListener<ArticleInfo>) {
        val beforeService = NetServiceManager.getInstance().create(BeforeService::class.java)
        NetClientManager.getInstance().doRequest(beforeService.getBefore(beforeDate), listener)
    }

    fun queryCss(url:String,listener: NetListener<String>) {
        val webtextService = NetServiceManager.getInstance().create(WebTextService::class.java)
        NetClientManager.getInstance().doRequest(webtextService.getWebCss(url),listener)
    }

}