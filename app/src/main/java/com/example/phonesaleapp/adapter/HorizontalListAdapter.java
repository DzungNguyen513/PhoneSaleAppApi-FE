package com.example.phonesaleapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder>{
    private String [] data;
    public HorizontalListAdapter(String[] data) {
        this.data = data;
    }
   public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType ){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,null);
        return new ViewHolder(view);
   }
    @Override
    public void onBindViewHolder(@NonNull HorizontalListAdapter.ViewHolder holder, int position) {
        holder.textViewCat.setText(data[position]);
        holder.imageViewCat.setImageResource(R.drawable.ip14);
    }
    @Override
    public int getItemCount() {
        return data.length;
    }
    public static  class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewCat;
        public TextView textViewCat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCat= itemView.findViewById(R.id.imageViewCategory);
            textViewCat=itemView.findViewById(R.id.textViewCategory);
        }
    }
}
