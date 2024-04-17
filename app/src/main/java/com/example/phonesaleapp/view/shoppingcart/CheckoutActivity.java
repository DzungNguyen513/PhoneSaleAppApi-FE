package com.example.phonesaleapp.view.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.CheckoutProductAdapter;
import com.example.phonesaleapp.adapter.ProductCartAdapter;
import com.example.phonesaleapp.model.ProductCart;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    ImageView img_back;
    Button btn_checkOut;
    TextView tv_TotalCheck;
    RecyclerView rvCheckoutItems;
    private CheckoutProductAdapter adapter;
    List<ProductCart> checkoutItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        AnhXa();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        checkoutItems = (List<ProductCart>) getIntent().getSerializableExtra("selectedItems");
        adapter = new CheckoutProductAdapter(this,checkoutItems);
        rvCheckoutItems.setAdapter(adapter);
        String totalPayment = getIntent().getStringExtra("totalPayment");
        tv_TotalCheck.setText(totalPayment);
    }
    private void AnhXa(){
        img_back = this.findViewById(R.id.img_back);
        rvCheckoutItems = this.findViewById(R.id.rvCheckoutItems);
        rvCheckoutItems.setLayoutManager(new LinearLayoutManager(this));
        btn_checkOut = this.findViewById(R.id.btn_checkOut);
        tv_TotalCheck = this.findViewById(R.id.tv_TotalCheck);
    }
}