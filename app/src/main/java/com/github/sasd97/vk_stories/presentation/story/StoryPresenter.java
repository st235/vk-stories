package com.github.sasd97.vk_stories.presentation.story;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.sasd97.lib_router.Router;
import com.github.sasd97.vk_stories.data.AppRepository;
import com.github.sasd97.vk_stories.presentation.base.BasePresenter;
import com.github.sasd97.vk_stories.presentation.publish.PublishActivity;
import com.github.sasd97.vk_stories.utils.IntentResolver;
import com.github.sasd97.vk_stories.utils.RxSchedulers;

import java.io.File;

import javax.inject.Inject;

import sasd97.java_blog.xyz.libs_common.utils.providers.CameraImageProvider;

import static com.github.sasd97.lib_router.commands.activities.Start.start;
import static com.github.sasd97.lib_router.commands.activities.StartForResult.startForResult;
import static com.github.sasd97.lib_router.commands.messages.ShowToast.showToast;

/**
 * Created by alexander on 10/09/2017.
 */

@InjectViewState
public class StoryPresenter extends BasePresenter<StoryView> {

    private File tempFile;

    private Router router;
    private RxSchedulers schedulers;
    private AppRepository repository;
    private CameraImageProvider provider;
    private IntentResolver intentResolver;

    @Inject
    public StoryPresenter(@NonNull Router router,
                          @NonNull RxSchedulers schedulers,
                          @NonNull AppRepository repository,
                          @NonNull CameraImageProvider provider,
                          @NonNull IntentResolver intentResolver) {
        this.router = router;
        this.provider = provider;
        this.repository = repository;
        this.schedulers = schedulers;
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

    void onAddCameraImage(@NonNull Uri uri) {
        add(provider
                .provide(uri)
                .compose(schedulers.getIoToMainTransformerSingle())
                .subscribe(getViewState()::addCameraImage));
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
