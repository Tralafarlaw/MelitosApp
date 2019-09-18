package com.amuyu.melitos.ui.Settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.amuyu.melitos.MainActivity
import com.amuyu.melitos.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.settings_fragment.view.*

class fr_Settings : Fragment() {
    lateinit var btn:Button
    private var mViewModel: SettingsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v =  inflater.inflate(R.layout.settings_fragment, container, false)
        btn = v.logout_btn
        btn.setOnClickListener { v -> logout() }
        return v
    }
    fun logout(){
        var aus = FirebaseAuth.getInstance();
        aus.signOut()
        val it = Intent(context, MainActivity::class.java)
        activity?.finish()
        startActivity(it)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    companion object {

        fun newInstance(): Settings {
            return Settings()
        }
    }

}