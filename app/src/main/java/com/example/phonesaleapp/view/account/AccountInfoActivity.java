package com.example.phonesaleapp.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonesaleapp.MainActivity;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.UserInfo;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.request.customer.CustomerByEmailDTO;
import com.example.phonesaleapp.api.request.customer.CustomerUpdateDTO;
import com.example.phonesaleapp.api.service.CustomerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountInfoActivity extends AppCompatActivity {
    ImageView img_back, img_setting;
    TextView tv_customerName1, tv_customerName, tv_phoneNumber, tv_emailAddress,tv_address, tv_gender;
    String email = UserInfo.getInstance().getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        AnhXa();
        loadCustomerByEmail(email);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountInfoActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName = tv_customerName.getText().toString();
                String phoneNumber = tv_phoneNumber.getText().toString();
                String emailAddress = tv_emailAddress.getText().toString();
                String address = tv_address.getText().toString();
                String gender = tv_gender.getText().toString();
                Intent intent = new Intent(AccountInfoActivity.this, EditAccountActivity.class);
                intent.putExtra("customerName", customerName);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("emailAddress", emailAddress);
                intent.putExtra("address", address);
                intent.putExtra("gender", gender);
                startActivity(intent);
            }
        });
    }

    private void loadCustomerByEmail(String email) {
        tv_emailAddress.setText(email);
        CustomerService service = RetrofitClient.getClient().create(CustomerService.class);
        service.getCustomerByEmail(email).enqueue(new Callback<CustomerByEmailDTO>() {
            @Override
            public void onResponse(Call<CustomerByEmailDTO> call, Response<CustomerByEmailDTO> response) {
                if (response.isSuccessful()){
                    CustomerByEmailDTO customerByEmailDTO = response.body();
                    tv_customerName1.setText(customerByEmailDTO.getCustomerName());
                    tv_customerName.setText(customerByEmailDTO.getCustomerName());
                    tv_phoneNumber.setText(customerByEmailDTO.getPhoneNumber());
                    tv_address.setText(customerByEmailDTO.getAddress());
                    Integer gender = customerByEmailDTO.getGender();
                    if (gender == null){
                        tv_gender.setText("Trống");
                    } else if (gender == 0){
                        tv_gender.setText("Anh");
                    } else if (gender == 1){
                        tv_gender.setText("Chị");
                    } else if (gender == 2){
                        tv_gender.setText("Khác");
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerByEmailDTO> call, Throwable t) {

            }
        });
    }
    private void AnhXa(){
        img_back = this.findViewById(R.id.img_back);
        img_setting = this.findViewById(R.id.img_setting);
        tv_customerName = this.findViewById(R.id.tv_customerName);
        tv_customerName1 = this.findViewById(R.id.tv_customerName1);
        tv_phoneNumber = this.findViewById(R.id.tv_phoneNumber);
        tv_emailAddress = this.findViewById(R.id.tv_emailAddress);
        tv_address = this.findViewById(R.id.tv_address);
        tv_gender = this.findViewById(R.id.tv_gender);
    }
}