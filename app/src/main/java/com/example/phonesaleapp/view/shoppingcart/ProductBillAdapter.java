package com.example.phonesaleapp.view.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.CheckoutProductAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.ProductCart;

import java.util.List;

public class ProductBillAdapter extends RecyclerView.Adapter<ProductBillAdapter.ViewHolder> {
    private Context context;
    private List<ProductCart> lstProductBill;
    public ProductBillAdapter(Context context, List<ProductCart> list){
        this.context = context;
        this.lstProductBill = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductBillAdapter.ViewHolder holder, int position) {
        ProductCart product = lstProductBill.get(position);
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
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ProductCheckoutName, tv_ProductCheckoutColor, tv_ProductCartStorage, tv_ProductCheckoutPrice, tv_itemCount;
        ImageView img_ProductCheckout;

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
