package com.example.check24products.app.presenter;

import androidx.annotation.NonNull;

import com.example.check24products.domain.interactor.GetProducts;
import com.example.check24products.data.model.ProductResponse;
import com.example.check24products.app.presenter.contract.MainView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;

public class MainPresenter {

    private GetProducts getProducts;
    private MainView view;


    public MainPresenter(MainView view) {
        this.view = view;
        getProducts = new GetProducts();
    }

    public void getProducts(boolean isRefresh) {

        DisposableObserver<Response<ProductResponse>> disposableObserver = getProducts.execute().observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response<ProductResponse>>() {

            @Override
            protected void onStart() {
                super.onStart();
                view.showProgressBar();
            }

            @Override
            public void onNext(@NonNull Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse responseBody = response.body();
                    if (!isRefresh) {
                        if (responseBody.getFilters() != null && responseBody.getFilters().size() > 0) {
                            view.loadFilters(responseBody.getFilters());
                        }
                        if (responseBody.getHeader() != null) {
                            view.loadHeader(responseBody.getHeader());
                        }
                    }

                    if (responseBody.getProducts() != null && responseBody.getProducts().size() > 0) {
                        if (isRefresh) {
                            view.refresh(responseBody.getProducts());
                        } else {
                            view.loadProducts(responseBody.getProducts());
                        }

                    }
                } else {
                    view.onError(response.message());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                view.onError(e.getLocalizedMessage());
                view.hideProgressBar();
            }

            @Override
            public void onComplete() {
                view.hideProgressBar();
            }
        });

    }

    public void getFilteredProduct(String filterName) {
        view.onFilterProdcutList(filterName);
    }


}
