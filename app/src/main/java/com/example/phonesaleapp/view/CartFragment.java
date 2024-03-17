package com.example.phonesaleapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.ProductCartAdapter;
import com.example.phonesaleapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private String URL = "http://192.168.1.7:7244/api/";
    private RecyclerView rvCartItems;
    private ProductCartAdapter adapter;
    private List<Product> products = new ArrayList<>();
    private static final String ARG_EMAIL = "email";
    private String email;

    public CartFragment() {

    }

    public static CartFragment newInstance(String email) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCartItems = view.findViewById(R.id.rvCartItems);
        adapter = new ProductCartAdapter(getContext(), products);
        rvCartItems.setAdapter(adapter);
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));

        loadProducts();
        return view;
    }
    private void loadProducts() {
        products.add(new Product("1", "Sản phẩm 1", 12, 1, "http://www.fixje.nl/iphone-14-pro-max-128gb-spacezwart/"));
        products.add(new Product("2", "Sản phẩm 2", 13, 1, "http://ibox.co.id/product/iphone-14-pro-max-ibox"));
        products.add(new Product("3", "Sản phẩm 3", 14, 1, "http://www.digitaltrends.com/mobile/iphone-se-2022-review/"));
        adapter.notifyDataSetChanged();
    }
}
