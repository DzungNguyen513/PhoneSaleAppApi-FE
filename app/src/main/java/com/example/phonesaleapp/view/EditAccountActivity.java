package com.example.phonesaleapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.phonesaleapp.R;

public class EditAccountActivity extends AppCompatActivity {
    TextView tv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        AnhXa();
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAccountActivity.this, AccountFragment.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void AnhXa(){
        tv_back = this.findViewById(R.id.tv_back);
    }
}
