package com.example.phonesaleapp.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.UserInfo;
import com.example.phonesaleapp.adapter.product.HorizontalListAdapter;
import com.example.phonesaleapp.adapter.product.ImagePagerAdapter;
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

public class HomeFragment extends Fragment implements ProductClickListener  {
    ArrayList<Category> arrayListCat= new ArrayList<>();
    RecyclerView recyclerViewCat, recyclerViewProduct;
    ViewPager viewPager;
    EditText edtSearch;
    ImageView img_Search;
    TextView txtResult, txtSPHC;
    CircleTabLayout tabLayout;
    HorizontalListAdapter adapterCat;
    ArrayList<Product_Detail> arrayListProduct= new ArrayList<>();
    ListProductAdapter productAdapter;

    String email = UserInfo.getInstance().getEmail();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Init(view);
        txtResult.setVisibility(View.GONE);

        // adapter của listProduct
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productAdapter= new ListProductAdapter(getContext(), arrayListProduct, this);

        recyclerViewProduct.setAdapter(productAdapter);


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtResult.setVisibility(View.GONE);
        int []images= {R.drawable.anhqc1, R.drawable.qcip14, R.drawable.qcip15};
        ImagePagerAdapter adapter= new ImagePagerAdapter(requireContext(),images);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.setScrollPosition(position,positionOffset,false);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // listView ngang của category
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCat.setLayoutManager(layoutManager);
        // Thiết lập adapter
        adapterCat= new HorizontalListAdapter(arrayListCat,getContext(),  this);
        recyclerViewCat.setAdapter(adapterCat);

        LoadCategory(recyclerViewCat);
        LoadProduct();
        img_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSearch= edtSearch.getText().toString();
                Intent intent= new Intent(getContext(), SearchProduct.class);
                intent.putExtra("search",strSearch);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });

    }



    private  void LoadCategory(RecyclerView recyclerViewCat){
        arrayListCat.add(new Category("All","Tất cả", 1));
        CategoryService categoryService= RetrofitClient.getClient().create(CategoryService.class);
        Call<List<Category>> callCat= categoryService.GetCategories();
        callCat.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    List<Category> categories= response.body();
                    for (Category cat: categories){
                        arrayListCat.add(cat);
                        adapterCat.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Lọc sản phẩm theo danh mục


    }


    private  void LoadProduct(){
        ProductService productService= RetrofitClient.getClient().create(ProductService.class);
        Call<List<Product>> callListProduct= productService.GetProducts();

        callListProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    List<Product> products= response.body();

                    for (Product pd: products){
                        Product_Detail productDetail= new Product_Detail(pd.getProductId(),pd.getProductName(), pd.getPrice(), "");

                        Call<List<ProductImage>> callListImage= productService.GetProductImages(pd.getProductId());
                        callListImage.enqueue(new Callback<List<ProductImage>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<ProductImage>> call, @NonNull Response<List<ProductImage>> response) {
                                if(response.isSuccessful() && response.body()!=null) {
                                    List<ProductImage> productImages = response.body();
                                    for (ProductImage pro : productImages) {
                                        if (pro.isPrimary()) {
                                            productDetail.imagePath= pro.getImagePath();
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
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Init(View view){
        recyclerViewCat= view.findViewById(R.id.recyclerView);
        viewPager= view.findViewById(R.id.viewPagerImage);
        tabLayout= view.findViewById(R.id.tabLayout);
        recyclerViewProduct= view.findViewById(R.id.recyclerViewProduct_Search);
        txtResult= view.findViewById(R.id.textViewResult);
        edtSearch= view.findViewById(R.id.edTSearch);
        img_Search= view.findViewById(R.id.img_searchProduct);
        txtSPHC= view.findViewById(R.id.txtSPHC);
    }

    @Override
    public void onClickProduct(String productID) {
        Intent intent= new Intent(getContext(), ProductDetail_Activity.class);
        intent.putExtra("productId", productID);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String CategoryId) {
        arrayListProduct= new ArrayList<>();
        if (CategoryId=="All"){
            LoadProduct();
            txtResult.setVisibility(View.GONE);
            recyclerViewProduct.setVisibility(View.VISIBLE);
        }else{
            ProductService productService= RetrofitClient.getClient().create(ProductService.class);
            Call<List<Product>> callProductByCat= productService.FilterByCategory(CategoryId);

            callProductByCat.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful() && response.body()!=null){
                        List<Product> products= response.body();
                        if (products.isEmpty()){
                            txtResult.setVisibility(View.VISIBLE);
                            recyclerViewProduct.setVisibility(View.GONE);
                            txtSPHC.setVisibility(View.GONE);
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
                        }
                    }else{
                        txtResult.setVisibility(View.VISIBLE);
                        recyclerViewProduct.setVisibility(View.GONE);
                        txtSPHC.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable throwable) {

                }
            });
        }

    }
}
