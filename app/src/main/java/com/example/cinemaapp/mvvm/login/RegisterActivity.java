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
import android.widget.Toast;

import com.example.cinemaapp.MainActivity;
import com.example.cinemaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    EditText edt_email, edt_password;
    Button btn_signup;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công, Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Đăng kí thất bại",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    private void AnhXa(){
        edt_email = this.findViewById(R.id.edt_email);
        edt_password = this.findViewById(R.id.edt_password);
        btn_signup = this.findViewById(R.id.btn_signup);
        img_back = this.findViewById(R.id.img_back);
    }
}