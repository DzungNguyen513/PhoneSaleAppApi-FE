package com.example.phonesaleapp.view.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.account.AccountOptionAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.CustomerService;
import com.example.phonesaleapp.model.account.AccountOption;
import com.example.phonesaleapp.model.customer.CustomerByEmailDTO;
import com.example.phonesaleapp.view.bill.BillActivity;
import com.example.phonesaleapp.view.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    private static final String ARG_EMAIL = "email";
    ImageView img_profile;
    TextView tv_customerName;
    String email;
    RecyclerView mRecyclerView;
    AccountOptionAdapter adapter;
    List<AccountOption> mOptionList = new ArrayList<>();;
    public AccountFragment() {

    }

    public static AccountFragment newInstance(String email) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        AnhXa(view);

        mOptionList.add(new AccountOption("Chỉnh sửa trang cá nhân", R.drawable.icon_editprofile));
        mOptionList.add(new AccountOption("Địa chỉ giao hàng", R.drawable.icon_location));
        mOptionList.add(new AccountOption("Lịch sử mua hàng", R.drawable.hitory_search));
        mOptionList.add(new AccountOption("Đổi Mật Khẩu", R.drawable.icon_changepass));
        mOptionList.add(new AccountOption("Đăng xuất",R.drawable.signout));

        adapter.setOnItemClickListener(new AccountOptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Solve(position);
            }
        });
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AccountInfoActivity.class);
                startActivity(intent);
            }
        });
        getCustomerName();
        return view;
    }
    private void getCustomerName(){
        CustomerService customerService = RetrofitClient.getClient().create(CustomerService.class);
        Call<CustomerByEmailDTO> customerCall = customerService.getCustomerByEmail(email);
        customerCall.enqueue(new Callback<CustomerByEmailDTO>() {
            @Override
            public void onResponse(Call<CustomerByEmailDTO> call, Response<CustomerByEmailDTO> response) {
                if (response.isSuccessful()){
                    CustomerByEmailDTO customer = response.body();
                    tv_customerName.setText(customer.getCustomerName().toString().trim());
                }
            }
            @Override
            public void onFailure(Call<CustomerByEmailDTO> call, Throwable throwable) {

            }
        });
    }
    private void Solve(int position) {
        AccountOption option = mOptionList.get(position);
        switch (option.getTitle()) {
            case "Chỉnh sửa trang cá nhân":
                Intent intent1 = new Intent(getContext(), AccountInfoActivity.class);
                startActivity(intent1);
                break;
            case "Địa chỉ giao hàng":
                showSnackbar("Hệ thống đang phát triển");
                /*Intent intent2 = new Intent(getContext(), AddressActivity.class);
                startActivity(intent2);*/
                break;
            case "Lịch sử mua hàng":
                Intent intent3 = new Intent(getContext(), BillActivity.class);
                startActivity(intent3);
                break;
            case "Đổi Mật Khẩu":
                Intent intent4 = new Intent(getContext(), ChangePassActivity.class);
                startActivity(intent4);
                break;
            case "Đăng xuất":
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }

    private void showSnackbar(String message) {
        View rootView = getActivity().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(params);
        snackbar.setAction("Đóng", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    private void AnhXa(View view){
        adapter = new AccountOptionAdapter(mOptionList);
        img_profile = view.findViewById(R.id.img_profile);
        mRecyclerView = view.findViewById(R.id.rvAccountOptions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        tv_customerName = view.findViewById(R.id.tv_customerName);
    }
}
