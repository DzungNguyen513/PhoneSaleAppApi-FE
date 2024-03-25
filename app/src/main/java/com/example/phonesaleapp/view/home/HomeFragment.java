package com.example.phonesaleapp.view.home;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.adapter.HorizontalListAdapter;
import com.example.phonesaleapp.adapter.ImagePagerAdapter;
import com.example.phonesaleapp.adapter.ListProductAdapter;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.CategoryService;
import com.example.phonesaleapp.api.service.ProductService;
import com.example.phonesaleapp.model.Category;
import com.example.phonesaleapp.model.Product;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ArrayList<String> arrayListCatName= new ArrayList<>();;
    RecyclerView recyclerView;
    ViewPager viewPager;
    CircleTabLayout tabLayout;
    HorizontalListAdapter adapterCat;
    ArrayList<Product> arrayListProduct= new ArrayList<>();
    ListProductAdapter productAdapter;
    GridView gridListProduct;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Init(view);
        // listView ngang
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        // Thiết lập adapter
        adapterCat= new HorizontalListAdapter(arrayListCatName);
        recyclerView.setAdapter(adapterCat);
        // adapter của listProduct
        productAdapter= new ListProductAdapter(getContext(), R.layout.item_product, arrayListProduct);
        gridListProduct.setAdapter(productAdapter);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        LoadCategory();
        LoadProduct();

    }
    private  void LoadCategory(){
        CategoryService categoryService= RetrofitClient.getClient().create(CategoryService.class);
        Call<List<Category>> callCat= categoryService.GetCategories();
        callCat.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    List<Category> categories= response.body();
                    for (Category cat: categories){
                        arrayListCatName.add(cat.getCategoryName());
                        adapterCat.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                        arrayListProduct.add(new Product(pd.getProductId(),pd.getProductName(),pd.getColorName(), pd.getPrice(), pd.getImg()));
                        productAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Product: "+pd);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Init(View view){
        recyclerView= view.findViewById(R.id.recyclerView);
        viewPager= view.findViewById(R.id.viewPagerImage);
        tabLayout= view.findViewById(R.id.tabLayout);
        gridListProduct= view.findViewById(R.id.gridListSanPham);
    }
}
