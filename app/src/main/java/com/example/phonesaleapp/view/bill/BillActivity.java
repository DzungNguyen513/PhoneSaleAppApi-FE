package com.example.phonesaleapp.view.bill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.phonesaleapp.MainActivity;
import com.example.phonesaleapp.R;

import com.example.phonesaleapp.UserInfo;
import com.example.phonesaleapp.adapter.bill.BillPagerAdapter;
import com.example.phonesaleapp.view.shoppingcart.ChatMessageActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.viewpager2.widget.ViewPager2;

public class BillActivity extends AppCompatActivity {
    ImageView img_Back, img_searchBill, img_message;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    String email = UserInfo.getInstance().getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        AnhXa();
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Chờ xác nhận");
                        break;
                    case 1:
                        tab.setText("Chờ lấy hàng");
                        break;
                    case 2:
                        tab.setText("Chờ giao hàng");
                        break;
                    case 3:
                        tab.setText("Đã giao");
                        break;
                    case 4:
                        tab.setText("Đã hủy");
                        break;
                }
            }
        }).attach();

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BillActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        img_searchBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BillActivity.this, SearchBillActivity.class);
                startActivity(intent);
            }
        });
        img_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BillActivity.this, ChatMessageActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
    private void AnhXa(){
        img_Back = this.findViewById(R.id.img_Back_Search);
        img_searchBill = this.findViewById(R.id.img_searchBill);
        img_message = this.findViewById(R.id.img_message);
        viewPager = this.findViewById(R.id.view_pager);
        tabLayout = this.findViewById(R.id.tabs);
        viewPager.setAdapter(new BillPagerAdapter(this));
    }
}
