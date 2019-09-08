package com.amuyu.melitos.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amuyu.melitos.CONSTANTES;
import com.amuyu.melitos.MainActivity;
import com.amuyu.melitos.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.concurrent.Executor;

public class Login extends Fragment implements View.OnClickListener, OnSuccessListener<AuthResult>, OnFailureListener {
    SignInButton google_btn;
    Button regular_btn;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth;
    String Mail ="", Pass="";
    TextInputEditText fUSer, fPass;
    TextInputLayout lUser, lPass;
    View mRoot;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mRoot = inflater.inflate(R.layout.fragment_login, container, false);
        init();
        adjust();
        return mRoot;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.signin):{
                RSignin();
            }break;
            case (R.id.google_signin): {
                GsignIn();
            }break;
        }
    }
    private void init(){
        // inicializar campos
        regular_btn =   mRoot.findViewById(R.id.signin);
        google_btn =    mRoot.findViewById(R.id.google_signin);
        fUSer =         mRoot.findViewById(R.id.login_user_f);
        fPass =         mRoot.findViewById(R.id.login_pass_f);
        lUser =         mRoot.findViewById(R.id.login_user_l);
        lPass =         mRoot.findViewById(R.id.login_pass_l);
    }
    private void adjust(){
        //ajustar campos
        regular_btn.setOnClickListener(this);
        google_btn.setOnClickListener(this);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }
    private void GsignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, CONSTANTES.RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CONSTANTES.RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void RSignin (){

        auth = FirebaseAuth.getInstance();
        Mail = fUSer.getText().toString();
        Pass = fPass.getText().toString();
        lUser.setErrorEnabled(false);
        lPass.setErrorEnabled(false);
        fUSer.setEnabled(false);
        fPass.setEnabled(false);
        if(!Mail.equals("") && !Pass.equals("")) {
            auth.createUserWithEmailAndPassword(Mail, Pass).addOnSuccessListener(getActivity(), this).addOnFailureListener(getActivity(), this);
        }


    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        //GoogleSignInAccount account = completedTask.getResult(ApiException.class);
        // Signed in successfully, show authenticated UI.
        NavHostFragment.findNavController(this).navigate(R.id.menuActivity);
    }



    @Override
    public void onFailure(@NonNull Exception e) {
        if(e instanceof FirebaseAuthUserCollisionException){
            auth.signInWithEmailAndPassword(Mail, Pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    NavHostFragment.findNavController(Login.this).navigate(R.id.menuActivity);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    fUSer.setEnabled(true);
                    fPass.setEnabled(true);
                    if(e instanceof FirebaseAuthInvalidUserException){
                        Toast.makeText(getContext(),R.string.error_suspended_account , Toast.LENGTH_LONG).show();
                    }
                    if(e instanceof FirebaseAuthInvalidCredentialsException){
                        fPass.setError(getText(R.string.error_wrong_pass));
                    }
                }
            });
        }else {
            fUSer.setEnabled(true);
            fPass.setEnabled(true);
            if(e instanceof FirebaseAuthWeakPasswordException){
                fPass.setError(getText(R.string.error_weak_pass));
            }
            if(e instanceof FirebaseAuthInvalidCredentialsException){
                fUSer.setError(getText(R.string.error_mail));
            }

        }
    }

    @Override
    public void onSuccess(AuthResult authResult) {
        NavHostFragment.findNavController(this).navigate(R.id.menuActivity);

    }


}
