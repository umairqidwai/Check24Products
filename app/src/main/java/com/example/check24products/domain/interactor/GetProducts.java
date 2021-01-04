package com.example.check24products.domain.interactor;

import com.example.check24products.data.model.ProductResponse;
import com.example.check24products.data.repo.ProductsRepo;
import com.example.check24products.domain.interactor.repo.impl.ProductsRepoImpl;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class GetProducts {

    private ProductsRepo productRepo = new ProductsRepoImpl();

    public Observable<Response<ProductResponse>> execute() {
        return productRepo.getProductList().subscribeOn(Schedulers.io());
    }
}
