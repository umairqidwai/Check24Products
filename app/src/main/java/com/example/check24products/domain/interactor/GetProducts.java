package com.example.check24products.domain.interactor;

import com.example.check24products.data.model.ProductResponse;
import com.example.check24products.domain.interactor.base.UseCase;
import com.example.check24products.domain.repo.ProductsRepo;
import com.example.check24products.presentation.scheduler.DefaultSchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;

public class GetProducts extends UseCase<GetProducts.Result> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private DefaultSchedulerProvider schedulerProvider;
    private ProductsRepo productRepo;

    public GetProducts(DefaultSchedulerProvider schedulerProvider, ProductsRepo productRepo) {
        this.schedulerProvider = schedulerProvider;
        this.productRepo = productRepo;
    }


    @Override
    public void executeUseCase(DisposableObserver<Result> disposableObserver) {
        DisposableObserver<Result> observer = productRepo.getProductList().flatMap(new Function<Response<ProductResponse>, Observable<Result>>() {
            @Override
            public Observable<Result> apply(Response<ProductResponse> response) throws Exception {
                return Observable.just(new Result(response.body(), response.isSuccessful(), response.message()));
            }
        }).subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.mainThread()).subscribeWith(disposableObserver);
        compositeDisposable.add(observer);
    }

    public void clear() {
        compositeDisposable.clear();
    }

    public class Result {
        private ProductResponse data;
        private boolean isSuccess;
        private String errMsg;

        public Result(ProductResponse data, boolean isSuccess, String errMsg) {
            this.data = data;
            this.isSuccess = isSuccess;
            this.errMsg = errMsg;
        }

        public ProductResponse getData() {
            return data;
        }

        public boolean getIsSuccess() {
            return isSuccess;
        }

        public String getErrMsg() {
            return errMsg;
        }

    }
}
