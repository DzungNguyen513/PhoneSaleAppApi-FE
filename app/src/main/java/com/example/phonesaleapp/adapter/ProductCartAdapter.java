package com.example.phonesaleapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.phonesaleapp.R;
import com.example.phonesaleapp.api.RetrofitClient;
import com.example.phonesaleapp.api.service.ShoppingCartService;
import com.example.phonesaleapp.model.ProductCart;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ViewHolder> {
    private Context context;
    private List<ProductCart> productList;

    public ProductCartAdapter(Context context, List<ProductCart> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductCart product = productList.get(position);
        holder.tvNameProduct.setText(product.getProductName());
        holder.tvPriceProduct.setText(String.format("%,d VND", product.getPrice()));
        holder.tvCount.setText(String.valueOf(product.getAmount()));
        String imageUrl = "http://192.168.1.7:7244/Assets/Images/" + product.getImg();
        Glide.with(context).load(imageUrl).into(holder.imgPhotoProduct);

        holder.imgRemove.setOnClickListener(v -> {
            AlertDialog.Builder builder =new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm "+product.getProductName()+" khỏi giỏ hàng không ?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteProductFromCart(product.getShoppingCartId(), product.getProductID(),position);
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void deleteProductFromCart(String shoppingCartId, String productId, int position) {
        ShoppingCartService service = RetrofitClient.getClient().create(ShoppingCartService.class);
        Call<Void> call = service.deleteProductFromCart(shoppingCartId, productId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    productList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Sản phẩm đã được xóa khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("DeletingProduct", "ShoppingCartId: " + shoppingCartId + ", ProductId: " + productId);
                Toast.makeText(context, "Lỗi khi xóa sản phẩm khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBoxSelectItem;
        public ImageView imgPhotoProduct, imgMinus, imgPlus, imgRemove;
        public TextView tvNameProduct, tvPriceProduct, tvCount;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBoxSelectItem = itemView.findViewById(R.id.cb_productCart);
            imgPhotoProduct = itemView.findViewById(R.id.img_photo_cart);
            tvNameProduct = itemView.findViewById(R.id.tv_name_product_cart);
            tvPriceProduct = itemView.findViewById(R.id.tv_price_product_cart);
            imgMinus = itemView.findViewById(R.id.img_minus_cart);
            tvCount = itemView.findViewById(R.id.tv_count_cart);
            imgPlus = itemView.findViewById(R.id.img_plus_cart);
            imgRemove = itemView.findViewById(R.id.img_remove_cart);
        }
    }
}
