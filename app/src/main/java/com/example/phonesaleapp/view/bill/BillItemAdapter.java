package com.example.phonesaleapp.view.bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.model.Bill;

import java.util.List;

public class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.ViewHolder> {
    private Context context;
    private List<Bill> lstBill;
    public BillItemAdapter(Context context, List<Bill> lstBill){
        this.context = context;
        this.lstBill = lstBill;
    }
    @NonNull
    @Override
    public BillItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillItemAdapter.ViewHolder holder, int position) {
        Bill bill = lstBill.get(position);
        holder.tv_statusBill.setText("Chờ xác nhận");
        holder.tv_dateBill.setText(bill.getDateBill());
        holder.tv_billId.setText(bill.getBillId());
        holder.tv_amountProduct.setText(String.format("x"+bill.getTotalProducts()));
        holder.tv_totalBill.setText(String.format("đ%,d.000",bill.getTotalBill()));
    }

    @Override
    public int getItemCount() {
        return lstBill.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_statusBill, tv_billId, tv_dateBill, tv_amountProduct, tv_totalBill;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_statusBill = itemView.findViewById(R.id.tv_statusBill);
            tv_billId = itemView.findViewById(R.id.tv_billId);
            tv_dateBill = itemView.findViewById(R.id.tv_dateBill);
            tv_amountProduct = itemView.findViewById(R.id.tv_amountProduct);
            tv_totalBill = itemView.findViewById(R.id.tv_totalBill);
        }
    }
}
