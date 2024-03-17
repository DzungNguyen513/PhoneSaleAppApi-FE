package com.example.phonesaleapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phonesaleapp.MainActivity;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.api.service.LoginService;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.request.login.LoginRequest;
import com.example.phonesaleapp.api.request.login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        tvRegister.setOnClickListener(v -> gotoRegisterActivity());
        btnLogin.setOnClickListener(v -> {
            String email = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            Login(email, password);
        });
    }

    private void Login(String email, String password) {
        LoginService loginService = RetrofitClient.getClient().create(LoginService.class);
        Call<LoginResponse> call = loginService.loginUser(new LoginRequest(email, password));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().success) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Đã xảy ra lỗi, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gotoRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void initViews() {
        edtUsername = findViewById(R.id.edt_userName);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_signin);
        tvRegister = findViewById(R.id.tv_register);
    }
}
