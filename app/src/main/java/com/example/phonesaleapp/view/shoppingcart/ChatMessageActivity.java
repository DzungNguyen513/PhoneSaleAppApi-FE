package com.example.phonesaleapp.view.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.phonesaleapp.R;

public class ChatMessageActivity extends AppCompatActivity {
    ImageView img_Back;
    String customerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        AnhXa();
        customerEmail = getIntent().getStringExtra("email");
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        img_Back = findViewById(R.id.img_Back_Search);
    }
}
