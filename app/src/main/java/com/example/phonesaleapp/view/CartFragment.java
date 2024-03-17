package com.example.phonesaleapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.ProductCartAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.request.customer.CustomerResponse;
import com.example.phonesaleapp.api.service.ShoppingCartService;
import com.example.phonesaleapp.model.ProductCart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    private RecyclerView rvCartItems;
    private ProductCartAdapter adapter;
    private List<ProductCart> productList = new ArrayList<>();
    private String customerEmail = "trang";
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
        adapter = new ProductCartAdapter(getContext(), productList);
        rvCartItems.setAdapter(adapter);
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));

        loadCustomerProducts();
        return view;
    }
    private void loadCustomerProducts() {
        ShoppingCartService service = RetrofitClient.getClient().create(ShoppingCartService.class);
        Call<CustomerResponse> customerIdCall = service.getCustomerIDByEmail(customerEmail);
        customerIdCall.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.isSuccessful()) {
                    CustomerResponse customerResponse = response.body();
                    String customerId = customerResponse.getCustomerId();
                    Call<List<ProductCart>> cartProductsCall = service.getCartProducts(customerId);
                    cartProductsCall.enqueue(new Callback<List<ProductCart>>() {
                        @Override
                        public void onResponse(Call<List<ProductCart>> call, Response<List<ProductCart>> response) {
                            if (response.isSuccessful()) {
                                List<ProductCart> products = response.body();
                                productList.clear();
                                productList.addAll(products);
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "Không thể lấy sản phẩm từ giỏ hàng", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ProductCart>> call, Throwable t) {
                            Toast.makeText(getContext(), "Lỗi Logic", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy CustomerID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                Log.e("CartFragment", "Lỗi mạng: ", t);
                Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
