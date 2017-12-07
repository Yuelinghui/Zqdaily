package com.zdaily.ui.home.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qdaily.frame.managercenter.MManagerCenter
import com.zdaily.ui.R
import com.zdaily.ui.core.ImageManager
import com.zdaily.ui.home.entity.StoriesInfo

/**
 * Created by yuelinghui on 17/12/6.
 */
class MainAdapter(val context: Context,var list:MutableList<StoriesInfo> ) : RecyclerView.Adapter<ItemViewHolder>() {

    private var mItemClickListener:((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val viewHolder = ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        val storiesInfo = list?.get(position)
        holder?.titleTxt?.text = storiesInfo.title
        MManagerCenter.getManager(ImageManager::class.java).displayImage(context,storiesInfo.images[0],holder?.logoImg)
        if (storiesInfo.multipic) {
            holder?.multiPicTxt?.visibility = View.VISIBLE
        } else{
            holder?.multiPicTxt?.visibility = View.GONE
        }
        holder?.itemLayout?.setOnClickListener { mItemClickListener?.invoke(storiesInfo.id) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnItemClickListener(listener:(Int) -> Unit) {
        mItemClickListener = listener
    }

    fun addData(list: List<StoriesInfo>) {
        val curCount = itemCount
        this.list.addAll(list)
        notifyItemChanged(curCount,list.size)
    }

    fun setData(list: List<StoriesInfo>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}

class ItemViewHolder(val view: View):RecyclerView.ViewHolder(view) {

    var itemLayout:ViewGroup? = null
    var titleTxt:TextView? = null
    var multiPicTxt:TextView? = null
    var logoImg:ImageView? = null
    init {
        itemLayout = view.findViewById(R.id.layout_item)
        titleTxt = view.findViewById(R.id.txt_title)
        multiPicTxt = view.findViewById(R.id.txt_multipic)
        logoImg = view.findViewById(R.id.img_logo)
    }
}
