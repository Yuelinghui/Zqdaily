package com.zdaily.ui.home.ui.detail

import com.qdaily.frame.core.UIData
import com.zdaily.ui.home.entity.HomeDetailInfo

/**
 * Created by yuelinghui on 17/12/7.
 */
data class DetailData(
        var detailInfo: HomeDetailInfo?,
        var webCss:String?
) : UIData()