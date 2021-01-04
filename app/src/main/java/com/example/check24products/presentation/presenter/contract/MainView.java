package com.example.check24products.presentation.presenter.contract;

import com.example.check24products.data.model.Header;
import com.example.check24products.data.model.Product;

import java.util.List;

public interface MainView {

    void loadHeader(Header header);

    void loadFilters(List<String> filters);

    void loadProducts(List<Product> productList);

    void showProgressBar();

    void hideProgressBar();

    void refresh(List<Product> productList);

    void onError(String errMsg);

    void onFilterProdcutList(String filterName);
}
