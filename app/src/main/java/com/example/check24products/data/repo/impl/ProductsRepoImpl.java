package com.example.check24products.data.repo.impl;

import com.example.check24products.data.model.ProductResponse;
import com.example.check24products.data.remote.api.ProductService;
import com.example.check24products.data.remote.RetrofitClient;
import com.example.check24products.data.repo.ProductsRepo;

import io.reactivex.Observable;
import retrofit2.Response;

public class ProductsRepoImpl implements ProductsRepo {

    @Override
    public Observable<Response<ProductResponse>> getProductList() {
        return RetrofitClient.getClient("https://app.check24.de").create(ProductService.class).getProductList();
    }
}
