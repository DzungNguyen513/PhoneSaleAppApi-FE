package com.example.phonesaleapp.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.phonesaleapp.R;

public class AccountInfoActivity extends AppCompatActivity {
    ImageView img_back, img_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        AnhXa();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountInfoActivity.this, EditAccountActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa(){
        img_back = this.findViewById(R.id.img_back);
        img_setting = this.findViewById(R.id.img_setting);
    }
}