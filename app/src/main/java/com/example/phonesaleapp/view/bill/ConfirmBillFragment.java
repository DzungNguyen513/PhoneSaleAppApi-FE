package com.example.phonesaleapp.view.bill;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;

public class ConfirmBillFragment extends Fragment {
    RecyclerView rcv_confirmBill;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_bill, container, false);
        AnhXa(view);
        return view;
    }
    private void AnhXa(View view){
        rcv_confirmBill = view.findViewById(R.id.rcv_confirmBill);
    }
}
