package com.example.phonesaleapp.view.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.register.RegisterRequest;
import com.example.phonesaleapp.model.register.RegisterResponse;
import com.example.phonesaleapp.api.service.RegisterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edt_email, edt_password;
    Button btn_signup;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();

        img_back.setOnClickListener(v -> {
            finish();
        });

        btn_signup.setOnClickListener(v -> {
            String email = edt_email.getText().toString().trim();
            String password = edt_password.getText().toString().trim();

            RegisterRequest registerRequest = new RegisterRequest(email, password);
            RegisterService service = RetrofitClient.getClient().create(RegisterService.class);
            Call<RegisterResponse> call = service.registerUser(registerRequest);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        boolean success = response.body().isSuccess();
                        if (success) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công, Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Đã xảy ra lỗi, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void AnhXa() {
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_signup = findViewById(R.id.btn_signup);
        img_back = findViewById(R.id.img_back);
    }
}
