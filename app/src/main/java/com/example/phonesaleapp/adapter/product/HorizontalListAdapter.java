package com.example.phonesaleapp.adapter.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.model.category.Category;
import com.example.phonesaleapp.view.home.Event.CatClickItemListener;
import com.example.phonesaleapp.view.home.Event.ProductClickListener;

import java.util.ArrayList;

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder>{
    private ArrayList<Category> data;
    private ProductClickListener catClickItemListener;

    public HorizontalListAdapter(ArrayList<Category> data, ProductClickListener catClickItemListener) {
        this.data = data;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (catClickItemListener!=null){
                    catClickItemListener.onItemClick(cat.getCategoryId());
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
