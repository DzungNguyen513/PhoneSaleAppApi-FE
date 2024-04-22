package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.api.request.bill.BillDTO;
import com.example.phonesaleapp.api.request.bill.BillResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BillService {
    @POST("Bill/CreateBill")
    Call<BillResponse> createBill(@Body BillDTO bill);
}
