package com.amuyu.melitos.ui.ProductoDialog

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox

import com.amuyu.melitos.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.product_dialog_fragment.view.*


class ProductDIalog : DialogFragment() {
    lateinit var root: View
    lateinit var call: Callback
    lateinit var nam: TextInputEditText
    lateinit var buyP :TextInputEditText
    lateinit var sellP :TextInputEditText
    lateinit var stock: TextInputEditText
    lateinit var minStock : TextInputEditText
    lateinit var maxOff : TextInputEditText
    lateinit var desc: TextInputEditText
    lateinit var isStock: CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, com.amuyu.melitos.R.style.FullscreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root  = inflater.inflate(com.amuyu.melitos.R.layout.product_dialog_fragment, container, false)
        root.d_agre.setOnClickListener{v -> agre()}
        root.d_cancel.setOnClickListener{v -> cancel()}
        root.d_barcode.setOnClickListener{v -> barcode()}
        desc = root.d_desc_f
        isStock = root.d_is_stock
        maxOff = root.d_max_off_f
        stock = root.d_stock_f
        nam = root.d_name_f
        buyP = root.d_buy_f
        sellP = root.d_price_f
        minStock = root.d_min_stock
        return root
    }
    fun agre(){
        val name = nam.text.toString()
        val price = buyP.text.toString()


        val db = FirebaseFirestore.getInstance();

    }
    fun cancel (){}
    fun barcode(){}
    fun category(){}

    companion object {


        fun newInstance(): ProductDIalog {
            return ProductDIalog()
        }
    }

    interface Callback {

        fun onActionClick(name: String)

    }
}
