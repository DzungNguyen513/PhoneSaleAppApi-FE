package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.api.request.customer.CustomerByEmailDTO;
import com.example.phonesaleapp.api.request.customer.CustomerUpdateDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerService {
    @PUT("Customer/UpdateCustomer/{customerId}")
    Call<Void> updateCustomer(@Path("customerId") String customerId, @Body CustomerUpdateDTO request);
    @GET("Customer/GetCustomerByEmail/{email}")
    Call<CustomerByEmailDTO> getCustomerByEmail(@Path("email") String email);
}
