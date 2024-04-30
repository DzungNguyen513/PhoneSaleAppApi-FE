package com.example.phonesaleapp.view.bill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.UserInfo;
import com.example.phonesaleapp.adapter.bill.BillItemAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.request.customer.CustomerResponse;
import com.example.phonesaleapp.api.service.BillService;
import com.example.phonesaleapp.api.service.CustomerService;
import com.example.phonesaleapp.model.Bill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBillActivity extends AppCompatActivity {
    ImageView img_Back;
    EditText edt_searchBill;
    Button btn_searchBill;
    RecyclerView rcv_searchBill;
    BillItemAdapter adapter;
    List<Bill> lstBill = new ArrayList<>();
    String email = UserInfo.getInstance().getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bill);
        AnhXa();
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchBillActivity.this, BillActivity.class);
                startActivity(intent);
            }
        });
        btn_searchBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = edt_searchBill.getText().toString().trim();
                if (query.equals("")){
                    Toast.makeText(SearchBillActivity.this, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                    return;
                }
                searchBill(query);
            }
        });
        edt_searchBill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchBill(s.toString().trim());
            }
        });
    }
    private void searchBill(String query){
        CustomerService customerService = RetrofitClient.getClient().create(CustomerService.class);
        Call<CustomerResponse> customerIdCall = customerService.getCustomerIDByEmail(email);
        customerIdCall.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if(response.isSuccessful()){
                    String customerId = response.body().getCustomerId();
                    BillService billService = RetrofitClient.getClient().create(BillService.class);
                    Call<List<Bill>> billCall = billService.getBillByID(customerId, query);
                    billCall.enqueue(new Callback<List<Bill>>() {
                        @Override
                        public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                            if (response.isSuccessful()){
                                List<Bill> bills = response.body();
                                lstBill.clear();
                                lstBill.addAll(bills);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Bill>> call, Throwable throwable) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable throwable) {

            }
        });
    }
    private void AnhXa(){
        adapter = new BillItemAdapter(this, lstBill);
        img_Back = this.findViewById(R.id.img_Back);
        edt_searchBill = this.findViewById(R.id.edt_searchBill);
        btn_searchBill = this.findViewById(R.id.btn_searchBill);
        rcv_searchBill = this.findViewById(R.id.rcv_searchBill);
        rcv_searchBill.setLayoutManager(new LinearLayoutManager(this));
        rcv_searchBill.setAdapter(adapter);
    }
}