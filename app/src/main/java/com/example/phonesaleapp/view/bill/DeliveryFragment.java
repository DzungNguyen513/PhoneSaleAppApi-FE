package com.example.phonesaleapp.view.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.UserInfo;
import com.example.phonesaleapp.adapter.bill.BillItemAdapter;
import com.example.phonesaleapp.adapter.bill.BillStatus;
import com.example.phonesaleapp.adapter.product.ListProductAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.ProductService;
import com.example.phonesaleapp.model.customer.CustomerIdResponse;
import com.example.phonesaleapp.api.service.BillService;
import com.example.phonesaleapp.api.service.CustomerService;
import com.example.phonesaleapp.model.bill.Bill;
import com.example.phonesaleapp.model.product.Product;
import com.example.phonesaleapp.model.product.ProductImage;
import com.example.phonesaleapp.model.product.Product_Detail;
import com.example.phonesaleapp.view.home.Event.ProductClickListener;
import com.example.phonesaleapp.view.home.ProductDetail_Activity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryFragment extends Fragment implements ProductClickListener {
    LinearLayout ln_notBill;
    RecyclerView rcv_deliveryBill, rcv_productSuggest;
    BillItemAdapter adapter;
    List<Bill> lstBill = new ArrayList<>();
    ListProductAdapter productAdapter;
    ArrayList<Product_Detail> arrayListProduct= new ArrayList<>();
    String email = UserInfo.getInstance().getEmail();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);
        AnhXa(view);
        loadBill();
        productAdapter= new ListProductAdapter(getContext(), arrayListProduct, this);
        rcv_productSuggest.setAdapter(productAdapter);
        loadProduct();
        return view;
    }
    private  void loadProduct(){
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
    private void loadBill() {
        CustomerService customerService = RetrofitClient.getClient().create(CustomerService.class);
        BillService billService = RetrofitClient.getClient().create(BillService.class);
        Call<CustomerIdResponse> customerIdCall = customerService.getCustomerIDByEmail(email);
        customerIdCall.enqueue(new Callback<CustomerIdResponse>() {
            @Override
            public void onResponse(Call<CustomerIdResponse> call, Response<CustomerIdResponse> response) {
                if(response.isSuccessful()){
                    CustomerIdResponse customerResponse = response.body();
                    String customerId = customerResponse.getCustomerId();
                    Call<List<Bill>> billCall = billService.getBillOfCustomerID(customerId, BillStatus.ChoGiaoHang);
                    billCall.enqueue(new Callback<List<Bill>>() {
                        @Override
                        public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                            if (response.isSuccessful()) {
                                List<Bill> bills = response.body();
                                lstBill.clear();
                                lstBill.addAll(bills);
                                adapter.notifyDataSetChanged();
                                if (lstBill.isEmpty()) {
                                    ln_notBill.setVisibility(View.VISIBLE);
                                    rcv_deliveryBill.setVisibility(View.GONE);
                                } else {
                                    ln_notBill.setVisibility(View.GONE);
                                    rcv_deliveryBill.setVisibility(View.VISIBLE);
                                }
                            } else {
                                ln_notBill.setVisibility(View.VISIBLE);
                                rcv_deliveryBill.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Bill>> call, Throwable throwable) {
                            Toast.makeText(getContext(), "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            ln_notBill.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CustomerIdResponse> call, Throwable throwable) {
                ln_notBill.setVisibility(View.VISIBLE);
                rcv_deliveryBill.setVisibility(View.GONE);
            }
        });
    }
    private void AnhXa(View view){
        adapter = new BillItemAdapter(getContext(), lstBill);
        rcv_deliveryBill = view.findViewById(R.id.rcv_deliveryBill);
        rcv_deliveryBill.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_deliveryBill.setAdapter(adapter);
        ln_notBill = view.findViewById(R.id.ln_notBill);
        rcv_productSuggest = view.findViewById(R.id.rcv_productSuggest);
        rcv_productSuggest.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
    @Override
    public void onClickProduct(String productID) {
        Intent intent= new Intent(getContext(), ProductDetail_Activity.class);
        intent.putExtra("productId", productID);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}
