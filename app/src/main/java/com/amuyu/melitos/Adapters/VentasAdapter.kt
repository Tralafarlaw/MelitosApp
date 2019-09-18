package com.amuyu.melitos.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.amuyu.melitos.R
import com.amuyu.melitos.ui.vender.VentasItem
import com.bumptech.glide.Glide

import java.util.ArrayList

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amuyu.melitos.C

import com.facebook.FacebookSdk.getApplicationContext

class VentasAdapter(private val mLayoutManager: GridLayoutManager) :
    RecyclerView.Adapter<VentasAdapter.ItemViewHolder>() {

    private var mItems: ArrayList<Map<String, Any>>? = null
    fun setmItems(data:ArrayList<Map<String, Any>>) {
        mItems = data
    }

    override fun getItemViewType(position: Int): Int {
        val spanCount = mLayoutManager.spanCount
        return if (spanCount == SPAN_COUNT_ONE) {
            VIEW_TYPE_BIG
        } else {
            VIEW_TYPE_SMALL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View
        if (viewType == VIEW_TYPE_BIG) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.vender_item, parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.vender_item2, parent, false)
        }
        return ItemViewHolder(view, viewType)
    }

    override fun onBindViewHolder(v: ItemViewHolder, position: Int) {
        v.title.text = mItems!![position].get(C.cPName).toString()
        v.info.text = mItems!![position].get(C.cPPrice).toString()
    }

    override fun getItemCount(): Int {
        if(mItems == null){return 0}
        return mItems!!.size
    }

    inner class ItemViewHolder
    //CheckBox checkBox;

        (itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var iv: ImageView
        var title: TextView
        var info: TextView

        init {
            if (viewType == VIEW_TYPE_BIG) {
                iv = itemView.findViewById<View>(R.id.rowImageView) as ImageView
                title = itemView.findViewById<View>(R.id.rowTextView) as TextView
                info = itemView.findViewById<View>(R.id.rowTextView2) as TextView
                //checkBox = (CheckBox) itemView.findViewById(R.id.rowCheckBox);
            } else {
                iv = itemView.findViewById<View>(R.id.SrowImageView) as ImageView
                title = itemView.findViewById<View>(R.id.SrowTextView) as TextView
                info = itemView.findViewById<View>(R.id.SrowTextView2) as TextView
            }
        }
    }

    companion object {
        val SPAN_COUNT_ONE = 1
        val SPAN_COUNT_THREE = 3

        private val VIEW_TYPE_SMALL = 1
        private val VIEW_TYPE_BIG = 2
    }


}
