package com.example.phonesaleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.phonesaleapp.view.AccountFragment;
import com.example.phonesaleapp.view.CartFragment;
import com.example.phonesaleapp.view.HomeFragment;
import com.example.phonesaleapp.view.NotiFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = getIntent().getStringExtra("email");
        bottomNav = this.findViewById(R.id.bottomnav);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_app, new HomeFragment()).commit();
        bottomNav.setSelectedItemId(R.id.action_Home);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.action_Home){
                fragment = new HomeFragment();
            }else if (itemId == R.id.action_Cart){
                fragment = CartFragment.newInstance(email);
            } else if (itemId == R.id.action_Noti) {
                fragment = new NotiFragment();
            } else if (itemId == R.id.action_MyPage) {
                fragment = AccountFragment.newInstance(email);
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_app, fragment).commit();
                item.setChecked(true);
            }
            return true;
        });

    }
}

