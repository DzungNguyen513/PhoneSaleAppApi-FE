package com.example.phonesaleapp.view.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.model.systemnotification.SystemNotification;

import retrofit2.Call;

public class NotificationDetailActivity extends AppCompatActivity {
    ImageView img_Back;
    TextView tv_titleNoti, tv_dateNoti, tv_contentNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        AnhXa();
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadNotiDetail();
    }
    private void loadNotiDetail() {
        Intent intent = getIntent();
        SystemNotification notification = (SystemNotification) intent.getSerializableExtra("notification");
        if (notification != null) {
            tv_titleNoti.setText(notification.getTitle());
            tv_dateNoti.setText(notification.getCreatedAt());
            tv_contentNoti.setText(notification.getMessage());
        }
    }
    private void AnhXa(){
        img_Back = this.findViewById(R.id.img_Back);
        tv_titleNoti = this.findViewById(R.id.tv_titleNoti);
        tv_dateNoti = this.findViewById(R.id.tv_dateNoti);
        tv_contentNoti = this.findViewById(R.id.tv_contentNoti);
    }
}