package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.api.request.bill.BillDTO;
import com.example.phonesaleapp.api.request.bill.BillResponse;
import com.example.phonesaleapp.model.Bill;
import com.example.phonesaleapp.adapter.bill.BillStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BillService {
    @POST("Bill/CreateBill")
    Call<BillResponse> createBill(@Body BillDTO bill);
    @GET("Bill/GetBillInfo/{customerId}")
    Call<List<Bill>> getBillOfCustomerID(@Path("customerId") String customerId, @Query("status") BillStatus status);
}
