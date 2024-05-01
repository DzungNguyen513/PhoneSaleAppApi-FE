package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.model.customer.ChangePassDTO;
import com.example.phonesaleapp.model.customer.CustomerByEmailDTO;
import com.example.phonesaleapp.model.customer.CustomerIdResponse;
import com.example.phonesaleapp.model.customer.CustomerUpdateDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerService {
    @GET("Customer/GetCustomerIDByEmail/{email}")
    Call<CustomerIdResponse> getCustomerIDByEmail(@Path("email") String email);
    @PUT("Customer/UpdateCustomer/{customerId}")
    Call<Void> updateCustomer(@Path("customerId") String customerId, @Body CustomerUpdateDTO request);
    @GET("Customer/GetCustomerByEmail/{email}")
    Call<CustomerByEmailDTO> getCustomerByEmail(@Path("email") String email);
    @POST("Customer/ChangePassword")
    Call<Void> changePassWord(@Body ChangePassDTO request);
}
