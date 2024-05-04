package com.example.phonesaleapp.view.bill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.bill.ProductBillAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.BillService;
import com.example.phonesaleapp.model.bill.BillSummaryDTO;
import com.example.phonesaleapp.model.bill.ProductBill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailActivity extends AppCompatActivity {
    ImageView img_Back;
    TextView tv_statusBill, tv_billId, tv_dateBill, tv_customerName, tv_phoneNumber, tv_deliveryAddress, tv_note, tv_totalBill, tv_tranferStatus;
    RecyclerView rcv_lstBillDetail;
    ProductBillAdapter adapter;
    List<ProductBill> lstProduct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        AnhXa();
        Intent intent = getIntent();
        String statusBill = intent.getStringExtra("statusBill");
        tv_statusBill.setText(statusBill);
        if(statusBill.equals("Chờ Xác Nhận")){
            tv_tranferStatus.setText("Chờ người gửi xác nhận đơn hàng");
        }else if(statusBill.equals("Chờ Thanh Toán")){
            tv_tranferStatus.setText("Người gửi đang chuẩn bị hàng");
        }else if(statusBill.equals("Chờ Giao Hàng")){
            tv_tranferStatus.setText("Đơn hàng đang trên đường đến bạn");
        }else if(statusBill.equals("Hoàn thành")){
            tv_tranferStatus.setText("Giao hàng thành công");
        }else if(statusBill.equals("Đã Hủy")){
            tv_tranferStatus.setText("Đơn hàng đã bị hủy");
        }
        tv_billId.setText(intent.getStringExtra("billId"));
        tv_dateBill.setText(intent.getStringExtra("dateBill"));
        tv_totalBill.setText(intent.getStringExtra("totalBill"));
        loadCustomer();
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadCustomer() {
        String billId = getIntent().getStringExtra("billId");
        BillService billService = RetrofitClient.getClient().create(BillService.class);
        Call<BillSummaryDTO> call = billService.getBillDetail(billId);
        call.enqueue(new Callback<BillSummaryDTO>() {
            @Override
            public void onResponse(Call<BillSummaryDTO> call, Response<BillSummaryDTO> response) {
                if (response.isSuccessful()){
                    BillSummaryDTO billSummary = response.body();
                    tv_customerName.setText(billSummary.getCustomerName());
                    tv_phoneNumber.setText(billSummary.getCustomerPhone());
                    tv_deliveryAddress.setText(billSummary.getDeliveryAddress());
                    tv_note.setText(billSummary.getNote());
                    List<ProductBill> productBills = billSummary.getLstProductBill();
                    lstProduct.clear();
                    lstProduct.addAll(productBills);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<BillSummaryDTO> call, Throwable throwable) {

            }
        });
    }

    private void AnhXa(){
        adapter = new ProductBillAdapter(this, lstProduct);
        rcv_lstBillDetail = this.findViewById(R.id.rcv_lstBillDetail);
        rcv_lstBillDetail.setAdapter(adapter);
        rcv_lstBillDetail.setLayoutManager(new LinearLayoutManager(this));
        img_Back = this.findViewById(R.id.img_Back);
        tv_statusBill = this.findViewById(R.id.tv_statusBill);
        tv_billId = this.findViewById(R.id.tv_billId);
        tv_dateBill = this.findViewById(R.id.tv_dateBill);
        tv_customerName = this.findViewById(R.id.tv_customerName);
        tv_phoneNumber = this.findViewById(R.id.tv_phoneNumber);
        tv_deliveryAddress = this.findViewById(R.id.tv_deliveryAddress);
        tv_note = this.findViewById(R.id.tv_note);
        tv_totalBill = this.findViewById(R.id.tv_totalBill);
        tv_tranferStatus = this.findViewById(R.id.tv_tranferStatus);
    }
}