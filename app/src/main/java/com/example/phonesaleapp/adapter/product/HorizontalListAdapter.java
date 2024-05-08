package com.example.phonesaleapp.adapter.product;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.model.category.Category;
import com.example.phonesaleapp.view.home.Event.CatClickItemListener;
import com.example.phonesaleapp.view.home.Event.ProductClickListener;

import java.util.ArrayList;
import java.util.Collections;

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder>{
    private ArrayList<Category> data;
    private Context context;

    private ProductClickListener catClickItemListener;
    private int selectedItemList=-1;

    public HorizontalListAdapter(ArrayList<Category> data, ProductClickListener catClickItemListener) {
        this.data = data;
        this.catClickItemListener = catClickItemListener;

    }

    public HorizontalListAdapter(ArrayList<Category> data, Context context, ProductClickListener catClickItemListener) {
        this.data = data;
        this.context = context;
        this.catClickItemListener = catClickItemListener;
    }

    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType ){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,null);
       RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
               RecyclerView.LayoutParams.WRAP_CONTENT,
               RecyclerView.LayoutParams.WRAP_CONTENT
       );
       layoutParams.setMargins(5, 2, 5,2); // Khoảng cách 2dp giữa các item
       itemView.setLayoutParams(layoutParams);
       return new ViewHolder(itemView);
   }
    @Override
    public void onBindViewHolder(@NonNull HorizontalListAdapter.ViewHolder holder, int position) {
        Category cat= data.get(position);
        holder.textViewCat.setText(cat.getCategoryName());

        if (position == selectedItemList) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue_t));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (catClickItemListener!=null){

                        catClickItemListener.onItemClick(cat.getCategoryId());

                    selectedItemList=position;
                    // Cập nhật lại giao diện để hiển thị màu nền mới
                    notifyDataSetChanged();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }


    public static  class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewCat;
        public TextView textViewCat; 
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCat=itemView.findViewById(R.id.textViewCategory);
        }
    }

}
