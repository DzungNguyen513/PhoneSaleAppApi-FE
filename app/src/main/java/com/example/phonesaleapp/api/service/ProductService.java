package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.model.Product;
import com.example.phonesaleapp.model.ProductDetail;
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
    
    @GET("Product/GetProduct/{productId}")
    Call<Product> GetProduct(@Path("productId") String productId);


    @GET("Product/GetProductDetails/{productId}")
    Call<List<ProductDetail>> GetProductDetails(@Path("productId") String productId);

    @GET("Product/CalculateProductDetailPrice/{productId}/{colorName}/{storageGb}")
    Call<Double> CalculateProductDetailPrice(
            @Path("productId") String productId,
            @Path("colorName") String colorName,
            @Path("storageGb") Integer storageGb
    );



}
