package com.example.phonesaleapp.adapter;

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
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.Product_Detail;
import com.example.phonesaleapp.view.home.Event.ProductClickListener;

import java.util.List;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {
    private final Context context;
    private final List<Product_Detail> productList;
    private ProductClickListener clickItemProduct;

    public ListProductAdapter(Context context, List<Product_Detail> productList, ProductClickListener clickItemProduct) {
        this.context = context;
        this.productList = productList;
        this.clickItemProduct=clickItemProduct;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product_Detail product = productList.get(position);
        holder.bind(product);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickItemProduct!=null){
                    clickItemProduct.onClickProduct(product.productId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameProduct;
        public TextView priceProduct;
        public ImageView imgProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.textViewProductName);
            priceProduct = itemView.findViewById(R.id.textViewProductPrice);
            imgProduct = itemView.findViewById(R.id.imageViewProduct);
        }

        public void bind(Product_Detail product) {
            nameProduct.setText(product.productName);
            priceProduct.setText(String.valueOf(product.price));
            String baseUrl = RetrofitClient.getBaseUrl();
            String imageUrl = baseUrl.replace("/api/", "/Assets/Images/") + product.productId + "/" + product.imagePath;
            Glide.with(context).load(imageUrl).into(imgProduct);

            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT; // Set the width to match parent
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT; // Set the height to wrap content
            itemView.setLayoutParams(layoutParams);
        }
    }
}
