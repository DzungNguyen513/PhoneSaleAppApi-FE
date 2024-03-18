package com.example.phonesaleapp.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.phonesaleapp.R;

public class SlashActivity extends AppCompatActivity {

    private Handler handler;
    private Intent intent;
    TextView tv_hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash);
        AnhXa();
        handler = new Handler();
        handler.postDelayed(()->nextLoginActivity(), 2000);
        handler.postDelayed(()->{
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 4000);
    }

    private void nextLoginActivity() {
    }

    private void AnhXa(){
        tv_hello = this.findViewById(R.id.tv_hello);
    }
}