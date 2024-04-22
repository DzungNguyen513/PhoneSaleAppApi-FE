package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.api.request.customer.ChangePassRequest;
import com.example.phonesaleapp.api.request.customer.CustomerByEmailDTO;
import com.example.phonesaleapp.api.request.customer.CustomerResponse;
import com.example.phonesaleapp.api.request.customer.CustomerUpdateDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerService {
    @GET("Customer/GetCustomerIDByEmail/{email}")
    Call<CustomerResponse> getCustomerIDByEmail(@Path("email") String email);
    @PUT("Customer/UpdateCustomer/{customerId}")
    Call<Void> updateCustomer(@Path("customerId") String customerId, @Body CustomerUpdateDTO request);
    @GET("Customer/GetCustomerByEmail/{email}")
    Call<CustomerByEmailDTO> getCustomerByEmail(@Path("email") String email);
    @POST("Customer/ChangePassword")
    Call<Void> changePassWord(@Body ChangePassRequest request);
}
