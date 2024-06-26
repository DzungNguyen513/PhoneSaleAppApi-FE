package com.example.phonesaleapp.view.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.UserInfo;
import com.example.phonesaleapp.adapter.notification.SystemNotificationAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.CustomerService;
import com.example.phonesaleapp.api.service.SystemNotificationService;
import com.example.phonesaleapp.model.customer.CustomerIdResponse;
import com.example.phonesaleapp.model.systemnotification.SystemNotification;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotiFragment extends Fragment {
    ImageView img_readNoti;
    SystemNotificationAdapter adapter;
    List<SystemNotification> lstNoti = new ArrayList<>();
    ListView lv_lstNoti;
    String email = UserInfo.getInstance().getEmail();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noti, container, false);
        AnhXa(view);
        loadNoti();
        return view;
    }
    private void loadNoti(){
        CustomerService customerService = RetrofitClient.getClient().create(CustomerService.class);
        Call<CustomerIdResponse> customerIdResponseCall = customerService.getCustomerIDByEmail(email);
        customerIdResponseCall.enqueue(new Callback<CustomerIdResponse>() {
            @Override
            public void onResponse(Call<CustomerIdResponse> call, Response<CustomerIdResponse> response) {
                if (response.isSuccessful()){
                    String customerId = response.body().getCustomerId();
                    SystemNotificationService service = RetrofitClient.getClient().create(SystemNotificationService.class);
                    Call<List<SystemNotification>> callLstNoti = service.getSystemNotification(customerId);
                    callLstNoti.enqueue(new Callback<List<SystemNotification>>() {
                        @Override
                        public void onResponse(Call<List<SystemNotification>> call, Response<List<SystemNotification>> response) {
                            if (response.isSuccessful()){
                                List<SystemNotification> notis = response.body();
                                lstNoti.clear();
                                lstNoti.addAll(notis);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SystemNotification>> call, Throwable throwable) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CustomerIdResponse> call, Throwable throwable) {

            }
        });
        img_readNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View bottomSheetView  = getLayoutInflater().inflate(R.layout.bottomsheet_read_noti, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                bottomSheetDialog.setContentView(bottomSheetView );
                Button btn_confirmReadNoti = bottomSheetView .findViewById(R.id.btn_confirmReadNoti);
                btn_confirmReadNoti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.show();
            }
        });
    }
    private void AnhXa(View view){
        adapter = new SystemNotificationAdapter(getContext(), R.layout.item_notification, lstNoti);
        lv_lstNoti = view.findViewById(R.id.lv_lstNoti);
        lv_lstNoti.setAdapter(adapter);
        img_readNoti = view.findViewById(R.id.img_readNoti);
    }
}
