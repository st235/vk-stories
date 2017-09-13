package com.github.sasd97.vk_stories.utils;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;

/**
 * Created by alexander on 07/07/2017.
 */

public abstract class RxSchedulers {

    abstract public Scheduler getMainThreadScheduler();
    abstract public Scheduler getIoScheduler();
    abstract public Scheduler getComputationScheduler();

    public <T> ObservableTransformer<T, T> getIoToMainTransformer()  {
        return objectObservable -> objectObservable
                .subscribeOn(getIoScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> SingleTransformer<T, T> getIoToMainTransformerSingle()  {
        return objectObservable -> objectObservable
                .subscribeOn(getIoScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> FlowableTransformer<T, T> getIoToMainTransformerFlowable() {
        return objectObservable -> objectObservable
                .subscribeOn(getIoScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> ObservableTransformer<T, T> getComputationToMainTransformer()  {
        return objectObservable -> objectObservable
                .subscribeOn(getComputationScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> SingleTransformer<T, T> getComputationToMainTransformerSingle()  {
        return objectObservable -> objectObservable
                .subscribeOn(getComputationScheduler())
                .observeOn(getMainThreadScheduler());
    }

}