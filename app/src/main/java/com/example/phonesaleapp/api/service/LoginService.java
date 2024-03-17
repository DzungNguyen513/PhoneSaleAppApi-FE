package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.api.request.login.LoginRequest;
import com.example.phonesaleapp.api.request.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("Login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
