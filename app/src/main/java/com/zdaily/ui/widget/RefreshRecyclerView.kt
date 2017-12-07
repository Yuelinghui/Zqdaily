package com.zdaily.ui.widget

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.zdaily.ui.R

/**
 * Created by yuelinghui on 17/12/6.
 */
class RefreshRecyclerView : FrameLayout {

    private var mSwipLayout: SwipeRefreshLayout? = null
    private var mRecyclerView: RecyclerView? = null

    var mRefreshListener: ((View) -> Unit)? = null

    var mLoadListener: ((View) -> Unit)? = null

    var mLayoutManager: LinearLayoutManager? = null

    var mAdapter: RecyclerView.Adapter<*>? = null

    var mIsLoading: Boolean = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context,attributeSet: AttributeSet?,defStyleAttr: Int):super(context,attributeSet,defStyleAttr) {
        val view = LayoutInflater.from(context).inflate(R.layout.refresh_recyclerview_layout, this, true)
        mSwipLayout = view.findViewById(R.id.swiplayout)
        mRecyclerView = view.findViewById(R.id.recyclerview)
        mSwipLayout?.setOnRefreshListener { mRefreshListener?.invoke(this) }
        mLayoutManager = LinearLayoutManager(context)
        mLayoutManager?.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView?.layoutManager = mLayoutManager
        mRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = mLayoutManager?.findLastVisibleItemPosition()
                if (lastVisibleItemPosition?.plus(1) == mAdapter?.itemCount) {
                    val isRefreshing = mSwipLayout?.isRefreshing ?: false
                    if (isRefreshing) {
                        return
                    }
                    if (!mIsLoading) {
                        mIsLoading = true
                        mLoadListener?.invoke(this@RefreshRecyclerView)
                    }
                }
            }
        })
    }
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet,0)

    fun compelete() {
        mSwipLayout?.isRefreshing = false
        mIsLoading = false
    }

    fun setOnRefreshListener(listener: (View) -> Unit) {
        mRefreshListener = listener
    }

    fun setOnLoadListener(listener: (View) -> Unit) {
        mLoadListener = listener
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        mAdapter = adapter
        mRecyclerView?.adapter = mAdapter
    }
}