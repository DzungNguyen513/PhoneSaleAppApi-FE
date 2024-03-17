package com.example.phonesaleapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.model.Product;

import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ViewHolder> {
    private Context context;
    private List<Product> productList;

    public ProductCartAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvNameProduct.setText(product.getProductName());
        holder.tvPriceProduct.setText(String.format("%,d VND", product.getPrice()));
        holder.tvCount.setText(String.valueOf(product.getAmount()));
        Glide.with(context).load(product.getImageUrl()).into(holder.imgPhotoProduct);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBoxSelectItem;
        public ImageView imgPhotoProduct, imgMinus, imgPlus, imgRemove;
        public TextView tvNameProduct, tvPriceProduct, tvCount;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBoxSelectItem = itemView.findViewById(R.id.cb_productCart);
            imgPhotoProduct = itemView.findViewById(R.id.img_photo_cart);
            tvNameProduct = itemView.findViewById(R.id.tv_name_product_cart);
            tvPriceProduct = itemView.findViewById(R.id.tv_price_product_cart);
            imgMinus = itemView.findViewById(R.id.img_minus_cart);
            tvCount = itemView.findViewById(R.id.tv_count_cart);
            imgPlus = itemView.findViewById(R.id.img_plus_cart);
            imgRemove = itemView.findViewById(R.id.img_remove_cart);
        }
    }
}
