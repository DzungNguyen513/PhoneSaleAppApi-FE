package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.model.product.Product;
import com.example.phonesaleapp.model.product.ProductDetail;
import com.example.phonesaleapp.model.product.ProductImage;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {
    @GET("Product/GetProducts")
    Call<List<Product>> GetProducts();
    @GET("Product/GetProductImages/{productId}")
    Call<List<ProductImage>> GetProductImages(@Path("productId") String productId);
    
    @GET("Product/GetProduct/{productId}")
    Call<Product> GetProduct(@Path("productId") String productId);

    @GET("Product/GetProductDetails/{productId}")
    Call<List<ProductDetail>> GetProductDetails(@Path("productId") String productId);

    @GET("Product/CalculateProductDetailPrice/{productId}")
    Call<Integer> calculateProductDetailPrice(
            @Path("productId") String productId,
            @Query("colorName") String colorName,
            @Query("storageGb") Integer storageGb
    );

    @GET("Product/TotalAmountByProductId/{productId}")
    Call<Integer> TotalAmountByProductId(@Path("productId") String productId);

    @GET("Product/AmountByColorStorage/{productId}")
    Call<Integer> AmountByColorStorage(
            @Path("productId") String productId,
            @Query("colorName") String colorName,
            @Query("storageGb") Integer storageGb
    );


}
