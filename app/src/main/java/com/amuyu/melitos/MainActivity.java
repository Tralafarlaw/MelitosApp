package com.amuyu.melitos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
   FirebaseAuth auth = FirebaseAuth.getInstance();
    NavController navController;
   @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_b);
        navController = Navigation.findNavController(this, R.id.fragmentsdf);
    }
}
