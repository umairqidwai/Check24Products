package com.example.check24products.domain.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler io();

    Scheduler newThread();

}
