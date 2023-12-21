package com.example.appointmentscheduling.superAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.appointmentscheduling.MainHomeActivity;
import com.example.appointmentscheduling.R;
import com.example.appointmentscheduling.admin.AdminDraw;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appointmentscheduling.databinding.ActivityMainHomeBinding;

public class SuperAdminDraw extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_main_home);
        Toolbar toolbar = findViewById(R.id.sup_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.super_drawer_layout);
        NavigationView navigationView = findViewById(R.id.super_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_welcome, R.id.nav_createsch, R.id.nav_viewsch,R.id.nav_notification,R.id.nav_institute,R.id.nav_admin_requests,R.id.nav_admin_accepted_centers)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.sup_nav_host_fragment_content_main_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.super_admin_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sup_action_logout:
                Intent i = new Intent(SuperAdminDraw.this, MainHomeActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.sup_nav_host_fragment_content_main_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}