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
import com.example.phonesaleapp.api.request.customer.CustomerResponse;
import com.example.phonesaleapp.api.service.BillService;
import com.example.phonesaleapp.api.service.CustomerService;
import com.example.phonesaleapp.model.Bill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickUpFragment extends Fragment {
    LinearLayout ln_notBill;
    RecyclerView rcv_pickupBill;
    BillItemAdapter adapter;
    List<Bill> lstBill = new ArrayList<>();
    String email = UserInfo.getInstance().getEmail();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickup, container, false);
        AnhXa(view);
        loadBill();
        return view;
    }

    private void loadBill() {
        CustomerService customerService = RetrofitClient.getClient().create(CustomerService.class);
        BillService billService = RetrofitClient.getClient().create(BillService.class);
        Call<CustomerResponse> customerIdCall = customerService.getCustomerIDByEmail(email);
        customerIdCall.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if(response.isSuccessful()){
                    CustomerResponse customerResponse = response.body();
                    String customerId = customerResponse.getCustomerId();
                    Call<List<Bill>> billCall = billService.getBillOfCustomerID(customerId, BillStatus.ChoLayHang);
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
                                    rcv_pickupBill.setVisibility(View.GONE);
                                } else {
                                    ln_notBill.setVisibility(View.GONE);
                                    rcv_pickupBill.setVisibility(View.VISIBLE);
                                }
                            } else {
                                ln_notBill.setVisibility(View.VISIBLE);
                                rcv_pickupBill.setVisibility(View.GONE);
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
            public void onFailure(Call<CustomerResponse> call, Throwable throwable) {
                ln_notBill.setVisibility(View.VISIBLE);
                rcv_pickupBill.setVisibility(View.GONE);
            }
        });
    }

    private void AnhXa(View view){
        adapter = new BillItemAdapter(getContext(), lstBill);
        ln_notBill = view.findViewById(R.id.ln_notBill);
        rcv_pickupBill = view.findViewById(R.id.rcv_pickupBill);
        rcv_pickupBill.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_pickupBill.setAdapter(adapter);
    }
}
