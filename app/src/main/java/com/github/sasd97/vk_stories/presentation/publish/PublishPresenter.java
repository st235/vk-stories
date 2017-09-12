package com.github.sasd97.vk_stories.presentation.publish;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.sasd97.vk_stories.data.AppRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alexander on 13/09/2017.
 */

@Singleton
@InjectViewState
public class PublishPresenter extends MvpPresenter<PublishView> {

    private AppRepository repository;

    @Inject
    public PublishPresenter(@NonNull AppRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attachView(PublishView view) {
        super.attachView(view);
        getViewState().showBitmap(repository.getPreview());
    }
}
