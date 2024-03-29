package com.amuyu.melitos.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amuyu.melitos.C;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

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
        startActivityForResult(signInIntent, C.RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == C.RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    SweetAlertDialog pDialog;
    private void RSignin (){

        auth = FirebaseAuth.getInstance();
        Mail = fUSer.getText().toString();
        Pass = fUSer.getText().toString();

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent ));
        pDialog.setTitleText(getString(R.string.loading));
        pDialog.setCancelable(false);
        pDialog.show();
        if(!Mail.equals("") && !Pass.equals("")) {
            auth.createUserWithEmailAndPassword(Mail, Pass).addOnSuccessListener(this).addOnFailureListener(getActivity(), this);
        }else {
            if(Pass.equals("")) {
                lPass.setError(getText(R.string.empty_field));
            }
            if(Mail.equals("")){
                lUser.setError(getText(R.string.empty_field));
            }
            pDialog.dismissWithAnimation();
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
                    lUser.setEnabled(true);
                    lPass.setEnabled(true);
                    if(e instanceof FirebaseAuthInvalidUserException){
                        Toast.makeText(getContext(),R.string.error_wrong_mail , Toast.LENGTH_LONG).show();
                    }
                    if(e instanceof FirebaseAuthInvalidCredentialsException){
                        lPass.setError(getText(R.string.error_wrong_pass));
                    }
                }
            });
        }else {
            pDialog.dismissWithAnimation();

            if(e instanceof FirebaseAuthWeakPasswordException){
                lPass.setError(getText(R.string.error_weak_pass));
            }
            if(e instanceof FirebaseAuthInvalidCredentialsException){
                lUser.setError(getText(R.string.error_mail));
            }

        }
    }

    @Override
    public void onSuccess(AuthResult authResult) {
        pDialog.dismissWithAnimation();
        NavHostFragment.findNavController(this).navigate(R.id.menuActivity);

    }


}
