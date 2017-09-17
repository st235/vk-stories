package com.github.sasd97.vk_stories.presentation.base;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by alexander on 17/09/2017.
 */

public abstract class BasePresenter<View extends MvpView> extends MvpPresenter<View> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void add(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void destroyView(View view) {
        super.destroyView(view);
        compositeDisposable.clear();
    }
}
