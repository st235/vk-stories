package com.github.sasd97.vk_stories.presentation.authorization;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.sasd97.lib_router.Router;
import com.github.sasd97.vk_stories.presentation.story.StoryActivity;
import com.vk.sdk.VKSdk;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.github.sasd97.lib_router.commands.activities.And.and;
import static com.github.sasd97.lib_router.commands.activities.FinishThis.finishThis;
import static com.github.sasd97.lib_router.commands.activities.Start.start;
import static com.github.sasd97.lib_router.commands.messages.ShowToast.showToast;

/**
 * Created by alexander on 12/09/2017.
 */

@Singleton
@InjectViewState
public class AuthorizationPresenter extends MvpPresenter<AuthorizationView> {

    private Router router;

    @Inject
    AuthorizationPresenter(@NonNull Router router) {
        this.router = router;
    }

    void chooseAuthorization() {
        if (VKSdk.isLoggedIn()) goToStoriesEditor();
    }

    void goToStoriesEditor() {
        router.pushCommand(finishThis(and(start(StoryActivity.class))));
    }

    void showErrorMessage(@NonNull String message) {
        router.pushCommand(showToast(Toast.LENGTH_SHORT, message));
    }
}
