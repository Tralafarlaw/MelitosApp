package com.amuyu.melitos.ui.vender

import android.app.Dialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.get

import com.amuyu.melitos.Adapters.VentasAdapter
import com.amuyu.melitos.R

import java.util.ArrayList

import com.amuyu.melitos.Adapters.VentasAdapter.Companion.SPAN_COUNT_ONE
import com.amuyu.melitos.Adapters.VentasAdapter.Companion.SPAN_COUNT_THREE
import com.amuyu.melitos.C
import com.amuyu.melitos.ui.ProductsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.ventas_fragment.view.*
import stream.customalert.CustomAlertDialogue

class ventas : Fragment() {
    val TAG = "VENTAS FRAGMENT TAG"
    lateinit var mViewModel: ProductsViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var itemAdapter: VentasAdapter
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var cart:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val v = inflater.inflate(R.layout.ventas_fragment, container, false)
        recyclerView = v.rv
        cart = v.lookup
        cart.setOnClickListener{_->showcart()}
        return v
    }
    fun showcart(){
        var list = arrayListOf<String>()
        for(aux in itemAdapter.items){
            if(aux.cv.isChecked){
                list.add(aux.title.text.toString())
            }
        }
        showdialog(list)
    }
    fun showdialog(list: ArrayList<String>){
        val alert = CustomAlertDialogue.Builder(context)
            .setStyle(CustomAlertDialogue.Style.ACTIONSHEET)
            .setTitle("Carrito")
            .setTitleColor(com.amuyu.melitos.R.color.md_text_white_87)
            .setCancelText("Aceptar")
            .setOnCancelClicked(object : CustomAlertDialogue.OnCancelClicked {
                override fun OnClick(view: View, dialog: Dialog) {
                    acept(dialog)
                }
            })
            .setOthers(list)
            .setDecorView(activity?.window?.getDecorView())
            .build()
        alert.show()
    }
    fun acept(dialog: Dialog){
        dialog.dismiss()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        // TODO: Use the ViewModel

        gridLayoutManager = GridLayoutManager(context, SPAN_COUNT_ONE)
        itemAdapter = VentasAdapter(gridLayoutManager!!)
        itemAdapter.sw =true
        recyclerView.adapter = itemAdapter
        recyclerView.layoutManager = gridLayoutManager
        mViewModel.getUsers().observe(this, Observer { ventasItems ->
            itemAdapter.setmItems(ventasItems)
            itemAdapter.notifyDataSetChanged()
        })
        set_l()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_switch_layout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_switch_layout) {
            switchLayout()
            switchIcon(item)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchLayout() {
        if (gridLayoutManager!!.spanCount == SPAN_COUNT_ONE) {
            gridLayoutManager!!.spanCount = SPAN_COUNT_THREE
        } else {
            gridLayoutManager!!.spanCount = SPAN_COUNT_ONE
        }
        itemAdapter!!.notifyItemRangeChanged(0, itemAdapter!!.itemCount)
    }

    private fun switchIcon(item: MenuItem) {
        if (gridLayoutManager!!.spanCount == SPAN_COUNT_THREE) {
            item.icon = resources.getDrawable(R.drawable.ic_span_3)
        } else {
            item.icon = resources.getDrawable(R.drawable.ic_span_1)
        }
    }

    companion object {

        fun newInstance(): ventas {
            return ventas()
        }
    }
    fun set_l(){
        val aut = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val uid = aut.currentUser?.uid.toString()
        db.collection(C.Root).document(C.cProducto).collection(uid).addSnapshotListener{ snapshot, e ->
            var list = ArrayList<Map<String, Any>>()
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                Log.e("ERoor DB", "Error al leer la base de latos para llenarla")
                Toast.makeText(context, R.string.db_error_read, Toast.LENGTH_LONG).show()
                return@addSnapshotListener

            }

            if (snapshot != null && !snapshot.isEmpty) {
                Log.d(TAG, "Current data: ${snapshot.documents.toString()}")
                val documents = snapshot.documents
                for (documet in documents){
                    list.add(documet.data as Map<String, Any>)
                }
                itemAdapter.setmItems(list)
                itemAdapter.notifyDataSetChanged()
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }


}
