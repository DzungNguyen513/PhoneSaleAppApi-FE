package com.example.phonesaleapp.view.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.UserInfo;
import com.example.phonesaleapp.adapter.shoppingcart.CheckoutProductAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.request.bill.BillDTO;
import com.example.phonesaleapp.api.request.bill.BillDetailDTO;
import com.example.phonesaleapp.api.request.bill.BillResponse;
import com.example.phonesaleapp.api.request.customer.CustomerResponse;
import com.example.phonesaleapp.api.service.BillService;
import com.example.phonesaleapp.api.service.CustomerService;
import com.example.phonesaleapp.model.ProductCart;
import com.example.phonesaleapp.view.bill.BillActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    ImageView img_back;
    Button btn_checkOut;
    TextView tv_TotalCheck, tv_TotalCheck1, tv_TotalCheck2;
    EditText edt_customerName, edt_deliveryAddress, edt_phoneNumber, edt_note;
    RecyclerView rvCheckoutItems;
    CheckoutProductAdapter adapter;
    List<ProductCart> checkoutItems;
    String email = UserInfo.getInstance().getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        AnhXa();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        checkoutItems = (List<ProductCart>) getIntent().getSerializableExtra("selectedItems");
        adapter = new CheckoutProductAdapter(this,checkoutItems);
        rvCheckoutItems.setAdapter(adapter);
        String totalPayment = getIntent().getStringExtra("totalPayment");
        tv_TotalCheck.setText(totalPayment);
        tv_TotalCheck1.setText(totalPayment);
        tv_TotalCheck2.setText(totalPayment);
        btn_checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName = edt_customerName.getText().toString().trim();
                String deliveryAddress = edt_deliveryAddress.getText().toString().trim();
                String phoneNumber = edt_phoneNumber.getText().toString().trim();
                String note = edt_note.getText().toString().trim();
                if (customerName.equals("") || deliveryAddress.equals("") || phoneNumber.equals("") || note.equals("")){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin thanh toán", Toast.LENGTH_LONG).show();
                    return;
                }
                BottomDialogCheckout bottomDialog = new BottomDialogCheckout(totalPayment, customerName, deliveryAddress, phoneNumber, note);
                bottomDialog.show(getSupportFragmentManager(), "BottomDialogCheckout");
                bottomDialog.setOnConfirmListener(new BottomDialogCheckout.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        createBill(customerName,deliveryAddress, phoneNumber, note);
                    }
                });
            }
        });
    }
    private void createBill(String customerName, String deliveryAddress, String phoneNumber, String note){
        List<BillDetailDTO> billDetails = new ArrayList<>();
        for (ProductCart product : checkoutItems){
            BillDetailDTO detail = new BillDetailDTO();
            detail.setProductID(product.getProductID());
            detail.setColorName(product.getColorName());
            detail.setStorageGB(product.getStorageGB());
            detail.setPrice(product.getDiscountedPrice());
            detail.setAmount(product.getAmount());
            billDetails.add(detail);
        }
        BillDTO bill = new BillDTO();
        bill.setCustomerName(customerName);
        bill.setDeliveryAddress(deliveryAddress);
        bill.setCustomerPhone(phoneNumber);
        bill.setNote(note);
        bill.setBillDetails(billDetails);
        CustomerService customerService = RetrofitClient.getClient().create(CustomerService.class);
        Call<CustomerResponse> customerIdCall = customerService.getCustomerIDByEmail(email);
        customerIdCall.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if(response.isSuccessful()){
                    CustomerResponse customerResponse = response.body();
                    String customerId = customerResponse.getCustomerId();
                    bill.setCustomerId(customerId);

                    BillService service = RetrofitClient.getClient().create(BillService.class);
                    Call<BillResponse> billCall = service.createBill(bill);
                    billCall.enqueue(new Callback<BillResponse>() {
                        @Override
                        public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(CheckoutActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(CheckoutActivity.this, BillActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Tạo đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<BillResponse> call, Throwable throwable) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable throwable) {

            }
        });
    }
    private void AnhXa(){
        img_back = this.findViewById(R.id.img_back);
        rvCheckoutItems = this.findViewById(R.id.rvCheckoutItems);
        rvCheckoutItems.setLayoutManager(new LinearLayoutManager(this));
        btn_checkOut = this.findViewById(R.id.btn_checkOut);
        tv_TotalCheck = this.findViewById(R.id.tv_TotalCheck);
        tv_TotalCheck1 = this.findViewById(R.id.tv_TotalCheck1);
        tv_TotalCheck2 = this.findViewById(R.id.tv_TotalCheck2);
        edt_customerName = this.findViewById(R.id.edt_customerName);
        edt_deliveryAddress = this.findViewById(R.id.edt_deliveryAddress);
        edt_phoneNumber = this.findViewById(R.id.edt_phoneNumber);
        edt_note = this.findViewById(R.id.edt_note);
    }
}