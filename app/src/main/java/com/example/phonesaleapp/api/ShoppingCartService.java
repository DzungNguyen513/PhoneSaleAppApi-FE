package com.example.phonesaleapp.api;

import com.example.phonesaleapp.model.Product;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ShoppingCartService {
    @GET("ShoppingCart/Details/{customerId}")
    Call<List<Product>> getCartProducts(@Path("customerId") String customerId);
    @GET("Customer/GetCustomerIDByEmail/{email}")
    Call<String> getCustomerIDByEmail(@Path("email") String email);
}
