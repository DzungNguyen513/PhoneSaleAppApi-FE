package com.example.phonesaleapp.view.bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.UserInfo;
import com.example.phonesaleapp.adapter.bill.BillItemAdapter;
import com.example.phonesaleapp.adapter.bill.BillStatus;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.customer.CustomerIdResponse;
import com.example.phonesaleapp.api.service.BillService;
import com.example.phonesaleapp.api.service.CustomerService;
import com.example.phonesaleapp.model.bill.Bill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DestroyBillFragment extends Fragment {
    LinearLayout ln_notBill;
    RecyclerView rcv_destroyBill;
    BillItemAdapter adapter;
    List<Bill> lstBill = new ArrayList<>();
    String email = UserInfo.getInstance().getEmail();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destroy_bill, container, false);
        AnhXa(view);
        loadBill();
        return view;
    }
    private void loadBill() {
        CustomerService customerService = RetrofitClient.getClient().create(CustomerService.class);
        BillService billService = RetrofitClient.getClient().create(BillService.class);
        Call<CustomerIdResponse> customerIdCall = customerService.getCustomerIDByEmail(email);
        customerIdCall.enqueue(new Callback<CustomerIdResponse>() {
            @Override
            public void onResponse(Call<CustomerIdResponse> call, Response<CustomerIdResponse> response) {
                if(response.isSuccessful()){
                    CustomerIdResponse customerResponse = response.body();
                    String customerId = customerResponse.getCustomerId();
                    Call<List<Bill>> billCall = billService.getBillOfCustomerID(customerId, BillStatus.DaHuy);
                    billCall.enqueue(new Callback<List<Bill>>() {
                        @Override
                        public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                            if (response.isSuccessful()) {
                                List<Bill> bills = response.body();
                                lstBill.clear();
                                lstBill.addAll(bills);
                                adapter.notifyDataSetChanged();
                                if (lstBill.isEmpty()) {
                                    ln_notBill.setVisibility(View.VISIBLE);
                                    rcv_destroyBill.setVisibility(View.GONE);
                                } else {
                                    ln_notBill.setVisibility(View.GONE);
                                    rcv_destroyBill.setVisibility(View.VISIBLE);
                                }
                            } else {
                                ln_notBill.setVisibility(View.VISIBLE);
                                rcv_destroyBill.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Bill>> call, Throwable throwable) {
                            Toast.makeText(getContext(), "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            ln_notBill.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CustomerIdResponse> call, Throwable throwable) {
                ln_notBill.setVisibility(View.VISIBLE);
                rcv_destroyBill.setVisibility(View.GONE);
            }
        });
    }
    private void AnhXa(View view){
        adapter = new BillItemAdapter(getContext(), lstBill);
        rcv_destroyBill = view.findViewById(R.id.rcv_destroyBill);
        rcv_destroyBill.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_destroyBill.setAdapter(adapter);
        ln_notBill = view.findViewById(R.id.ln_notBill);
    }
}
