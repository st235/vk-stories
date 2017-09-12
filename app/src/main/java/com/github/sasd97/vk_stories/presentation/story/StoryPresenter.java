package com.github.sasd97.vk_stories.presentation.story;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.sasd97.lib_router.Router;
import com.github.sasd97.vk_stories.data.AppRepository;
import com.github.sasd97.vk_stories.presentation.publish.PublishActivity;

import javax.inject.Inject;

import static com.github.sasd97.lib_router.commands.activities.And.and;
import static com.github.sasd97.lib_router.commands.activities.FinishThis.finishThis;
import static com.github.sasd97.lib_router.commands.activities.Start.start;

/**
 * Created by alexander on 10/09/2017.
 */

@InjectViewState
public class StoryPresenter extends MvpPresenter<StoryView> {

    private Router router;
    private AppRepository repository;

    @Inject
    public StoryPresenter(@NonNull Router router,
                          @NonNull AppRepository repository) {
        this.router = router;
        this.repository = repository;
    }

    public void onSend(@NonNull Bitmap bitmap) {
        repository.savePreview(bitmap);
        router.pushCommand(finishThis(and(start(PublishActivity.class))));
    }
}
