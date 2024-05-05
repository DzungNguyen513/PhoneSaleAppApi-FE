package com.example.phonesaleapp.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonesaleapp.MainActivity;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.api.service.LoginService;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.login.LoginRequest;
import com.example.phonesaleapp.model.login.LoginResponse;

import org.json.JSONObject;

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
        AnhXa();
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
                if (!response.isSuccessful()) {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        runOnUiThread(() -> {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("Thông báo");
                            builder.setMessage(errorMessage);
                            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Có lỗi xảy ra, vui lòng thử lại sau.", Toast.LENGTH_LONG).show());
                    }
                } else if (response.body() != null) {
                    boolean success = response.body().success;
                    if (success) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Kết nối mạng có vấn đề. Vui lòng kiểm tra và thử lại.", Toast.LENGTH_SHORT).show());
            }
        });

    }

    private void gotoRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void AnhXa() {
        edtUsername = findViewById(R.id.edt_userName);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_signin);
        tvRegister = findViewById(R.id.tv_register);
    }
}
