package com.amuyu.melitos.ui.logo;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amuyu.melitos.CONSTANTES;
import com.amuyu.melitos.R;
import com.amuyu.melitos.ui.login.Login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class Logo extends Fragment {


    public Logo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRoot =  inflater.inflate(R.layout.fragment_logo, container, false);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                FirebaseAuth auth = FirebaseAuth.getInstance();
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
                if (auth.getCurrentUser() != null || account!=null){
                    NavHostFragment.findNavController(Logo.this).navigate(R.id.menuActivity, null);
                }else {
                    NavHostFragment.findNavController(Logo.this).navigate(R.id.f_login, null);
                }
            };
        }, CONSTANTES.DURACION_SPLASH);
        return mRoot;
    }

}
