package com.example.phonesaleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.CustomerService;
import com.example.phonesaleapp.model.customer.CustomerIdResponse;
import com.example.phonesaleapp.model.customer.TokenUpdateDTO;
import com.example.phonesaleapp.view.AlertBroadcastReceiver;
import com.example.phonesaleapp.view.account.AccountFragment;
import com.example.phonesaleapp.view.shoppingcart.CartFragment;
import com.example.phonesaleapp.view.home.HomeFragment;
import com.example.phonesaleapp.view.notification.NotiFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        loadTokenCustomerId();
        Intent intent = getIntent();
        if (intent != null) {
            String fragmentName = intent.getStringExtra("fragmentName");
            String email = intent.getStringExtra("email");
            if ("CartFragment".equals(fragmentName)) {
                displayCartFragment(email);
                bottomNav.setSelectedItemId(R.id.action_Cart);
            }
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(new AlertBroadcastReceiver(), filter);
    }
    private void displayCartFragment(String email) {
        CartFragment fragment = CartFragment.newInstance(email);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
    private void loadTokenCustomerId(){
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w("FCM", "Lỗi đăng kí Token", task.getException());
                    return;
                }
                String token = task.getResult();
                Log.d("FCM", "FCM Token: " + token);
                CustomerService service = RetrofitClient.getClient().create(CustomerService.class);
                Call<CustomerIdResponse> customerIdResponseCall = service.getCustomerIDByEmail(email);
                customerIdResponseCall.enqueue(new Callback<CustomerIdResponse>() {
                    @Override
                    public void onResponse(Call<CustomerIdResponse> call, Response<CustomerIdResponse> response) {
                        if (response.isSuccessful()){
                            String customerId = response.body().getCustomerId();
                            TokenUpdateDTO tokenUpdateDTO = new TokenUpdateDTO(token);
                            Call<Void> callToken = service.updateToken(customerId, tokenUpdateDTO);
                            callToken.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        Log.d("Update Token", "Token được tạo thành công");
                                    } else {
                                        Log.e("Update Token", "Lỗi tạo token " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("Update Token", "Lỗi tạo token: " + t.getMessage());
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<CustomerIdResponse> call, Throwable throwable) {
                    }
                });
            });

    }
}

