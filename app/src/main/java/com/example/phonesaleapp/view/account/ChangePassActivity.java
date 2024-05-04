
package com.example.phonesaleapp.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phonesaleapp.MainActivity;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.UserInfo;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.model.customer.ChangePassDTO;
import com.example.phonesaleapp.api.service.CustomerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassActivity extends AppCompatActivity {
    EditText edt_username, edt_oldPassword, edt_newPassword, edt_confirmNewPassword;
    Button btn_updatePass;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        AnhXa();
        String username = UserInfo.getInstance().getEmail().toString();
        edt_username.setText(username);
        btn_updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edt_oldPassword.getText().toString().trim();
                String newPass = edt_newPassword.getText().toString().trim();
                String confirmNewPass = edt_confirmNewPassword.getText().toString().trim();
                ChangePassDTO changePassDTO = new ChangePassDTO(username, oldPass, newPass, confirmNewPass);
                CustomerService customerService = RetrofitClient.getClient().create(CustomerService.class);
                customerService.changePassWord(changePassDTO).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                String errorMessage = response.errorBody().string();
                                JSONObject errorJson = new JSONObject(errorMessage);
                                String message = errorJson.getString("message");
                                Toast.makeText(ChangePassActivity.this, message, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {

                    }
                });
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void AnhXa(){
        edt_username = this.findViewById(R.id.edt_username);
        edt_oldPassword = this.findViewById(R.id.edt_oldPassword);
        edt_newPassword = this.findViewById(R.id.edt_newPassword);
        edt_confirmNewPassword = this.findViewById(R.id.edt_confirmNewPassword);
        btn_updatePass = this.findViewById(R.id.btn_updatePass);
        img_back = this.findViewById(R.id.img_back);
    }
}