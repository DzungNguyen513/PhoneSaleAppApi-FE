package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("Categories/GetCategories")
    Call<List<Category>> GetCategories();
}
