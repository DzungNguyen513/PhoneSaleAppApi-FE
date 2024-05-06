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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.MainActivity;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.product.ListProductAdapter;
import com.example.phonesaleapp.adapter.shoppingcart.ProductCartAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.customer.CustomerIdResponse;
import com.example.phonesaleapp.api.service.CustomerService;
import com.example.phonesaleapp.api.service.ProductService;
import com.example.phonesaleapp.api.service.ShoppingCartService;
import com.example.phonesaleapp.model.product.Product;
import com.example.phonesaleapp.model.shoppingcart.ProductCart;
import com.example.phonesaleapp.model.product.ProductImage;
import com.example.phonesaleapp.model.product.Product_Detail;
import com.example.phonesaleapp.view.home.ProductDetail_Activity;
import com.example.phonesaleapp.view.home.Event.ProductClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements ProductClickListener{
    CheckBox cb_allProductCart;
    TextView tv_totalCheck;
    Button btn_buy;
    ImageView img_Back, img_message;
    RecyclerView rvCartItems, rcv_productSuggest;
    RelativeLayout rl_cartEmpty;
    LinearLayout ln_shopping;
    ProductCartAdapter adapter;
    ListProductAdapter productAdapter;
    List<ProductCart> productList = new ArrayList<>();
    ArrayList<Product_Detail> arrayListProduct= new ArrayList<>();
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
            ArrayList<ProductCart> selectedItems = new ArrayList<>();
            for (ProductCart productCart: productList){
                if (productCart.isSelected()){
                    selectedItems.add(productCart);
                }
            }
            if (!selectedItems.isEmpty()) {
                String totalPayment = tv_totalCheck.getText().toString().trim();
                Intent intent = new Intent(getContext(), CheckoutActivity.class);
                intent.putExtra("totalPayment", totalPayment);
                intent.putExtra("selectedItems", selectedItems);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Vui lòng chọn sản phẩm !", Toast.LENGTH_SHORT).show();
            }
        });
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("email", customerEmail);
                startActivity(intent);
            }
        });
        img_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChatMessageActivity.class);
                intent.putExtra("email", customerEmail);
                startActivity(intent);
            }
        });
        productAdapter= new ListProductAdapter(getContext(), arrayListProduct, this);
        rcv_productSuggest.setAdapter(productAdapter);
        LoadProduct();
        return view;
    }
    private  void LoadProduct(){
        ProductService productService= RetrofitClient.getClient().create(ProductService.class);
        Call<List<Product>> callListProduct= productService.GetProducts();
        callListProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    List<Product> products= response.body();

                    for (Product pd: products){
                        Product_Detail productDetail= new Product_Detail(pd.getProductId(),pd.getProductName(), pd.getPrice(), "");

                        Call<List<ProductImage>> callListImage= productService.GetProductImages(pd.getProductId());
                        callListImage.enqueue(new Callback<List<ProductImage>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<ProductImage>> call, @NonNull Response<List<ProductImage>> response) {
                                if(response.isSuccessful() && response.body()!=null) {
                                    List<ProductImage> productImages = response.body();
                                    for (ProductImage pro : productImages) {
                                        if (pro.isPrimary()) {
                                            productDetail.imagePath= pro.getImagePath();
                                            arrayListProduct.add(productDetail);
                                            productAdapter.notifyDataSetChanged();
                                            break;
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call<List<ProductImage>> call, @NonNull Throwable t) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateTotalCart() {
        double total = 0;
        for (ProductCart product : productList) {
            if (product.isSelected()) {
                total += product.getDiscountedPrice() * product.getAmount();
            }
        }
        tv_totalCheck.setText(String.format("đ%,d.000", (int) total));
    }
    private void loadCustomerProducts() {
        CustomerService customerService = RetrofitClient.getClient().create(CustomerService.class);
        ShoppingCartService shoppingCartService = RetrofitClient.getClient().create(ShoppingCartService.class);
        Call<CustomerIdResponse> customerIdCall = customerService.getCustomerIDByEmail(customerEmail);
        customerIdCall.enqueue(new Callback<CustomerIdResponse>() {
            @Override
            public void onResponse(Call<CustomerIdResponse> call, Response<CustomerIdResponse> response) {
                if (response.isSuccessful()) {
                    CustomerIdResponse customerResponse = response.body();
                    String customerId = customerResponse.getCustomerId();
                    Call<List<ProductCart>> cartProductsCall = shoppingCartService.getCartProducts(customerId);
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
                                    ln_shopping.setVisibility(View.GONE);
                                } else {
                                    rl_cartEmpty.setVisibility(View.GONE);
                                    ln_shopping.setVisibility(View.VISIBLE);
                                }
                            } else {
                                rl_cartEmpty.setVisibility(View.VISIBLE);
                                ln_shopping.setVisibility(View.GONE);
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
            public void onFailure(Call<CustomerIdResponse> call, Throwable t) {
                Log.e("CartFragment", "Lỗi: ", t);
                Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
                rl_cartEmpty.setVisibility(View.VISIBLE);
                ln_shopping.setVisibility(View.GONE);
            }
        });
    }

    private void AnhXa(View view){
        adapter = new ProductCartAdapter(getContext(), productList);
        rvCartItems = view.findViewById(R.id.rvCartItems);
        rvCartItems.setAdapter(adapter);
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_productSuggest = view.findViewById(R.id.rcv_productSuggest);
        rcv_productSuggest.setLayoutManager(new GridLayoutManager(getContext(), 2));
        cb_allProductCart = view.findViewById(R.id.cb_allProductCart);
        tv_totalCheck = view.findViewById(R.id.tv_TotalCheck);
        btn_buy = view.findViewById(R.id.btn_buy);
        rl_cartEmpty = view.findViewById(R.id.rl_cartEmpty);
        img_Back = view.findViewById(R.id.img_Back);
        ln_shopping = view.findViewById(R.id.ln_shopping);
        img_message = view.findViewById(R.id.img_message);
    }
    @Override
    public void onClickProduct(String productID) {
        Intent intent= new Intent(getContext(), ProductDetail_Activity.class);
        intent.putExtra("productId", productID);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String CategoryId) {

    }
}