package com.leencecodes.nifixiegari.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import com.leencecodes.nifixiegari.R;

public class MainActivity extends AppCompatActivity {


    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        DrawerLayout mDrawer = findViewById(R.id.drawerLayout);
        NavigationView mNavigationView = findViewById(R.id.navigationView);

        navController = Navigation.findNavController(this, R.id.fragment);

        //setup bottom navigation
        NavigationUI.setupWithNavController(mBottomNavigationView, navController);

        //Setup Top Back post_style_button
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(mDrawer).build();
        NavigationUI.setupActionBarWithNavController(this, navController, mDrawer);

        //Setup Navigation Drawer
        NavigationUI.setupWithNavController(mNavigationView, navController);

    }

    //For Back button --top
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }
}