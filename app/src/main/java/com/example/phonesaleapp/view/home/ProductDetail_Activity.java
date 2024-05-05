package com.example.phonesaleapp.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.product.Grid_Adapter;
import com.example.phonesaleapp.adapter.product.ListProductImagesAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.CustomerService;
import com.example.phonesaleapp.api.service.ProductService;
import com.example.phonesaleapp.api.service.ShoppingCartService;
import com.example.phonesaleapp.model.color.Color;
import com.example.phonesaleapp.model.customer.CustomerIdResponse;
import com.example.phonesaleapp.model.product.Product;
import com.example.phonesaleapp.model.product.ProductDetail;
import com.example.phonesaleapp.model.product.ProductImage;
import com.example.phonesaleapp.model.shoppingcart.ShoppingCartDetail;
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
    TextView txtWarning;
    TextView txtProductName, txtProductPrice, txtDetailOfProduct, txtAmountProduct,txtAmountProductDetail, txtPriceProduct,txtColor, txtStorage, txtAmount_BuyProduct;
    ViewPager viewPagerProductImage;
    ArrayList<Integer> listStorage= new ArrayList<>();
    ArrayList<String> listColor= new ArrayList<>();
    Button btnAddToCart, btnOrderNow, buttonOrder;
    ImageView imgProduct_Dt,imgMinus, imgPlus, img_Back;
    Grid_Adapter gridAdapterColor, gridAdapterStorage;
    GridView gridViewColor, gridViewStorage;
    ArrayList<String > arrayListColor= new ArrayList<>();
    ArrayList<String > arrayListStorage= new ArrayList<>();
    private String strColor=" ", strStorage=" ",color="";
    private  int storage=0;
    String spc="";
    String customerId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity);
        Init();
        Intent intent= getIntent();
        String productId= intent.getStringExtra("productId");




        String email= intent.getStringExtra("email");
        CustomerService customerService = RetrofitClient.getClient().create(CustomerService.class);
        Call<CustomerIdResponse> customerIdResponseCall = customerService.getCustomerIDByEmail(email);

        customerIdResponseCall.enqueue(new Callback<CustomerIdResponse>() {
            @Override
            public void onResponse(Call<CustomerIdResponse> call, Response<CustomerIdResponse> response) {
                if (response.isSuccessful() && response.body()!=null){
                    customerId = response.body().getCustomerId();
                    Log.d("bbbbb", customerId);


                }
            }

            @Override
            public void onFailure(Call<CustomerIdResponse> call, Throwable throwable) {

            }
        });
        //Log.d("bbbbb", customerId);
        ShoppingCartService shoppingCartService = RetrofitClient.getClient().create(ShoppingCartService.class);
        Call<String> SPCIdCall= shoppingCartService.GetShoppingCartIdByCustomerId(customerId);
        SPCIdCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String shoppingCartId = response.body();
                    Log.d("ShoppingCartId", shoppingCartId); // Ghi log shopping cart ID
                    // Thực hiện các hoạt động khác với shopping cart ID ở đây
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("Lỗi ", String.valueOf(throwable));
            }
        });

        Load_Product_Detail(productId);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bbb", spc);
                ShowOptionProduct(productId,spc);
            }
        });
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

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

        // Load  product detail
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

        // load amount
        Call<Integer> callAmount= productService.TotalAmountByProductId(productId);
        callAmount.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()&& response.body() !=null){
                    int amount= response.body();
                    txtAmountProduct.setText("Kho: "+amount);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {

            }
        });

    }



    private void ShowOptionProduct(String productID,String ShoppingCartID){
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

        txtColor= view.findViewById(R.id.textViewColor1);
        txtStorage= view.findViewById(R.id.textViewStorage1);
        imgMinus= view.findViewById(R.id.img_minusProductCart);
        imgPlus= view.findViewById(R.id.img_plusProductCart);
        txtAmount_BuyProduct= view.findViewById(R.id.tv_countProductCart);
        txtWarning= view.findViewById(R.id.txtWarning);
        buttonOrder=view.findViewById(R.id.buttonOrder);



        // lời gọi dịch vụ
        ProductService productService= RetrofitClient.getClient().create(ProductService.class);
        // đưa ra thông tin
        Call<Product> callProduct= productService.GetProduct(productID);
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Product product = (Product) response.body();
                    txtPriceProduct.setText(String.valueOf(product.getPrice()));

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

        // khai báo adapter
        gridAdapterColor= new Grid_Adapter(this, R.layout.item_category, arrayListColor);
        gridViewColor.setAdapter(gridAdapterColor);
        gridAdapterStorage= new Grid_Adapter(this, R.layout.item_category, arrayListStorage);
        gridViewStorage.setAdapter(gridAdapterStorage);


        // load amount common
        Call<Integer> callAmount= productService.TotalAmountByProductId(productID);
        callAmount.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()&& response.body() !=null){
                    int amount= response.body();
                    txtAmountProductDetail.setText("" + amount);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {

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

        // event click on grid
        gridViewColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                color= arrayListColor.get(position);
                txtColor.setText(color);
                txtColor.setBackgroundResource(R.drawable.custom_border);
                txtAmount_BuyProduct.setText("1");
                txtAmount_BuyProduct.setBackgroundColor(getResources().getColor(android.R.color.white));

                // Load image by color
                Call<List<ProductImage>> callListColor= productService.GetProductImages(productID);
                callListColor.enqueue(new Callback<List<ProductImage>>() {
                    @Override
                    public void onResponse(Call<List<ProductImage>> call, Response<List<ProductImage>> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            List<ProductImage> productImages= response.body();
                            for(ProductImage pr: productImages){
                                if (pr.getColorName()!=null && pr.getColorName().equals(color)){
                                    String baseUrl = RetrofitClient.getBaseUrl();
                                    String imageUrl = baseUrl.replace("/api/", "/Assets/Images/") +
                                            productID +"/"+pr.getImagePath();
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

                // change price by color and storage
                Call<Integer> callPrice = productService.calculateProductDetailPrice(productID, color, storage);
                callPrice.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            int totalPrice= response.body();
                            txtPriceProduct.setText(String.valueOf(totalPrice));
                        }

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable throwable) {

                    }
                });

                // chang amount by color and storage
                Call<Integer> callAmountByColorStor= productService.AmountByColorStorage(productID, color, storage);
                callAmountByColorStor.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            txtWarning.setText("");
                            int amount= response.body();
                            txtAmountProductDetail.setText(""+ amount);
                            if (amount==0){
                                txtWarning.setText("Sản phẩm này đã hết. Vui lòng chọn sản phẩm khác!");
                                txtAmount_BuyProduct.setText("0");
                                txtAmount_BuyProduct.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                            }else if (amount <=2){
                                txtWarning.setText("Số lượng sản phẩm còn rất thấp!");
                            }

                            // sự kiện thay đổi số lượng
                            ChangeAmout(imgMinus, imgPlus, txtAmount_BuyProduct, txtWarning,amount, txtColor,txtStorage);

                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable throwable) {

                    }
                });


            }
        });
        gridViewStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String storageStr= arrayListStorage.get(position);
                String storageNumberStr = storageStr.replaceAll("[^\\d.]", "");
                storage = Integer.parseInt(storageNumberStr);
                txtStorage.setText(storageStr);
                txtStorage.setBackgroundResource(R.drawable.custom_border);
                txtAmount_BuyProduct.setText("1");
                txtAmount_BuyProduct.setBackgroundColor(getResources().getColor(android.R.color.white));

                // change price by color and storage
                Call<Integer> callPrice = productService.calculateProductDetailPrice(productID, color, storage);
                callPrice.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            int totalPrice= response.body();
                            txtPriceProduct.setText(String.valueOf(totalPrice));
                        }

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable throwable) {

                    }
                });

                // chang amount by color and storage
                Call<Integer> callAmountByColorStor= productService.AmountByColorStorage(productID, color, storage);
                callAmountByColorStor.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        txtWarning.setText("");
                        if (response.isSuccessful() && response.body()!=null){
                            int amount= response.body();
                            txtAmountProductDetail.setText(String.valueOf( amount));
                            if (amount==0){
                                txtWarning.setText("Sản phẩm này đã hết. Vui lòng chọn sản phẩm khác!");
                                txtAmount_BuyProduct.setText("0");
                                txtAmount_BuyProduct.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

                            }else if (amount <=2){
                                txtWarning.setText("Số lượng sản phẩm còn rất thấp!");
                            }

                            // sự kiện thay đổi số lượng
                            ChangeAmout(imgMinus, imgPlus, txtAmount_BuyProduct, txtWarning,amount, txtColor, txtStorage);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable throwable) {

                    }
                });

            }
        });

