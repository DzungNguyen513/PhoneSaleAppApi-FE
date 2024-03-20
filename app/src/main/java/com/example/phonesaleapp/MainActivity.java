package com.example.phonesaleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.phonesaleapp.view.account.AccountFragment;
import com.example.phonesaleapp.view.shoppingcart.CartFragment;
import com.example.phonesaleapp.view.home.HomeFragment;
import com.example.phonesaleapp.view.notification.NotiFragment;
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == -99) {
            showAccountFragment();
        }
    }

    public void showAccountFragment() {
        Fragment accountFragment = AccountFragment.newInstance(email);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_app, accountFragment).commit();
        bottomNav.setSelectedItemId(R.id.action_MyPage);
    }
}

