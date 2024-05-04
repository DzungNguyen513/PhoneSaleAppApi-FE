package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.model.register.RegisterRequest;
import com.example.phonesaleapp.model.register.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("Customer/Register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);
}
