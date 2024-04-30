package com.example.phonesaleapp.adapter.bill;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.ProductBill;

import java.util.List;

public class ProductBillAdapter extends RecyclerView.Adapter<ProductBillAdapter.ViewHolder> {
    private Context context;
    private List<ProductBill> lstProductBill;
    public ProductBillAdapter(Context context, List<ProductBill> lstProductBill){
        this.context = context;
        this.lstProductBill = lstProductBill;
    }

    @NonNull
    @Override
    public ProductBillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_bill,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductBillAdapter.ViewHolder holder, int position) {
        ProductBill product = lstProductBill.get(position);
        holder.tv_ProductBillName.setText(product.getProductName());
        holder.tv_ProductBillPrice.setText(String.format("đ%,d.000", product.getDiscountedPrice()));
        holder.tv_priceOrigin.setText(String.format("đ%,d.000", product.getOriginalPrice()));
        holder.tv_ProductBillColor.setText(product.getColorName());
        holder.tv_ProductBillStorage.setText(String.valueOf(product.getStorageGB()+ "GB"));
        holder.tv_itemCount.setText(String.valueOf("x"+product.getAmount()));
        String baseUrl = RetrofitClient.getBaseUrl();
        String imageUrl = baseUrl.replace("/api/", "/Assets/Images/"+product.getProductID()+"/") + product.getImg();
        Glide.with(context).load(imageUrl).into(holder.img_ProductBill);
    }

    @Override
    public int getItemCount() {
        return lstProductBill.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ProductBillName, tv_ProductBillColor, tv_ProductBillStorage, tv_ProductBillPrice,tv_priceOrigin, tv_itemCount;
        ImageView img_ProductBill;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ProductBillName = itemView.findViewById(R.id.tv_ProductBillName);
            tv_ProductBillColor = itemView.findViewById(R.id.tv_ProductBillColor);
            tv_ProductBillStorage = itemView.findViewById(R.id.tv_ProductBillStorage);
            tv_ProductBillPrice = itemView.findViewById(R.id.tv_ProductBillPrice);
            tv_priceOrigin = itemView.findViewById(R.id.tv_priceOrigin);
            tv_priceOrigin.setPaintFlags(tv_priceOrigin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv_itemCount = itemView.findViewById(R.id.tv_itemCount);
            img_ProductBill = itemView.findViewById(R.id.img_ProductBill);
        }
    }
}
