package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.api.request.customer.CustomerResponse;
import com.example.phonesaleapp.model.ProductCart;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ShoppingCartService {
    @GET("ShoppingCartDetail/GetCartItems/{customerId}")
    Call<List<ProductCart>> getCartProducts(@Path("customerId") String customerId);
    @GET("Customer/GetCustomerIDByEmail/{email}")
    Call<CustomerResponse> getCustomerIDByEmail(@Path("email") String email);
    @DELETE("ShoppingCartDetail/{shoppingCartId}/{productId}")
    Call<Void> deleteProductFromCart(@Path("shoppingCartId") String shoppingCartId, @Path("productId") String productId);
}
