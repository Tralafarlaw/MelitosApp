package com.amuyu.melitos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
