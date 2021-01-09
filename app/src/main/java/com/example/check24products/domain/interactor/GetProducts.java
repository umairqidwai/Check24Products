package com.example.check24products.domain.interactor;

import com.example.check24products.domain.interactor.base.UseCase;
import com.example.check24products.domain.repo.ProductsRepo;
import com.example.check24products.presentation.scheduler.DefaultSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class GetProducts extends UseCase{

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private DefaultSchedulerProvider schedulerProvider;
    private ProductsRepo productRepo;

    public GetProducts(DefaultSchedulerProvider schedulerProvider, ProductsRepo productRepo) {
        this.schedulerProvider = schedulerProvider;
        this.productRepo = productRepo;
    }


    @Override
    public void executeUseCase(DisposableObserver disposableObserver) {
        DisposableObserver observer = productRepo.getProductList().subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.mainThread()).subscribeWith(disposableObserver);
        compositeDisposable.add(observer);
    }

    public void clear(){
        compositeDisposable.clear();
    }
}
