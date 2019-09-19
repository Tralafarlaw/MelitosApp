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
import kotlinx.android.synthetic.main.vender_item.view.*
import kotlinx.android.synthetic.main.vender_item2.view.*

class VentasAdapter(private val mLayoutManager: GridLayoutManager) :
    RecyclerView.Adapter<VentasAdapter.ItemViewHolder>() {
    var sw = false
    var items = arrayListOf<ItemViewHolder>()
    private var mItems: ArrayList<Map<String, Any>>? = null
    fun setmItems(data:ArrayList<Map<String, Any>>) {
        mItems = data
    }
    fun at(i: Int){

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
        v.info.text = mItems!![position].get(C.cPStock).toString()
        if(v.price != null){
            v.price.text = mItems!![position].get(C.cPPrice).toString()
        }
        if(sw){
            v.cv.visibility = View.VISIBLE
        }else{
            v.cv.visibility = View.GONE
        }
        items.add(position, v)
    }

    override fun getItemCount(): Int {
        if(mItems == null){return 0}
        return mItems!!.size
    }

    inner class ItemViewHolder


        (itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var iv: ImageView
        var title: TextView
        var info: TextView
        lateinit var price: TextView
        var cv: CheckBox
        init {
            if (viewType == VIEW_TYPE_BIG) {
                iv = itemView.findViewById<View>(R.id.rowImageView) as ImageView
                title = itemView.findViewById<View>(R.id.rowTextView) as TextView
                info = itemView.findViewById<View>(R.id.rowTextView2) as TextView
                price = itemView.rowTextView3
                cv= itemView.rowCheckBox
                //checkBox = (CheckBox) itemView.findViewById(R.id.rowCheckBox);
            } else {
                iv = itemView.SrowImageView
                title = itemView.SrowTextView
                info = itemView.SrowTextView2
                cv=itemView.checkBox
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
