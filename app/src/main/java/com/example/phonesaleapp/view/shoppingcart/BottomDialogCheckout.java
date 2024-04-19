package com.example.phonesaleapp.view.shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.phonesaleapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDialogCheckout extends BottomSheetDialogFragment {
    TextView tv_total;
    Button btn_ConfirmBuy;
    String totalBill;
    public BottomDialogCheckout(String totalBill){
        this.totalBill = totalBill;
    }
    public interface OnConfirmListener {
        void onConfirm();
    }

    private OnConfirmListener onConfirmListener;

    public void setOnConfirmListener(OnConfirmListener listener) {
        this.onConfirmListener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomdialog_checkout, container, false);

        tv_total = view.findViewById(R.id.tv_total);
        btn_ConfirmBuy = view.findViewById(R.id.btn_ConfirmBuy);

        tv_total.setText(String.format(totalBill));

        btn_ConfirmBuy.setOnClickListener(v -> {
            if (onConfirmListener != null) {
                onConfirmListener.onConfirm();
            }
            dismiss();
        });

        return view;
    }
}
