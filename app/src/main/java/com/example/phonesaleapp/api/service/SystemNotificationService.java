package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.model.systemnotification.SystemNotification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SystemNotificationService {
    @GET("SystemNotification/GetNotification")
    Call<List<SystemNotification>> getSystemNotification(@Query("customerId") String customerId);
}
