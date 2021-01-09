package com.example.check24products.domain.repo;

import com.example.check24products.data.model.ProductResponse;

import io.reactivex.Observable;
import retrofit2.Response;

public interface ProductsRepo {

    Observable<Response<ProductResponse>> getProductList();

}
