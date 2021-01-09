package com.example.check24products.presentation.presenter;

import com.example.check24products.data.model.ProductResponse;
import com.example.check24products.domain.interactor.GetProducts;
import com.example.check24products.domain.repo.impl.ProductsRepoImpl;
import com.example.check24products.presentation.presenter.contract.MainView;
import com.example.check24products.presentation.scheduler.DefaultSchedulerProvider;

import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;

public class MainPresenter {

    private GetProducts getProducts;
    private MainView view;


    public MainPresenter(MainView view) {
        this.view = view;
        DefaultSchedulerProvider defaultSchedulerProvider = new DefaultSchedulerProvider();
        getProducts = new GetProducts(defaultSchedulerProvider, new ProductsRepoImpl());
    }

    public void getProducts(boolean isRefresh) {

        getProducts.executeUseCase(new DisposableObserver<Response<ProductResponse>>() {
            @Override
            protected void onStart() {
                super.onStart();
                view.showProgressBar();
            }

            @Override
            public void onNext(Response<ProductResponse> response) {
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
                    view.onError("Error Code:"+response.code()+" \nError Message:"+response.message());
                }
            }

            @Override
            public void onError(Throwable e) {
                view.onError(e.getMessage());
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
