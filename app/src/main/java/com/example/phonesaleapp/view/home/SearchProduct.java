package com.example.phonesaleapp.view.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.product.HorizontalListAdapter;
import com.example.phonesaleapp.adapter.product.ListProductAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.CategoryService;
import com.example.phonesaleapp.api.service.ProductService;
import com.example.phonesaleapp.model.category.Category;
import com.example.phonesaleapp.model.product.Product;
import com.example.phonesaleapp.model.product.ProductImage;
import com.example.phonesaleapp.model.product.Product_Detail;
import com.example.phonesaleapp.view.home.Event.ProductClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProduct extends AppCompatActivity implements ProductClickListener {
    TextView txtResult, txtWaring;
    RecyclerView recyclerViewProduct;
    RecyclerView recyclerViewCat;
    ArrayList<Product_Detail> arrayListProduct= new ArrayList<>();
    ListProductAdapter productAdapter;
    ImageView img_Back, img_filter, img_cacel_filter;
    String email;
    ArrayList<Category> arrayListCat= new ArrayList<>();
    HorizontalListAdapter adapterCat;
    String categoryID="";
    String strSearch="";
    EditText edtpriceMin, edtpriceMax;
    Button btnFilter_Product;
    int price_Min, price_Max;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_searchproduct);
        Init();

        Intent intent= getIntent();
        strSearch= intent.getStringExtra("search");
        email= intent.getStringExtra("email");
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter= new ListProductAdapter(this, arrayListProduct,this);
        recyclerViewProduct.setAdapter(productAdapter);
        Search_Product(strSearch);

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(SearchProduct.this);
                dialog.setContentView(R.layout.filter_product);

                // Thiết lập kích thước và vị trí của dialog
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.gravity = Gravity.RIGHT;

                // ánh xạ
                recyclerViewCat= dialog.findViewById(R.id.gridViewCat_Filter);
                edtpriceMin= dialog.findViewById(R.id.edtPriceMin);
                edtpriceMax= dialog.findViewById(R.id.edtPriceMax);
                txtWaring= dialog.findViewById(R.id.textViewWaring_Fiter);
                img_cacel_filter= dialog.findViewById(R.id.img_Cacel_Filter);
                btnFilter_Product= dialog.findViewById(R.id.buttonFilter_Product);
                // Thiết lập vị trí của dialog
                dialog.getWindow().setAttributes(layoutParams);

                // Hiển thị dialog
                dialog.show();
                arrayListCat= new ArrayList<>();
                GridLayoutManager layoutManager = new GridLayoutManager(dialog.getContext(), 3);
                recyclerViewCat.setLayoutManager(layoutManager);

                adapterCat= new HorizontalListAdapter(arrayListCat,SearchProduct.this,SearchProduct.this  );
                recyclerViewCat.setAdapter(adapterCat);
                categoryID=null;
                // Load cat
                LoadCategory();
                // lấy priceMin, priceMax


                btnFilter_Product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayListProduct = new ArrayList<>();
                        if (edtpriceMin.getText().toString().isEmpty() && edtpriceMax.getText().toString().isEmpty()) {
                            // Không có giá nhập vào, tiến hành tìm kiếm sản phẩm mà không áp dụng điều kiện giá
                            searchProducts(strSearch, categoryID, null, null);
                            dialog.dismiss();
                        } else {
                        if (!edtpriceMin.getText().toString().isEmpty() && !edtpriceMax.getText().toString().isEmpty()) {
                            // Lấy giá nhập vào từ người dùng
                            int price_Min = Integer.parseInt(edtpriceMin.getText().toString());
                            int price_Max = Integer.parseInt(edtpriceMax.getText().toString());

                            if (price_Min > price_Max) {
                                txtWaring.setText("Giá đi không thể cao hơn giá đến được!");
                            } else {
                                searchProducts(strSearch, categoryID, price_Min, price_Max);
                                dialog.dismiss();
                            }
                        }
                        else {
                            txtWaring.setText("Bạn phải nhập đầy đủ giá!");
                        }}
                    }


                });


                img_cacel_filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });




            }
        });



    }

    private void searchProducts(String strSearch, String categoryID, Integer priceMin, Integer priceMax) {
        ProductService productService = RetrofitClient.getClient().create(ProductService.class);

        // Kiểm tra nếu categoryID là "All", thiết lập giá trị null để tìm kiếm tất cả các danh mục
        if ("All".equals(categoryID)) {
            categoryID = null;
        }

        Call<List<Product>> callProducts_Filter = productService.SearchProducts(strSearch, categoryID, priceMin, priceMax);
        callProducts_Filter.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();
                    if (products.isEmpty()) {
                        txtResult.setVisibility(View.VISIBLE);
                        recyclerViewProduct.setVisibility(View.GONE);
                    } else {
                        txtResult.setVisibility(View.GONE);
                        recyclerViewProduct.setVisibility(View.VISIBLE);
                        for (Product pd : products) {
                            Product_Detail productDetail = new Product_Detail(pd.getProductId(), pd.getProductName(), pd.getPrice(), "");

                            Call<List<ProductImage>> callListImage = productService.GetProductImages(pd.getProductId());
                            callListImage.enqueue(new Callback<List<ProductImage>>() {
                                @Override
                                public void onResponse(@NonNull Call<List<ProductImage>> call, @NonNull Response<List<ProductImage>> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        List<ProductImage> productImages = response.body();
                                        for (ProductImage pro : productImages) {
                                            if (pro.isPrimary()) {
                                                productDetail.imagePath = pro.getImagePath();

                                                arrayListProduct.add(productDetail);
                                                productAdapter.notifyDataSetChanged();
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
                } else {
                    txtResult.setVisibility(View.VISIBLE);
                    recyclerViewProduct.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
            }
        });
    }



    private  void LoadCategory( ){

        arrayListCat.add(new Category("All","Tất cả", 1));
        CategoryService categoryService= RetrofitClient.getClient().create(CategoryService.class);
        Call<List<Category>> callCat= categoryService.GetCategories();
        callCat.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    List<Category> categories= response.body();
                    for (Category cat: categories){
                            if (!arrayListCat.contains(cat)) { // Kiểm tra xem cat có trong arrayListCat không
                                arrayListCat.add(cat); // Nếu không có thì thêm vào
                            }
                        adapterCat.notifyDataSetChanged();



                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {

            }
        });




    }
    private void Init() {
        txtResult= findViewById(R.id.textViewResult_Search);
        recyclerViewProduct= findViewById(R.id.recyclerViewProduct_Search);
        img_Back= findViewById(R.id.img_Back_Search);
        img_filter= findViewById(R.id.img_FilterProduct);
    }

    private void Search_Product(String strSearch){
        ProductService productService= RetrofitClient.getClient().create(ProductService.class);
        Call<List<Product>> callSearchProduct= productService.SearchProducts(strSearch,null,null,null);
        callSearchProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    List<Product> products= response.body();
                    if (products.isEmpty()){
                        txtResult.setVisibility(View.VISIBLE);
                        recyclerViewProduct.setVisibility(View.GONE);
                    }else {
                        txtResult.setVisibility(View.GONE);
                        recyclerViewProduct.setVisibility(View.VISIBLE);
                        for (Product pd : products) {
                            Product_Detail productDetail = new Product_Detail(pd.getProductId(), pd.getProductName(), pd.getPrice(), "");

                            Call<List<ProductImage>> callListImage = productService.GetProductImages(pd.getProductId());
                            callListImage.enqueue(new Callback<List<ProductImage>>() {
                                @Override
                                public void onResponse(@NonNull Call<List<ProductImage>> call, @NonNull Response<List<ProductImage>> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        List<ProductImage> productImages = response.body();
                                        for (ProductImage pro : productImages) {
                                            if (pro.isPrimary()) {
                                                productDetail.imagePath = pro.getImagePath();

                                                arrayListProduct.add(productDetail);
                                                productAdapter.notifyDataSetChanged();
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
                    } }else{
                    txtResult.setVisibility(View.VISIBLE);
                    recyclerViewProduct.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {

            }
        });




    }

    @Override
    public void onClickProduct(String productID) {
        Intent intent= new Intent(this, ProductDetail_Activity.class);
        intent.putExtra("productId", productID);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String CategoryId) {
        categoryID= CategoryId;
        Log.d("cat1: ", CategoryId);
    }
}