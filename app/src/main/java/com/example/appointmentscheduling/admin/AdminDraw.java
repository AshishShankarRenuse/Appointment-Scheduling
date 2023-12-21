package com.example.appointmentscheduling.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.appointmentscheduling.MainHome;
import com.example.appointmentscheduling.MainHomeActivity;
import com.example.appointmentscheduling.R;
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

public class AdminDraw extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_home);
        Toolbar toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.admin_drawer_layout);
        NavigationView navigationView = findViewById(R.id.admin_nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.ad_nav_home, R.id.ad_nav_fc_day, R.id.ad_nav_applied_fc,R.id.ad_nav_apply_fc)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.admin_nav_host_fragment_content_main_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Bundle extra = getIntent().getExtras();
        Log.d("username",extra.getString("username"));
        String uname = extra.getString("username");

        Bundle bundle = new Bundle();
        bundle.putString("username",uname);


        AdApplyForFc applyFc = new AdApplyForFc();
        applyFc.setUsername(uname);

        AdminMyAppliedFc myApplied = new AdminMyAppliedFc();
        myApplied.setUsername(uname);

        FCDayFragment.usernameFinal = uname;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_main_draw, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.admin_action_logout:
                Intent i = new Intent(AdminDraw.this, MainHomeActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.admin_nav_host_fragment_content_main_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}