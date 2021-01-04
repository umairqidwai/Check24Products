package com.example.check24products.data.remote.api;

import com.example.check24products.data.model.ProductResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ProductService {

    @GET("/products-test.json")
    Observable<Response<ProductResponse>> getProductList();
}
