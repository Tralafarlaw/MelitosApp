package com.amuyu.melitos;

import android.os.Bundle;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MenuActivity extends AppCompatActivity implements OnBubbleClickListener {

    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BubbleTabBar sd = findViewById(R.id.nav_view);


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        sd.addBubbleListener(this);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onBubbleClick(int i) {
        navController.navigate(i);
    }
}
