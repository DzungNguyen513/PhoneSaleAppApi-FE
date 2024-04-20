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
    TextView tv_total, tv_customerName, tv_deliveryAddress, tv_phoneNumber, tv_note;
    Button btn_ConfirmBuy;
    String totalBill, customerName, deliveryAddress, phoneNumber, note;
    public BottomDialogCheckout(String totalBill, String customerName, String deliveryAddress, String phoneNumber, String note) {
        this.totalBill = totalBill;
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.note = note;
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
        AnhXa(view);

        tv_total.setText(String.format(totalBill));
        tv_customerName.setText(customerName);
        tv_deliveryAddress.setText(deliveryAddress);
        tv_phoneNumber.setText(phoneNumber);
        tv_note.setText(note);

        btn_ConfirmBuy.setOnClickListener(v -> {
            if (onConfirmListener != null) {
                onConfirmListener.onConfirm();
            }
            dismiss();
        });

        return view;
    }
    private void AnhXa(View view){
        tv_total = view.findViewById(R.id.tv_total);
        btn_ConfirmBuy = view.findViewById(R.id.btn_ConfirmBuy);
        tv_customerName = view.findViewById(R.id.tv_customerName);
        tv_deliveryAddress = view.findViewById(R.id.tv_deliveryAddress);
        tv_phoneNumber = view.findViewById(R.id.tv_phoneNumber);
        tv_note = view.findViewById(R.id.tv_note);
    }
}
