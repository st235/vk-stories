package com.github.sasd97.vk_stories.presentation.publish;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.sasd97.lib_router.Router;
import com.github.sasd97.vk_stories.data.AppRepository;
import com.github.sasd97.vk_stories.presentation.base.BasePresenter;
import com.github.sasd97.vk_stories.presentation.story.StoryActivity;
import com.github.sasd97.vk_stories.utils.RxSchedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;

import static com.github.sasd97.lib_router.commands.activities.And.and;
import static com.github.sasd97.lib_router.commands.activities.FinishThis.finishThis;
import static com.github.sasd97.lib_router.commands.activities.Start.start;
import static com.github.sasd97.lib_router.commands.messages.ShowToast.showToast;

/**
 * Created by alexander on 13/09/2017.
 */

@Singleton
@InjectViewState
public class PublishPresenter extends BasePresenter<PublishView> {

    private Router router;
    private RxSchedulers schedulers;
    private AppRepository repository;

    private Disposable request;

    @Inject
    public PublishPresenter(@NonNull Router router,
                            @NonNull AppRepository repository,
                            @NonNull RxSchedulers schedulers) {
        this.router = router;
        this.repository = repository;
        this.schedulers = schedulers;
    }

    @Override
    public void attachView(PublishView view) {
        super.attachView(view);
        publishPreview(repository.getPreview());
    }

    void goToCreateNew() {
        router.pushCommand(finishThis(and(start(StoryActivity.class))));
    }

    void cancelRequest() {
        request.dispose();
        getViewState().showCancelled();
    }

    void showErrorMessage(@NonNull String message) {
        router.pushCommand(showToast(Toast.LENGTH_SHORT, message));
    }

    private void publishPreview(@Nullable Bitmap preview) {
        if (preview == null) {
            getViewState().showError();
            return;
        }

        request = repository.getUsedId()
                .flatMap(id -> repository.uploadPhoto(id, preview))
                .flatMap(ids -> repository.createPost(ids[0], ids[1]))
                .compose(schedulers.getIoToMainTransformerSingle())
                .subscribe(o -> getViewState().showSuccess(),
                        o -> getViewState().showError());

        add(request);
    }
}
