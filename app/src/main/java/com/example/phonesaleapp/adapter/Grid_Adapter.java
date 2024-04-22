package com.example.phonesaleapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.phonesaleapp.R;

import java.util.ArrayList;
import java.util.List;

public class Grid_Adapter extends ArrayAdapter<String> {
    private ArrayList<String > arrayList;
    private int selectedItem=-1;

    private final Context context;

    public Grid_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList= objects;
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

        String t = String.valueOf(arrayList.get(position));
        txtT.setText(t);

        if (position == selectedItem) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue_t));
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        // Thiết lập sự kiện click cho itemView
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật chỉ mục của item được chọn
                selectedItem = position;
                // Thông báo cho adapter biết là dữ liệu đã thay đổi
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
