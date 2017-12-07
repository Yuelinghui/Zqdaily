package com.zdaily.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.zdaily.ui.R

/**
 * Created by yuelinghui on 17/12/6.
 */
class TitleBar : FrameLayout {

    var mLeftLayout: FrameLayout? = null
    var mLeftImg: ImageView? = null
    var mLeftTxt: TextView? = null
    var mTitleTxt: TextView? = null
    var mRightLayout: FrameLayout? = null
    var mRightTxt: TextView? = null

    var mLeftClickListener:((View) -> Unit)? = null

    var mRightClickListener: ((View) -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributes: AttributeSet?) : this(context, attributes, 0)
    constructor(context: Context, attributes: AttributeSet?, defStyleAttr: Int) : super(context, attributes, defStyleAttr) {
        val view = LayoutInflater.from(context).inflate(R.layout.title_bar_layout,this,true)
        mLeftLayout = view.findViewById(R.id.nav_left)
        mLeftImg = view.findViewById(R.id.nav_left_img)
        mLeftTxt = view.findViewById(R.id.nav_left_text)
        mTitleTxt = view.findViewById(R.id.nav_title)
        mRightLayout = view.findViewById(R.id.nav_right)
        mRightTxt = view.findViewById(R.id.nav_right_text)
        mLeftLayout?.setOnClickListener { mLeftClickListener?.invoke(this) }
        mRightLayout?.setOnClickListener { mRightClickListener?.invoke(this) }
    }

    fun setLieftHide(hide:Boolean) {
        mLeftLayout?.visibility = if (hide) View.GONE else View.VISIBLE
    }

    fun setRightHide(hide: Boolean) {
        mRightLayout?.visibility = if (hide) View.GONE else View.VISIBLE
    }

    fun setLeftTxt(leftTxt: String) {
        mLeftLayout?.visibility = View.VISIBLE
        mLeftTxt?.visibility = View.VISIBLE
        mLeftImg?.visibility = View.GONE
        mLeftTxt?.text = leftTxt
    }

    fun setLeftTxt(leftTxtResId: Int) {
        setLeftTxt(context?.resources?.getString(leftTxtResId)?:"")
    }

    fun setRightTxt(rightTxt: String) {
        mRightLayout?.visibility = View.VISIBLE
        mRightTxt?.visibility = View.VISIBLE
        mRightTxt?.text = rightTxt
    }

    fun setRightTxt(rightTxtResId: Int) {
        setRightTxt(context?.resources?.getString(rightTxtResId)?:"")
    }

    fun setLeftImg(leftImgResId: Int) {
        mLeftLayout?.visibility = View.VISIBLE
        mLeftTxt?.visibility = View.GONE
        mLeftImg?.visibility = View.VISIBLE
        mLeftImg?.setImageResource(leftImgResId)
    }

    fun setTitle(titleTxt: String) {
        mTitleTxt?.text = titleTxt
    }

    fun setTitle(titleTxtResId: Int) {
        setTitle(context?.resources?.getString(titleTxtResId)?:"")
    }

    fun setOnLeftClickListener(listener: (View) -> Unit) {
        mLeftClickListener = listener
    }

    fun setOnRightClickListener(listener: (View) -> Unit) {
        mRightClickListener = listener
    }

}