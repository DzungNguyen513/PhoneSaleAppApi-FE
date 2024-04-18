package com.example.phonesaleapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.ProductCart;

import java.util.List;

public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.ViewHolder> {
    private Context context;
    private List<ProductCart> lstCheckoutProduct;
    public CheckoutProductAdapter(Context context, List<ProductCart> lstCheckoutProduct){
        this.context = context;
        this.lstCheckoutProduct = lstCheckoutProduct;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductCart product = lstCheckoutProduct.get(position);
        holder.tv_ProductCheckoutName.setText(product.getProductName());
        holder.tv_ProductCheckoutColor.setText(product.getColorName());
        holder.tv_ProductCartStorage.setText(product.getStorageGB() + "GB");
        holder.tv_ProductCheckoutPrice.setText(String.format("đ%,d.000", product.getDiscountedPrice()));
        holder.tv_itemCount.setText(String.valueOf(product.getAmount()));
        String baseUrl = RetrofitClient.getBaseUrl();
        String imageUrl = baseUrl.replace("/api/", "/Assets/Images/"+product.getProductID()+"/") + product.getImg();
        Glide.with(context).load(imageUrl).into(holder.img_ProductCheckout);
    }

    @Override
    public int getItemCount() {
        return lstCheckoutProduct.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_ProductCheckoutName, tv_ProductCheckoutColor, tv_ProductCartStorage, tv_ProductCheckoutPrice, tv_itemCount;
        public ImageView img_ProductCheckout;

        public ViewHolder(View itemView) {
            super(itemView);
            img_ProductCheckout = itemView.findViewById(R.id.img_ProductCheckout);
            tv_ProductCheckoutName = itemView.findViewById(R.id.tv_ProductCheckoutName);
            tv_ProductCheckoutColor = itemView.findViewById(R.id.tv_ProductCheckoutColor);
            tv_ProductCartStorage = itemView.findViewById(R.id.tv_ProductCartStorage);
            tv_ProductCheckoutPrice = itemView.findViewById(R.id.tv_ProductCheckoutPrice);
            tv_itemCount = itemView.findViewById(R.id.tv_itemCount);
        }
    }
}
