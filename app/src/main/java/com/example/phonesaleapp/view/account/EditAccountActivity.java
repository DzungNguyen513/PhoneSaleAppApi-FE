package com.example.phonesaleapp.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.UserInfo;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.customer.CustomerIdResponse;
import com.example.phonesaleapp.model.customer.CustomerUpdateDTO;
import com.example.phonesaleapp.api.service.CustomerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccountActivity extends AppCompatActivity {
    ImageView img_back;
    EditText edt_customerName, edt_phoneNumber, edt_emailAddress, edt_address;
    RadioGroup rdGroup;
    RadioButton rdb_male, rdb_female, rdb_other;
    Button btn_saveAccount;
    String email = UserInfo.getInstance().getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        AnhXa();
        Intent intent = getIntent();
        String customerName = intent.getStringExtra("customerName");
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String emailAddress = intent.getStringExtra("emailAddress");
        String address = intent.getStringExtra("address");
        String gender = intent.getStringExtra("gender");
        if (customerName.equals("Trống")){
            edt_customerName.setText("");
        } else {
            edt_customerName.setText(customerName);
        }
        if (phoneNumber.equals("Trống")){
            edt_phoneNumber.setText("");
        } else {
            edt_phoneNumber.setText(phoneNumber);
        }
        if (emailAddress.equals("Trống")){
            edt_emailAddress.setText("");
        } else {
            edt_emailAddress.setText(emailAddress);
        }
        if (address.equals("Trống")){
            edt_address.setText("");
        } else {
            edt_address.setText(address);
        }
        if(gender.equals("Anh")){
            rdb_male.setChecked(true);
        } else if(gender.equals("Chị")){
            rdb_female.setChecked(true);
        } else if(gender.equals("Khác")){
            rdb_other.setChecked(true);
        } else if(gender.equals("Trống")){

        }
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAccountActivity.this, AccountInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_saveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName = edt_customerName.getText().toString();
                String phoneNumber = edt_phoneNumber.getText().toString();
                String emailAddress = edt_emailAddress.getText().toString();
                String address = edt_address.getText().toString();
                int gender = getSelectedGender();
                updateCustomer(customerName, phoneNumber, emailAddress, address, gender);
            }
        });
    }
    private int getSelectedGender() {
        int selectedId = rdGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.rdb_male){
            return 0;
        } else if (selectedId == R.id.rdb_female){
            return 1;
        } else if (selectedId == R.id.rdb_other){
            return 2;
        } else{
            return 3;
        }
    }
    private void updateCustomer(String customerName, String phoneNumber, String emailAddress, String address, int gender) {
        CustomerService service = RetrofitClient.getClient().create(CustomerService.class);
        service.getCustomerIDByEmail(email).enqueue(new Callback<CustomerIdResponse>() {
            @Override
            public void onResponse(Call<CustomerIdResponse> call, Response<CustomerIdResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String customerId = response.body().getCustomerId();
                    CustomerUpdateDTO customerUpdateDTO = new CustomerUpdateDTO(customerName, phoneNumber, emailAddress, address, gender);
                    updateCustomerInfo(customerId, customerUpdateDTO);
                } else {
                    Toast.makeText(EditAccountActivity.this, "Lỗi không thể lấy ID khách hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerIdResponse> call, Throwable t) {
                Toast.makeText(EditAccountActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCustomerInfo(String customerId, CustomerUpdateDTO customerUpdateDTO) {
        CustomerService service = RetrofitClient.getClient().create(CustomerService.class);
        service.updateCustomer(customerId, customerUpdateDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditAccountActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditAccountActivity.this, AccountInfoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EditAccountActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditAccountActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void AnhXa(){
        img_back = this.findViewById(R.id.img_back);
        edt_customerName = this.findViewById(R.id.edt_customerName);
        edt_phoneNumber = this.findViewById(R.id.edt_phoneNumber);
        edt_emailAddress = this.findViewById(R.id.edt_emailAddress);
        edt_address = this.findViewById(R.id.edt_address);
        rdGroup = this.findViewById(R.id.rdGroup);
        rdb_male = this.findViewById(R.id.rdb_male);
        rdb_female = this.findViewById(R.id.rdb_female);
        rdb_other = this.findViewById(R.id.rdb_other);
        btn_saveAccount = this.findViewById(R.id.btn_saveAccount);
    }
}
