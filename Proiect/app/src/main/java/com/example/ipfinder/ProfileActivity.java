package com.example.ipfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {


    private ViewPager2 viewPager2;

    private ViewPageAdapter viewPageAdapter;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationView=findViewById(R.id.bottomNav);
        viewPager2=findViewById(R.id.viewPager);
        viewPageAdapter= new ViewPageAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.bottom_home: viewPager2.setCurrentItem(0);
                    break;
                    case R.id.bottom_location_finder: viewPager2.setCurrentItem(1);
                    break;
                    case R.id.bottom_tutorial: viewPager2.setCurrentItem(2);
                    break;
                }
                return false;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0: bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                    break;
                    case 1: bottomNavigationView.getMenu().findItem(R.id.bottom_location_finder).setChecked(true);
                    break;
                    case 2: bottomNavigationView.getMenu().findItem(R.id.bottom_tutorial).setChecked(true);
                    break;
                }
                super.onPageSelected(position);
            }
        });


    }
}
