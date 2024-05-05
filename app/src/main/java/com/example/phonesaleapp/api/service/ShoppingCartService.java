package com.example.phonesaleapp.api.service;

import com.example.phonesaleapp.model.shoppingcart.ShoppingCart;
import com.example.phonesaleapp.model.shoppingcart.ShoppingCartDetail;
import com.example.phonesaleapp.model.shoppingcart.UpdateAmountRequest;
import com.example.phonesaleapp.model.shoppingcart.ProductCart;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShoppingCartService {
    @GET("ShoppingCartDetail/GetCartItems/{customerId}")
    Call<List<ProductCart>> getCartProducts(@Path("customerId") String customerId);
    @DELETE("ShoppingCartDetail/{shoppingCartId}/{productId}")
    Call<Void> deleteProductFromCart(@Path("shoppingCartId") String shoppingCartId, @Path("productId") String productId);
    @PUT("ShoppingCartDetail/UpdateAmount")
    Call<Void> updateProductAmount(@Body UpdateAmountRequest request);

    @GET("ShoppingCart/GetShoppingCartIdByCustomerId/{customerId}")
    Call<String> GetShoppingCartIdByCustomerId(@Path("customerId") String customerId);

    @POST("ShoppingCartDetail/PostShoppingCartDetail")
    Call<Integer> PostShoppingCartDetail(@Body ShoppingCartDetail parameters);

    @GET("ShoppingCart/GetByCustomerId/{customerId}")
    Call<ShoppingCart> GetByCustomerId(@Path("customerId") String customerId);
}
