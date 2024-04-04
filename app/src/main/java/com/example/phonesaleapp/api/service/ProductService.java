package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.model.Product;
import com.example.phonesaleapp.model.ProductImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Path;

import retrofit2.http.GET;

public interface ProductService {
    @GET("Product/GetProducts")
    Call<List<Product>> GetProducts();
    @GET("Product/GetProductImages/{productId}")
    Call<List<ProductImage>> GetProductImages(@Path("productId") String productId);


}
