package com.example.phonesaleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.phonesaleapp.view.account.AccountFragment;
import com.example.phonesaleapp.view.shoppingcart.CartFragment;
import com.example.phonesaleapp.view.home.HomeFragment;
import com.example.phonesaleapp.view.notification.NotiFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("com.example.phonesaleapp")
                .addOnCompleteListener(task -> {
                    String msg = "Thành công";
                    if (!task.isSuccessful()) {
                        msg = "Lỗi";
                    }
                    Log.d("TAG", msg);
                });

        email = getIntent().getStringExtra("email");
        UserInfo.getInstance().setEmail(email);
        bottomNav = this.findViewById(R.id.bottomnav);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                item.setChecked(true);
            }
            return true;
        });
    }
}

