package com.example.phonesaleapp.view.shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.phonesaleapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDialogCartFragment extends BottomSheetDialogFragment {
    private TextView tv_total;
    private Button btn_ConfirmBuy;
    private double totalAmount;
    public BottomDialogCartFragment(double totalAmount){
        this.totalAmount = totalAmount;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomdialog_cart, container, false);

        tv_total = view.findViewById(R.id.tv_total);
        btn_ConfirmBuy = view.findViewById(R.id.btn_ConfirmBuy);

        tv_total.setText(String.format("Total: %,d VND", (int) totalAmount));

        btn_ConfirmBuy.setOnClickListener(v -> {
            dismiss();
        });

        return view;
    }
}
