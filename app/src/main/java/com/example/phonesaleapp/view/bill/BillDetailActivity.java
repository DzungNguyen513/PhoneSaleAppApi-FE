package com.example.phonesaleapp.view.bill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.phonesaleapp.MainActivity;
import com.example.phonesaleapp.R;

public class BillDetailActivity extends AppCompatActivity {
    ImageView img_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        AnhXa();
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BillDetailActivity.this, BillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
    private void AnhXa(){
        img_Back = this.findViewById(R.id.img_Back);
    }
}