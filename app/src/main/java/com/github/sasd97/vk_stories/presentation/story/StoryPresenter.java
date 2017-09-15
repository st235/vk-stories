package com.github.sasd97.vk_stories.presentation.story;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.sasd97.lib_router.Router;
import com.github.sasd97.vk_stories.data.AppRepository;
import com.github.sasd97.vk_stories.presentation.publish.PublishActivity;

import java.io.File;

import javax.inject.Inject;

import com.github.sasd97.vk_stories.utils.IntentResolver;

import static com.github.sasd97.lib_router.commands.activities.Start.start;
import static com.github.sasd97.lib_router.commands.activities.StartForResult.startForResult;
import static com.github.sasd97.lib_router.commands.messages.ShowToast.showToast;

/**
 * Created by alexander on 10/09/2017.
 */

@InjectViewState
public class StoryPresenter extends MvpPresenter<StoryView> {

    private File tempFile;

    private Router router;
    private AppRepository repository;
    private IntentResolver intentResolver;

    @Inject
    public StoryPresenter(@NonNull Router router,
                          @NonNull AppRepository repository,
                          @NonNull IntentResolver intentResolver) {
        this.router = router;
        this.repository = repository;
        this.intentResolver = intentResolver;
    }

    @Override
    public void attachView(StoryView view) {
        super.attachView(view);
        getViewState().showTypeCursor();
    }

    void showPermissionDeniedMessage(@NonNull String message) {
        router.pushCommand(showToast(Toast.LENGTH_SHORT, message));
    }

    void onSend(@NonNull Bitmap bitmap) {
        repository.savePreview(bitmap);
        router.pushCommand(start(PublishActivity.class));
    }

    void onOpenCamera() {
        tempFile = intentResolver.createCameraFile();
        Intent openCamera = intentResolver.createCameraIntent(tempFile);
        router.pushCommand(startForResult(openCamera, IntentResolver.CAMERA_REQUEST_CODE));
    }

    void onOpenGallery() {
        Intent openGallery = intentResolver.createGalleryIntent();
        router.pushCommand(startForResult(openGallery, IntentResolver.GALLERY_REQUEST_CODE));
    }

    File getTempFile() {
        return tempFile;
    }
}
