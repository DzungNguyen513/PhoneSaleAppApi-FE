package com.example.phonesaleapp.view.home;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.Grid_Adapter;
import com.example.phonesaleapp.adapter.ListProductImagesAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.ProductService;
import com.example.phonesaleapp.model.Product;
import com.example.phonesaleapp.model.ProductDetail;
import com.example.phonesaleapp.model.ProductImage;
import com.example.phonesaleapp.model.Product_Detail;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
    TextView txtAmountProductDetail, txtPriceProduct;

    Button btnAddToCart, btnOrderNow;
    ImageView imgProduct_Dt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity);
        Init();
        Intent intent= getIntent();
        String productId= intent.getStringExtra("productId");
        Load_Product_Detail(productId);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowOptionProduct(productId);
            }
        });


    }
String strColor=" ", strStorage=" ";
int amount=0;
    private  void Load_Product_Detail( String productId){

        // Load product image
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

        // Load i product detail
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

        // Load describe product
        Call<Product> callProduct= productService.GetProduct(productId);
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
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
    double colorprice, storageprice, price_c=0;
    Grid_Adapter gridAdapterColor, gridAdapterStorage;
    GridView gridViewColor, gridViewStorage;
    ArrayList<String > arrayListColor= new ArrayList<>();
    ArrayList<String > arrayListStorage= new ArrayList<>();
    String color;
    String prdID;
    private void ShowOptionProduct(String productID){
        prdID=productID;
        // Use BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProductDetail_Activity.this);
        View view = getLayoutInflater().inflate(R.layout.bottomdialog_buypoduct, null);
        bottomSheetDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 1500;
        view.setLayoutParams(layoutParams);
        //ánh xạ
        txtAmountProductDetail= view.findViewById(R.id.textViewAmount_PRDD);
        txtPriceProduct= view.findViewById(R.id.TextViewPrice_PRDD);
        imgProduct_Dt= view.findViewById(R.id.imageViewProduct2);
        gridViewColor= view.findViewById(R.id.gridViewSelectColor);
        gridViewStorage= view.findViewById(R.id.gridViewSelectStorage);
        // lời gọi dịch vụ
        ProductService productService= RetrofitClient.getClient().create(ProductService.class);
        // đưa ra thông tin
        Call<Product> callProduct= productService.GetProduct(productID);
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Product product = (Product) response.body();


                    // Load image chung

                    Call<List<ProductImage>> callListImage= productService.GetProductImages(productID);
                    callListImage.enqueue(new Callback<List<ProductImage>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<ProductImage>> call, @NonNull Response<List<ProductImage>> response) {
                            if(response.isSuccessful() && response.body()!=null) {
                                List<ProductImage> productImages = response.body();
                                for (ProductImage pro : productImages) {
                                    if (pro.isPrimary()) {
                                        String baseUrl = RetrofitClient.getBaseUrl();
                                        String imageUrl = baseUrl.replace("/api/", "/Assets/Images/") +
                                                productID +"/"+pro.getImagePath();
                                        Glide.with(ProductDetail_Activity.this).load(imageUrl).into(imgProduct_Dt);
                                        break;
                                    }
                                }

                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<ProductImage>> call, @NonNull Throwable t) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
        // Load color and storage
        Call<List<ProductDetail>> callPrdDetail= productService.GetProductDetails(productID);
        callPrdDetail.enqueue(new Callback<List<ProductDetail>>() {
            @Override
            public void onResponse(Call<List<ProductDetail>> call, Response<List<ProductDetail>> response) {
                if (response.isSuccessful() && response.body() !=null){
                    List<ProductDetail> product=  response.body();
                    for (ProductDetail prd: product){
                        if (!arrayListColor.contains(prd.getColorName())){
                            arrayListColor.add(prd.getColorName());
                        }
                        if (!arrayListStorage.contains(String.valueOf(prd.getStorageGb()))){
                            arrayListStorage.add(String.valueOf(prd.getStorageGb() +"GB"));
                        }
                    }
                    gridAdapterColor.notifyDataSetChanged();
                    gridAdapterStorage.notifyDataSetChanged();

                }
            }
            @Override
            public void onFailure(Call<List<ProductDetail>> call, Throwable t) {

            }
        });

        // gán view


        gridAdapterColor= new Grid_Adapter(this, R.layout.item_category, arrayListColor);
        gridViewColor.setAdapter(gridAdapterColor);

        gridAdapterStorage= new Grid_Adapter(this, R.layout.item_category, arrayListStorage);
        gridViewStorage.setAdapter(gridAdapterStorage);

        // event click on grid
        gridViewColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                color= arrayListColor.get(position);
                Toast.makeText(ProductDetail_Activity.this, color, Toast.LENGTH_SHORT).show();
//                String storageStr= arrayListStorage.get(position);
//                String storageNumberStr = storageStr.replaceAll("[^\\d.]", "");
//                Integer storage = Integer.parseInt(storageNumberStr);

                ProductService productService= RetrofitClient.getClient().create(ProductService.class);

                // get price by color and storage
                Call<Double> callprice= productService.calculateProductDetailPrice(prdID,color,null);
                callprice.enqueue(new Callback<Double>() {
                    @Override
                    public void onResponse(Call<Double> call, Response<Double> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            double totalprice= response.body();
                            txtPriceProduct.setText(String.valueOf(totalprice));
                            Toast.makeText(ProductDetail_Activity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Double> call, Throwable throwable) {

                    }
                });

                // Load image by color
                Call<List<ProductImage>> callListColor= productService.GetProductImages(prdID);
                callListColor.enqueue(new Callback<List<ProductImage>>() {
                    @Override
                    public void onResponse(Call<List<ProductImage>> call, Response<List<ProductImage>> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            List<ProductImage> productImages= response.body();
                            Log.d("Color_I", color);
                            for(ProductImage pr: productImages){
                                if (pr.getColorName()!=null && pr.getColorName().equals(color)){
                                    String baseUrl = RetrofitClient.getBaseUrl();
                                    String imageUrl = baseUrl.replace("/api/", "/Assets/Images/") +
                                            productID +"/"+pr.getImagePath();
                                    Log.d("Path_t", imageUrl);
                                    Glide.with(ProductDetail_Activity.this).load(imageUrl).into(imgProduct_Dt);
                                    break;
                                }
                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<List<ProductImage>> call, Throwable t) {

                    }

                });
            }
        });
        bottomSheetDialog.show();
    }


    private void Init() {
        viewPagerProductImage= findViewById(R.id.viewPagerListProductImage);
        tabLayout=  findViewById(R.id.tabLayoutProduct);
        txtProductName = findViewById(R.id.textViewProductDetail_Name);
        txtProductPrice= findViewById(R.id.textViewProductDetail_Price);
        txtDetailOfProduct= findViewById(R.id.textViewDetailOfProduct);
        btnAddToCart= findViewById(R.id.buttonAddtoCart);
        btnOrderNow= findViewById(R.id.buttonOrderNow);

    }
}
