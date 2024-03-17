package com.example.phonesaleapp.view;

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

        mAdapter = new AccountOptionsAdapter(mOptionList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
