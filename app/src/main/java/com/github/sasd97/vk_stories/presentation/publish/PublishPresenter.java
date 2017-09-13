package com.github.sasd97.vk_stories.presentation.publish;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.sasd97.vk_stories.data.AppRepository;
import com.github.sasd97.vk_stories.utils.RxSchedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alexander on 13/09/2017.
 */

@Singleton
@InjectViewState
public class PublishPresenter extends MvpPresenter<PublishView> {

    private RxSchedulers schedulers;
    private AppRepository repository;

    @Inject
    public PublishPresenter(@NonNull AppRepository repository,
                            @NonNull RxSchedulers schedulers) {
        this.repository = repository;
        this.schedulers = schedulers;
    }

    @Override
    public void attachView(PublishView view) {
        super.attachView(view);
        publishPreview(repository.getPreview());
    }

    void publishPreview(@Nullable Bitmap preview) {
        if (preview == null) return;

        repository.getUsedId()
                .flatMap(id -> repository.uploadPhoto(id, preview))
                .flatMap(ids -> repository.createPost(ids[0], ids[1]))
                .compose(schedulers.getIoToMainTransformerSingle())
                .subscribe(o -> {
                    getViewState().showBitmap(preview);
                });
    }
}
