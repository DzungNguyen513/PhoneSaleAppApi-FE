package com.example.phonesaleapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.ProductService;
import com.example.phonesaleapp.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductAdapter extends ArrayAdapter<Product> {
    public ListProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= convertView;
        if (view==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            view =inflater.inflate(R.layout.item_product,null);
        }
        Product product= getItem(position);
        if (product!=null){

            TextView priceProduct= view.findViewById(R.id.textViewProductPrice);
            ImageView imgProduct= view.findViewById(R.id.imageViewProduct);
            TextView nameProduct= view.findViewById(R.id.textViewProductName);
            nameProduct.setText(product.getProductName());
            priceProduct.setText(String.valueOf(product.getPrice()));
            String baseUrl = RetrofitClient.getBaseUrl();
            String imageUrl = baseUrl.replace("/api/", "/Assets/Images/") + product.getImg();
            Glide.with(getContext()).load(imageUrl).into(imgProduct);
        }

        return view;
    }
}
