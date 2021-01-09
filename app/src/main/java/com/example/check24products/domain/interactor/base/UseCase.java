package com.example.check24products.domain.interactor.base;

import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T> {

    public abstract void executeUseCase(DisposableObserver<T> observer);

}