//        buttonOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (txtColor.getText().toString().equals("")) {
////                    txtWarning.setText("Vui lòng chọn màu!");
////                } else if (txtStorage.getText().toString().equals("")) {
////                    txtWarning.setText("Vui lòng chọn dung lượng!");
////                } else if (txtWarning.getText().toString().equals("")){
//                    int amount=Integer.parseInt( txtAmount_BuyProduct.getText().toString());
//                    int price= Integer.parseInt(txtPriceProduct.getText().toString());
//
//
//                    ShoppingCartService shoppingCartService= RetrofitClient.getClient().create(ShoppingCartService.class);
//                    ShoppingCartDetail spcDetail= new ShoppingCartDetail(ShoppingCartID,productID,color,storage,amount,price);
//                Log.d("jjj", ShoppingCartID +" "+productID+" "+color+" "+storage);
//                    Call<Integer> postSPC= shoppingCartService.postShoppingCartDetail(spcDetail);
//                    postSPC.enqueue(new Callback<Integer>() {
//                        @Override
//                        public void onResponse(Call<Integer> call, Response<Integer> response) {
//                            if (response.isSuccessful() && response.body()!=null){
//                                int result= response.body();
//                                if (result==1){
//                                    Toast.makeText(ProductDetail_Activity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Integer> call, Throwable throwable) {
//
//                        }
//                    });
//
//                }
////            }
//        });




        bottomSheetDialog.show();
    }



    private void ChangeAmout(ImageView imgMinus, ImageView imgPlus,
                             TextView txtAmount, TextView txtWarning, int amoutC, TextView txtColor, TextView txtStorage){


        imgMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtWarning.setText("");
                int amount= Integer.parseInt(txtAmount.getText().toString());
                if (txtColor.getText().toString() ==""){
                    txtWarning.setText("Vui lòng chọn màu!");
                }else if(txtStorage.getText().toString()==""){
                    txtWarning.setText("Vui lòng chọn dung lượng!");
                }else if(amount<=1){
                    txtWarning.setText("Số lượng sản phẩm phải lớn hơn 1!");
                }else{
                    txtAmount.setText(String.valueOf(amount-1));
                }

            }
        });
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtWarning.setText("");
                int amount= Integer.parseInt(txtAmount.getText().toString());
                if (txtColor.getText().toString() ==""){
                    txtWarning.setText("Vui lòng chọn màu!");
                }else if(txtStorage.getText().toString()==""){
                    txtWarning.setText("Vui lòng chọn dung lượng!");
                }else if(amoutC==0){
                    txtAmount.setText("0");
                }else if(amount>=amoutC){
                    txtAmount.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    txtWarning.setText("Số lượng bạn chọn đã vượt quá số lượng của sản phẩm!");
                }
                else{
                    txtAmount.setText(String.valueOf(amount+1));
                }
            }
        });
    }


    private void Init() {
        viewPagerProductImage= findViewById(R.id.viewPagerListProductImage);
        tabLayout=  findViewById(R.id.tabLayoutProduct);
        txtProductName = findViewById(R.id.textViewProductDetail_Name);
        txtProductPrice= findViewById(R.id.textViewProductDetail_Price);
        txtDetailOfProduct= findViewById(R.id.textViewDetailOfProduct);
        btnAddToCart= findViewById(R.id.buttonAddtoCart);
        btnOrderNow= findViewById(R.id.buttonOrderNow);
        img_Back = findViewById(R.id.img_Back);
        txtAmountProduct= findViewById(R.id.tv_AmountProduct_Detail);
    }
}
