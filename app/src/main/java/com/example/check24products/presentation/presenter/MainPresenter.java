package com.example.check24products.presentation.presenter;

import com.example.check24products.domain.interactor.GetProducts;
import com.example.check24products.domain.repo.impl.ProductsRepoImpl;
import com.example.check24products.presentation.presenter.contract.MainView;
import com.example.check24products.presentation.scheduler.DefaultSchedulerProvider;

import io.reactivex.observers.DisposableObserver;

public class MainPresenter {

    private GetProducts getProducts;
    private MainView view;


    public MainPresenter(MainView view) {
        this.view = view;
        DefaultSchedulerProvider defaultSchedulerProvider = new DefaultSchedulerProvider();
        getProducts = new GetProducts(defaultSchedulerProvider, new ProductsRepoImpl());
    }

    public void getProducts(boolean isRefresh) {

        getProducts.executeUseCase(new DisposableObserver<GetProducts.Result>() {
            @Override
            protected void onStart() {
                super.onStart();
                view.showProgressBar();
            }

            @Override
            public void onNext(GetProducts.Result response) {

                if (response.getIsSuccess()) {
                    if (!isRefresh) {
                        if (response.getData().getFilters() != null && response.getData().getFilters().size() > 0) {
                            view.loadFilters(response.getData().getFilters());
                        }
                        if (response.getData().getHeader() != null) {
                            view.loadHeader(response.getData().getHeader());
                        }
                    }

                    if (response.getData().getProducts() != null && response.getData().getProducts().size() > 0) {
                        if (isRefresh) {
                            view.refresh(response.getData().getProducts());
                        } else {
                            view.loadProducts(response.getData().getProducts());
                        }

                    }
                } else {
                    view.onError("Error Message: " + response.getErrMsg());

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
