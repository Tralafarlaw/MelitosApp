package com.amuyu.melitos

import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.amuyu.melitos.ui.ProductsViewModel
import com.fxn.Bubble
import com.fxn.BubbleTabBar
import com.fxn.OnBubbleClickListener

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(), OnBubbleClickListener {
    lateinit var sd: BubbleTabBar
    lateinit var navController: NavController
    var is_f: Boolean? = null
    lateinit var aux: Bubble
    val TAG = "MENU ACTIVIT TAG"
    lateinit var model: ProductsViewModel
    val db = FirebaseFirestore.getInstance()
    val aut = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        model = ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        sd = nav_view
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        sd.addBubbleListener(this)
        aux = sd.getChildAt(2) as Bubble
        aux.isSelected = true
        is_f = true
        sd.invalidate()
        set_l()
    }
    override fun onBubbleClick(i: Int) {
        if (is_f!!) {
            is_f = false
            aux.isSelected = false
        }
        navController.navigate(i)
    }
    fun set_l(){
        var list = ArrayList<Map<String, Any>>()
        val uid = aut.currentUser?.uid.toString()
        db.collection(C.Root).document(C.cProducto).collection(uid).addSnapshotListener{ snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                Log.e("ERoor DB", "Error al leer la base de latos para llenarla")
                Toast.makeText(this, R.string.db_error_read, Toast.LENGTH_LONG).show()
                return@addSnapshotListener

            }

            if (snapshot != null && !snapshot.isEmpty) {
                Log.d(TAG, "Current data: ${snapshot.documents.toString()}")
                val documents = snapshot.documents
                for (documet in documents){
                    list.add(documet.data as Map<String, Any>)
                }
                model.setUsers(list)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }
}
