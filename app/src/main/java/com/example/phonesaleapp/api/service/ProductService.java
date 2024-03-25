package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Path;

import retrofit2.http.GET;

public interface ProductService {
    @GET("Product/GetProducts")
    Call<List<Product>> GetProducts();


}
