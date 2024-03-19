package com.example.phonesaleapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.AccountOptionsAdapter;
import com.example.phonesaleapp.model.AccountOption;
import com.example.phonesaleapp.view.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {
    private static final String ARG_EMAIL = "email";
    private String email;
    private RecyclerView mRecyclerView;
    private AccountOptionsAdapter mAdapter;
    private List<AccountOption> mOptionList;
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
        mRecyclerView = view.findViewById(R.id.rvAccountOptions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mOptionList = new ArrayList<>();
        mOptionList.add(new AccountOption("Chỉnh sửa trang cá nhân", R.drawable.icon_editprofile));
        mOptionList.add(new AccountOption("Địa chỉ giao hàng", R.drawable.icon_location));
        mOptionList.add(new AccountOption("Lịch sử mua hàng", R.drawable.hitory_search));
        mOptionList.add(new AccountOption("Đổi Mật Khẩu", R.drawable.icon_changepass));
        mOptionList.add(new AccountOption("Đăng xuất",R.drawable.signout));

        mAdapter = new AccountOptionsAdapter(mOptionList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AccountOptionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Solve(position);
            }
        });
        return view;
    }
    private void Solve(int position) {
        AccountOption option = mOptionList.get(position);
        switch (option.getTitle()) {
            case "Chỉnh sửa trang cá nhân":
                Intent intent1 = new Intent(getContext(), EditAccountActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                break;
            case "Địa chỉ giao hàng":
                break;
            case "Lịch sử mua hàng":
                break;
            case "Đổi Mật Khẩu":
                break;
            case "Đăng xuất":
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }

}
