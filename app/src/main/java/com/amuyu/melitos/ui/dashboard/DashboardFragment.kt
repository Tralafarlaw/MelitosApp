package com.amuyu.melitos.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amuyu.melitos.C

import com.amuyu.melitos.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {
    lateinit var namef : EditText
    lateinit var pricef :EditText
    lateinit var stock: EditText
    lateinit var btn: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val v=inflater.inflate(R.layout.fragment_dashboard, container, false)
        namef = v.ename
        pricef = v.eprecio
        stock = v.estck
        btn = v.ebtn
        btn.setOnClickListener { v->send() }
        return v
    }
    fun send(){
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid+""
        val db = FirebaseFirestore.getInstance()
        val pro = hashMapOf(
            C.cPName to namef.text.toString(),
            C.cPPrice to pricef.text.toString(),
            C.cPStock to stock.text.toString()
        )

        db.collection(C.Root).document(C.cProducto).collection(uid).add(pro)
    }
}