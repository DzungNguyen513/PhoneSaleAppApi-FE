package com.example.phonesaleapp.view.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.MainActivity;
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
    CheckBox cb_allProductCart;
    TextView tv_totalCheck;
    Button btn_buy;
    ImageView img_Back;
    private RecyclerView rvCartItems;
    private RelativeLayout rl_cartEmpty;
    private LinearLayout ln_muaHang;
    private ProductCartAdapter adapter;
    private List<ProductCart> productList = new ArrayList<>();
    private static final String ARG_EMAIL = "email";
    private String customerEmail;

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
            customerEmail = getArguments().getString(ARG_EMAIL);
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        AnhXa(view);
        loadCustomerProducts();
        adapter.setOnProductCartChangeListener(() -> updateTotalCart());
        cb_allProductCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.selectAllItems(isChecked);
            }
        });
        btn_buy.setOnClickListener(v -> {
            double total = 0;
            for (ProductCart product : productList) {
                if (product.isSelected()) {
                    total += product.getPrice() * product.getAmount();
                }
            }
            BottomDialogCartFragment bottomSheetDialog = new BottomDialogCartFragment(total);
            bottomSheetDialog.show(getChildFragmentManager(), bottomSheetDialog.getTag());
        });
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("email", customerEmail);
                startActivity(intent);
            }
        });
        return view;
    }
    private void updateTotalCart() {
        double total = 0;
        for (ProductCart product : productList) {
            if (product.isSelected()) {
                total += product.getPrice() * product.getAmount();
            }
        }
        tv_totalCheck.setText(String.format("%,d VND", (int) total));
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
                                if (productList.isEmpty()) {
                                    rl_cartEmpty.setVisibility(View.VISIBLE);
                                    ln_muaHang.setVisibility(View.GONE);
                                } else {
                                    rl_cartEmpty.setVisibility(View.GONE);
                                    ln_muaHang.setVisibility(View.VISIBLE);
                                }
                            } else {
                                rl_cartEmpty.setVisibility(View.VISIBLE);
                                ln_muaHang.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ProductCart>> call, Throwable t) {
                            Toast.makeText(getContext(), "Lỗi Logic", Toast.LENGTH_SHORT).show();
                            rl_cartEmpty.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy CustomerID", Toast.LENGTH_SHORT).show();
                    rl_cartEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                Log.e("CartFragment", "Lỗi: ", t);
                Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
                rl_cartEmpty.setVisibility(View.VISIBLE);
                ln_muaHang.setVisibility(View.GONE);
            }
        });
    }

    private void AnhXa(View view){
        adapter = new ProductCartAdapter(getContext(), productList);
        rvCartItems = view.findViewById(R.id.rvCartItems);
        rvCartItems.setAdapter(adapter);
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        cb_allProductCart = view.findViewById(R.id.cb_allProductCart);
        tv_totalCheck = view.findViewById(R.id.tv_TotalCheck);
        btn_buy = view.findViewById(R.id.btn_buy);
        rl_cartEmpty = view.findViewById(R.id.rl_cartEmpty);
        img_Back = view.findViewById(R.id.img_Back);
        ln_muaHang = view.findViewById(R.id.ln_muaHang);
    }
}