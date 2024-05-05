package com.example.phonesaleapp.adapter.product;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.ProductService;
import com.example.phonesaleapp.model.product.ProductImage;
import com.example.phonesaleapp.view.home.ProductDetail_Activity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Grid_Adapter extends ArrayAdapter<String> {
    private ArrayList<String > arrayList;


    private final Context context;

    public Grid_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects ) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= convertView;
        TextView txtT;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        }
        txtT = view.findViewById(R.id.textViewCategory);
        ImageView imgProduct= view.findViewById(R.id.imageViewProduct2);
        String t = String.valueOf(arrayList.get(position));
        txtT.setText(t);


        return view;
    }


}
