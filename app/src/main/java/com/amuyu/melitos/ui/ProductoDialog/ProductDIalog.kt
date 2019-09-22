package com.amuyu.melitos.ui.ProductoDialog

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import com.amuyu.melitos.C

import com.amuyu.melitos.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.product_dialog_fragment.view.*
import stream.customalert.CustomAlertDialogue
import android.app.Dialog
import android.transition.TransitionManager

import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.Task
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.new_category_dialog_layout.view.*


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
    lateinit var isminStock: CheckBox
    lateinit var isoff: CheckBox
    lateinit var chipGroup: ChipGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, com.amuyu.melitos.R.style.FullscreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root  = inflater.inflate(com.amuyu.melitos.R.layout.product_dialog_fragment, container, false)
        root.d_agre.setOnClickListener{_ -> agre()}
        root.d_cancel.setOnClickListener{_ -> cancel()}
        root.d_barcode.setOnClickListener{_ -> barcode()}
        root.d_cat.setOnClickListener{_-> category()}
        root.fullscreen_dialog_close.setOnClickListener{_->dismiss()}
        desc = root.d_desc_f
        isStock = root.d_is_stock
        maxOff = root.d_max_off_f
        stock = root.d_stock_f
        nam = root.d_name_f
        buyP = root.d_buy_f
        sellP = root.d_price_f
        minStock = root.d_min_stock
        isStock.setOnCheckedChangeListener{_, on ->
            root.d_stock_l.isEnabled = on
        }
        isminStock =root.d_is_off
        isminStock.setOnCheckedChangeListener{_, on ->
            root.d_max_off_l.isEnabled = on
        }
        isoff = root.d_is_min
        isoff.setOnCheckedChangeListener{_, on ->
            root.d_min_stock_l.isEnabled = on
        }
        chipGroup = root.d_chip_group
        return root
    }
    fun agre(){
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        val map = hashMapOf(
            C.cPName to nam.text.toString(),
            C.cPPrice to buyP.text.toString(),
            C.cPCost to sellP.text.toString(),
            C.cPStock to stock.text.toString(),
            C.cPMinStock to minStock.text.toString(),
            C.cPMaxoff to maxOff.text.toString(),
            C.cPDesc to desc.text.toString(),
            C.sPMinStock to isminStock.isChecked,
            C.sPStock to isStock.isChecked,
            C.cPOff to isoff.isChecked
        )
        val db = FirebaseFirestore.getInstance();
        if (uid != null) {
            db.collection(C.Root).document(C.cProducto).collection(uid).add(map).addOnSuccessListener{
                Toast.makeText(context, "Producto creado con exito", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }else{
            Toast.makeText(context, "User not loged", Toast.LENGTH_LONG).show()
            dismiss()
        }

    }
    fun cancel (){
        dismiss()
    }
    fun barcode(){}
    fun category(){
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val db = FirebaseFirestore.getInstance()
        var lis = arrayListOf<String>()
        db.collection(C.Root).document(C.cCategory).collection(uid).get().addOnSuccessListener{
            for (document in it.documents){
                var aux = document.get(C.cCName)
                lis.add(aux.toString())
            }
            val alert = CustomAlertDialogue.Builder(context)
                .setStyle(CustomAlertDialogue.Style.ACTIONSHEET)
                .setTitle(C.cCategory)
                .setTitleColor(com.amuyu.melitos.R.color.md_text_white_87)
                .setCancelText("Nuevo")
                .setOnCancelClicked(object : CustomAlertDialogue.OnCancelClicked {
                    override fun OnClick(view: View, dialog: Dialog) {
                        new_cat()
                    }
                })
                .setOthers(lis)
                .setOnItemClickListener { adapterView, _, i, _ ->
                    val selection = adapterView.getItemAtPosition(i).toString()
                    addchip(selection)
                }
                .setDecorView(activity?.window?.getDecorView())
                .build()
            alert.show()
        }


    }

    fun addchip(name: String){
        val chip = Chip(chipGroup.context)
        chip.text = name
        chip.isClickable = true
        chip.isCheckable = false
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener{
            TransitionManager.beginDelayedTransition(chipGroup)
            chipGroup.removeView(it)
        }
        chipGroup.addView(chip)
    }
    fun new_cat (){

        val li = LayoutInflater.from(context)
        val promptsView = li.inflate(com.amuyu.melitos.R.layout.new_category_dialog_layout, null)
        var alertDialogBuilder = AlertDialog.Builder(context!!, R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        alertDialogBuilder.setView(promptsView);
        var text = promptsView.new_cat_input
        alertDialogBuilder.setCancelable(true)
        alertDialogBuilder.setTitle(R.string.new_cat_title)
        alertDialogBuilder.setPositiveButton("Aceptar") { _,_ -> inagre(text)}
        alertDialogBuilder.setNegativeButton("Cancelar") { i, _ -> i.dismiss()}

        val dialog = alertDialogBuilder.create()

        dialog.show()
    }
    companion object {


        fun newInstance(): ProductDIalog {
            return ProductDIalog()
        }
    }
    fun inagre(text: TextInputEditText){
        var db = FirebaseFirestore.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val map = hashMapOf(
            C.cCName to text.text.toString()
        )
        db.collection(C.Root).document(C.cCategory).collection(uid).add(map).addOnCompleteListener{ite -> oncom(ite)}
    }
    fun oncom(it: Task<DocumentReference>){
        if(it.isSuccessful){
            Toast.makeText(context, "Categoria añadida con exito", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
        }
    }
    interface Callback {

        fun onActionClick(name: String)

    }


}
