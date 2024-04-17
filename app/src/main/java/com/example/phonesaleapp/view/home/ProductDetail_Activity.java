package com.example.phonesaleapp.view.home;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.ListProductImagesAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.ProductService;
import com.example.phonesaleapp.model.Product;
import com.example.phonesaleapp.model.ProductDetail;
import com.example.phonesaleapp.model.ProductImage;
import com.example.phonesaleapp.model.Product_Detail;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetail_Activity  extends AppCompatActivity {
    ArrayList<ProductImage> arrayListproductImages= new ArrayList<>();
    ListProductImagesAdapter listProductImagesAdapter;
    CircleTabLayout tabLayout;
    TextView txtProductName, txtProductPrice, txtDetailOfProduct, txtAmountProduct;
    ViewPager viewPagerProductImage;
    ArrayList<Integer> listStorage= new ArrayList<>();
    ArrayList<String> listColor= new ArrayList<>();
    MenuItem item_AddtoCart, item_BuyNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity);
        Init();
        Intent intent= getIntent();
        String productId= intent.getStringExtra("productId");
       Load_Product_Detail(productId);


    }
String strColor=" ", strStorage=" ";
int amount=0;
    private  void Load_Product_Detail( String productId){
        listProductImagesAdapter= new ListProductImagesAdapter(this, arrayListproductImages);
        viewPagerProductImage.setAdapter(listProductImagesAdapter);
        ProductService productService= RetrofitClient.getClient().create(ProductService.class);
        Call<List<ProductImage>> callListImage= productService.GetProductImages(productId);
        callListImage.enqueue(new Callback<List<ProductImage>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductImage>> call, Response<List<ProductImage>> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    List<ProductImage> productImages = (List<ProductImage>) response.body();
                    for (ProductImage prdI : productImages){
                        arrayListproductImages.add(prdI);
                        listProductImagesAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ProductImage>> call, Throwable t) {
            }
        });
        tabLayout.setupWithViewPager(viewPagerProductImage);

        Call<List<ProductDetail>> callProductDetail= productService.GetProductDetails(productId);
        callProductDetail.enqueue(new Callback<List<ProductDetail>>() {
            @Override
            public void onResponse(Call<List<ProductDetail>> call, Response<List<ProductDetail>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    List<ProductDetail> productDetails= response.body();
                    for (ProductDetail product : productDetails){
                        strStorage += product.getStorageGb() +" / ";
                        listStorage.add(product.getStorageGb());
                        listColor.add(product.getColorName());
                        strColor += " "+product.getColorName() +" /";
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductDetail>> call, Throwable t) {

            }
        });
        Call<Product> callProduct= productService.GetProduct(productId);
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Toast.makeText(ProductDetail_Activity.this, "Thành công", Toast.LENGTH_SHORT).show();
                if(response.isSuccessful() && response.body()!=null){
                    Product product= (Product) response.body();
                    String productName= product.getProductName() + strStorage.substring(0, strStorage.length()-1)
                            +strColor.substring(0, strColor.length()-1);
                    txtProductName.setText(productName);
                    txtProductPrice.setText(String.valueOf(product.getPrice()));
                    txtDetailOfProduct.setText(productName + " ."+ product.getDetail());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order, menu);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavOrder);
        Menu bottomMenu = bottomNavigationView.getMenu();
        MenuItem item_AddtoCart = bottomMenu.findItem(R.id.item_addToCart);
        MenuItem item_BuyNow = bottomMenu.findItem(R.id.item_BuyNow);

        SpannableString spannableString = new SpannableString("Thêm vào giỏ hàng");
        spannableString.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        item_AddtoCart.setTitle(spannableString);
        return true;

    }

    private void Init() {
        viewPagerProductImage= findViewById(R.id.viewPagerListProductImage);
        tabLayout=  findViewById(R.id.tabLayoutProduct);
        txtProductName = findViewById(R.id.textViewProductDetail_Name);
        txtProductPrice= findViewById(R.id.textViewProductDetail_Price);
        txtDetailOfProduct= findViewById(R.id.textViewDetailOfProduct);
//        txtAmountProduct= findViewById(R.id.textViewProductAmount);
    }
}
