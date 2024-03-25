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
import com.example.phonesaleapp.api.request.shoppingcartitems.UpdateAmountRequest;
import com.example.phonesaleapp.api.service.ShoppingCartService;
import com.example.phonesaleapp.model.ProductCart;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ViewHolder> {
    private Context context;
    private List<ProductCart> productList;
    private OnProductCartChangeListener changeListener;
    private boolean isSelectedAll = false;
    public ProductCartAdapter(Context context, List<ProductCart> productList) {
        this.context = context;
        this.productList = productList;
    }
    public interface OnProductCartChangeListener{
        void onProductCartChange();
    }
    public void setOnProductCartChangeListener(OnProductCartChangeListener listener) {
        this.changeListener = listener;
    }
    public void selectAllItems(boolean isSelectedAll) {
        this.isSelectedAll = isSelectedAll;
        notifyDataSetChanged();
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
        holder.tv_ProductCartName.setText(product.getProductName());
        holder.tv_ProductCartPrice.setText(String.format("%,d VND", product.getPrice()));
        holder.tv_ProductCartColor.setText(product.getColorName());
        holder.tv_ProductCartStorage.setText(String.valueOf(product.getStorageGB()));
        holder.tvCount.setText(String.valueOf(product.getAmount()));

        String baseUrl = RetrofitClient.getBaseUrl();
        String imageUrl = baseUrl.replace("/api/", "/Assets/Images/") + product.getImg();
        Glide.with(context).load(imageUrl).into(holder.img_ProductCart);
        ShoppingCartService service = RetrofitClient.getClient().create(ShoppingCartService.class);
        holder.cb_productCart.setChecked(isSelectedAll);
        holder.imgPlus.setOnClickListener(v -> {
            int newAmount = product.getAmount() + 1;
            product.setAmount(newAmount);
            holder.tvCount.setText(String.valueOf(newAmount));

            UpdateAmountRequest request = new UpdateAmountRequest(product.getShoppingCartId(), product.getProductID(), newAmount);
            service.updateProductAmount(request).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });

        holder.imgMinus.setOnClickListener(v -> {
            if (product.getAmount() > 1) {
                int newAmount = product.getAmount() - 1;
                product.setAmount(newAmount);
                holder.tvCount.setText(String.valueOf(newAmount));

                UpdateAmountRequest request = new UpdateAmountRequest(product.getShoppingCartId(), product.getProductID(), newAmount);
                service.updateProductAmount(request).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()) {

                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        holder.cb_productCart.setOnCheckedChangeListener((buttonView, isChecked) -> {
            productList.get(position).setSelected(isChecked);
            if (changeListener != null) {
                changeListener.onProductCartChange();
            }
        });


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
        public CheckBox cb_productCart;
        public ImageView img_ProductCart, imgMinus, imgPlus, imgRemove;
        public TextView tv_ProductCartName, tv_ProductCartPrice, tvCount, tv_ProductCartColor, tv_ProductCartStorage;

        public ViewHolder(View itemView) {
            super(itemView);
            cb_productCart = itemView.findViewById(R.id.cb_productCart);
            img_ProductCart = itemView.findViewById(R.id.img_ProductCart);
            tv_ProductCartName = itemView.findViewById(R.id.tv_ProductCartName);
            tv_ProductCartPrice = itemView.findViewById(R.id.tv_ProductCartPrice);
            tv_ProductCartColor = itemView.findViewById(R.id.tv_ProductCartColor);
            tv_ProductCartStorage = itemView.findViewById(R.id.tv_ProductCartStorage);
            imgMinus = itemView.findViewById(R.id.img_minusProductCart);
            tvCount = itemView.findViewById(R.id.tv_countProductCart);
            imgPlus = itemView.findViewById(R.id.img_plusProductCart);
            imgRemove = itemView.findViewById(R.id.img_removeProductCart);
        }
    }
}
