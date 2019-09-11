package com.amuyu.melitos;

import android.os.Bundle;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MenuActivity extends AppCompatActivity implements OnBubbleClickListener {
    AppBarConfiguration appBarConfiguration;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
       // BottomNavigationView navView = findViewById(R.id.nav_view);
        BubbleTabBar sd = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,R.id.navigation_sales, R.id.navigation_notifications, R.id.navigation_settings)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);}

        //NavigationUI.setupWithNavController(navView, navController);
        sd.addBubbleListener(this);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onBubbleClick(int i) {
        NavigationUI.navigateUp(navController, appBarConfiguration);
        navController.navigate(i);
    }
}
