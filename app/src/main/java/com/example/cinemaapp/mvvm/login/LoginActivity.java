package com.example.cinemaapp.mvvm.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemaapp.MainActivity;
import com.example.cinemaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    EditText edt_username;
    EditText edt_password;
    Button btn_login;
    TextView tv_register;
    ImageView img_facebook, img_linkedkin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        tv_register.setOnClickListener(v -> gotoRegisterActivity());
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edt_username.getText().toString().trim();
                String passWord = edt_password.getText().toString().trim();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(userName, passWord)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Sai tên tài khoản hoặc mật khẩu",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    private void gotoRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    private void AnhXa(){
        edt_username = this.findViewById(R.id.edt_userName);
        edt_password = this.findViewById(R.id.edt_password);
        btn_login = this.findViewById(R.id.btn_signin);
        tv_register = this.findViewById(R.id.tv_register);
        img_facebook = this.findViewById(R.id.login_facebook);
        img_linkedkin = this.findViewById(R.id.login_linkedkin);
    }
}