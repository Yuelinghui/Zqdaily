package com.zdaily.ui.widget

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView

/**
 * Created by yuelinghui on 17/12/7.
 */
class ScrollWebView : WebView {

    private var mIsScrolling = true

    private val mCurPoint: PointF = PointF()

    constructor(context:Context): this(context,null)
    constructor(context: Context,attributeSet: AttributeSet?): this(context,attributeSet,0)
    constructor(context: Context,attributeSet: AttributeSet?,defStyle:Int):super(context,attributeSet,defStyle)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mIsScrolling
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(event)
        }
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                mIsScrolling = true
                mCurPoint.x = event.x
                mCurPoint.y = event.y
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val lastY = event.getY(event.pointerCount - 1)
                if (isBottom() && isTop()) {
                    mIsScrolling = false
                }else if (isBottom()) {
                    mIsScrolling = false
                    if (mCurPoint.y - lastY < 0) {
                        mIsScrolling = true
                    }
                } else if (isTop()) {
                    mIsScrolling = false
                    if (mCurPoint.y - lastY > 0) {
                        mIsScrolling = true
                    }
                }
                parent.requestDisallowInterceptTouchEvent(mIsScrolling)
            }
            MotionEvent.ACTION_UP -> mIsScrolling = false
        }
        return super.onTouchEvent(event)
    }

    private fun isBottom():Boolean {
        val contentHeight = contentHeight * scale
        val currentHeight = height + scrollY
        return contentHeight - currentHeight < 1
    }

    private fun isTop():Boolean {
        return scrollY == 0
    }
}