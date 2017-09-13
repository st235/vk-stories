package com.github.sasd97.vk_stories.data;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.github.sasd97.vk_stories.data.net.VkApiWrapper;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by alexander on 12/09/2017.
 */

public class AppRepositoryImpl implements AppRepository {

    private VkApiWrapper vkApiWrapper;
    private WeakReference<Bitmap> previewReference;

    public AppRepositoryImpl(@NonNull VkApiWrapper vkApiWrapper) {
        this.vkApiWrapper = vkApiWrapper;
    }

    @Override
    public Single<Integer> getUsedId() {
        return vkApiWrapper.getUserId();
    }

    @Override
    public Single<Integer[]> uploadPhoto(int uid, @NonNull Bitmap bitmap) {
        return vkApiWrapper.uploadPhoto(uid, bitmap);
    }

    @Override
    public Single<String> createPost(int uid, int mediaId) {
        return vkApiWrapper.createPost(uid, mediaId);
    }

    @Override
    public void savePreview(@NonNull Bitmap bitmap) {
        previewReference = new WeakReference<>(bitmap);
    }

    @Override
    public Bitmap getPreview() {
        return previewReference.get();
    }
}
