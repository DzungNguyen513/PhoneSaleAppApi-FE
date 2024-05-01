package com.example.phonesaleapp.adapter.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.product.ProductImage;

import java.util.List;

public class ListProductImagesAdapter extends PagerAdapter {

    private Context context;
    private List<ProductImage> images;

    public ListProductImagesAdapter(Context context, List<ProductImage> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_image, container,false);
        ImageView imageProduct= itemView.findViewById(R.id.imageViewProduct_Detail);
        ProductImage productImage= images.get(position);
        String baseUrl = RetrofitClient.getBaseUrl();
        String imageUrl = baseUrl.replace("/api/", "/Assets/Images/") +
                productImage.getProductId() +"/"+productImage.getImagePath();
        Glide.with(context).load(imageUrl).into(imageProduct);
        container.addView(itemView);
        return itemView;
    }
}
