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

        // Load products for the given email
        loadProductsForEmail(email);

        return view;
    }
    private void loadProductsForEmail(String email) {
        // Here, you would load products based on the email. This might involve querying your database or making a network request.
        // This is just a placeholder for your actual product loading logic.
        // Example:
        products.add(new Product("1", "Product Name", 100.0, 1, "Image URL"));
        adapter.notifyDataSetChanged();
    }
}
